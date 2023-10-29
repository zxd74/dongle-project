package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.*;
import com.iwanvi.nvwa.dao.model.*;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AdDicService;
import com.iwanvi.nvwa.web.service.AuthGroupService;
import com.iwanvi.nvwa.web.util.WebConstants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class AuthGroupServiceImpl implements AuthGroupService {
    @Autowired
    private AuthGroupMapper authGroupMapper;
    @Autowired
    private GroupAuthsMapper groupAuthsMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthsMapper authsMapper;
    @Autowired
    private AdDicService adDicService;
    @Autowired
    private UserAuthsMapper userAuthsMapper;

    @Transactional
    @Override
    public void add(AuthGroup authGroup) {
        if (authGroup != null && StringUtils.isNotBlank(authGroup.getName())) {
            authGroup.setStatus(Constants.STATE_VALID);
            authGroupMapper.insertSelective(authGroup);
            if (!CollectionUtils.isEmpty(authGroup.getAuthList())) {
                authGroupMapper.countByExample(null);
                Set<Integer> pidSet = Sets.newHashSet();
                for (GroupAuths auth : authGroup.getAuthList()) {
                    auth.setGid(authGroup.getId());
                    groupAuthsMapper.insertSelective(auth);
                    if (!CollectionUtils.isEmpty(auth.getChilds())) {
                        for (GroupAuths authc : auth.getChilds()) {
                            authc.setGid(authGroup.getId());
                            if (authc.getStatus() == Constants.STATE_VALID) {
                                pidSet.add(auth.getAid());
                            }
                            groupAuthsMapper.insertSelective(authc);
                        }
                    }
                }
                if (!CollectionUtils.isEmpty(pidSet)) {
                    GroupAuths groupAuths = new GroupAuths();
                    groupAuths.setStatus(Constants.STATE_VALID);
                    GroupAuthsExample example = new GroupAuthsExample();
                    example.createCriteria().andGidEqualTo(authGroup.getId()).andAidIn(Lists.newArrayList(pidSet));
                    groupAuthsMapper.updateByExampleSelective(groupAuths, example);
                }
            }
        }
    }

    @Transactional
    @Override
    public void update(AuthGroup authGroup) {
        if (authGroup != null && authGroup.getId() != null) {
            authGroup.setName(null);
            authGroupMapper.updateByPrimaryKeySelective(authGroup);
            if (!CollectionUtils.isEmpty(authGroup.getAuthList())) {
                List<Integer> oldGaidList = getAidByGid(authGroup.getId());
                Set<Integer> pidSet = Sets.newHashSet();
                for (GroupAuths auth : authGroup.getAuthList()) {
                    auth.setStatus(Constants.STATE_INVALID);
                    groupAuthsMapper.updateByPrimaryKeySelective(auth);
                    if (!CollectionUtils.isEmpty(auth.getChilds())) {
                        for (GroupAuths authc : auth.getChilds()) {
                            if (authc.getStatus() == Constants.STATE_VALID) {
                                pidSet.add(auth.getAid());
                            }
                            groupAuthsMapper.updateByPrimaryKeySelective(authc);
                        }
                    }
                }
                if (!CollectionUtils.isEmpty(pidSet)) {
                    GroupAuthsExample example = new GroupAuthsExample();
                    example.createCriteria().andGidEqualTo(authGroup.getId()).andAidIn(Lists.newArrayList(pidSet));
                    GroupAuths auths = new GroupAuths();
                    auths.setStatus(Constants.STATE_VALID);
                    groupAuthsMapper.updateByExampleSelective(auths, example);
                }

                List<Integer> newGaidList = getAidByGid(authGroup.getId());
                UserExample userExample = new UserExample();
                userExample.createCriteria().andTypeEqualTo(authGroup.getId())
                        .andIdNotEqualTo(WebConstants.USER_XCAR_AGENT);
                List<User> userList = userMapper.selectByExample(userExample);
                List<Integer> uidList = Lists.transform(userList, User::getId);
                UserAuthsExample authsExample;
                //减少的权限 同步删除组内人员的权限
                for (Integer oldAid : oldGaidList) {
                    if (!newGaidList.contains(oldAid)) {
                        authsExample = new UserAuthsExample();
                        authsExample.createCriteria().andAidEqualTo(oldAid).andUidIn(uidList);
                        userAuthsMapper.deleteByExample(authsExample);
                    }
                }
                UserAuths userAuths;
                //增加的权限 同步增加组内人员的权限
                for (Integer newAid : newGaidList) {
                    if (!oldGaidList.contains(newAid)) {
                        for (Integer uid : uidList) {
                            userAuths = new UserAuths();
                            userAuths.setAid(newAid);
                            userAuths.setUid(uid);
                            userAuths.setStatus(Constants.STATE_INVALID);
                            userAuthsMapper.insertSelective(userAuths);
                        }
                    }
                }
            }
        } else {
            throw new ServiceException("参数错误");
        }
    }

    private List<Integer> getAidByGid(Integer gid) {
        if (gid != null) {
            GroupAuthsExample example = new GroupAuthsExample();
            example.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andGidEqualTo(gid);
            List<GroupAuths> groupAuthsList = groupAuthsMapper.selectByExample(example);
            List<Integer> gAidList = Lists.transform(groupAuthsList, GroupAuths::getAid);
            return gAidList;
        }
		return null;
    }

    @Override
    public AuthGroup get(Integer id) {
        if (id != null) {
            AuthGroup group = authGroupMapper.selectByPrimaryKey(id);
            GroupAuthsExample example = new GroupAuthsExample();
            example.createCriteria().andGidEqualTo(id);
            List<GroupAuths> authsList = groupAuthsMapper.selectByExample(example);
            for (GroupAuths auths : authsList) {
                Auths auth = authsMapper.selectByPrimaryKey(auths.getAid());
                auths.setName(auth.getName());
            }
            group.setAuthList(fillAndSortAuth(authsList));
            Dictionary type = adDicService.getDic(group.getType());
            group.setTypeName(type.getDicValue());
            return group;
        }
        return null;
    }
    private List<GroupAuths> fillAndSortAuth(List<GroupAuths> auths) {
        List<GroupAuths> authsVos = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(auths)) {
            Map<Integer,GroupAuths> authsMap = Maps.newHashMap();
            for (GroupAuths auth : auths) {
                authsMap.put(auth.getAid(), auth);
            }
            Auths menu;
            for (GroupAuths auth : authsMap.values()) {
                menu = authsMapper.selectByPrimaryKey(auth.getAid());
                if (menu.getPid() == Constants.INTEGER_0) {
                    authsVos.add(auth);
                    continue;
                }
                GroupAuths pvo = authsMap.get(menu.getPid());
                List<GroupAuths> childList = pvo.getChilds();
                if (!CollectionUtils.isEmpty(childList)){
                    childList.add(auth);
                } else {
                    childList = new ArrayList<GroupAuths>();
                    childList.add(auth);
                    pvo.setChilds(childList);
                }
            }
        }
        return authsVos;
    }

    @Override
    public List<AuthGroup> selectForList(AuthGroup authGroup) {
        AuthGroupExample example = bindingExample(authGroup);
        return authGroupMapper.selectByExample(example);
    }

    @Override
    public List<AuthGroup> getGroupListForAdd() {
        AuthGroupExample example = new AuthGroupExample();
        List<Integer> canNotAddGroupTypes = Lists.newArrayList();
        canNotAddGroupTypes.add(Constants.USER_TYPE_AGENT);
        canNotAddGroupTypes.add(Constants.USER_TYPE_CUST);
        example.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andTypeNotIn(canNotAddGroupTypes);
        return authGroupMapper.selectByExample(example);
    }

    @Override
    public Page<AuthGroup> selectForPage(AuthGroup authGroup, Integer cp, Integer ps) {
        Page<AuthGroup> page;
        AuthGroupExample example = bindingExample(authGroup);
        int count = authGroupMapper.countByExample(example);
        List<AuthGroup> list = Lists.newArrayList();
        if (cp != null && ps != null) {
            page = new Page<AuthGroup>(count, cp, ps);
        } else {
            page = new Page<AuthGroup>(count);
        }
        example.setStart(page.getStartPosition());
        example.setLimit(page.getPageSize());
        list = authGroupMapper.selectByExample(example);
        fillList(list);
        page.bindData(list);
        return page;
    }

    private void fillList(List<AuthGroup> list) {
        UserExample example;
        for (AuthGroup group : list) {
            example = new UserExample();
            example.createCriteria().andTypeEqualTo(group.getId());
            int num = userMapper.countByExample(example);
            group.setPersonNum(num);
        }
    }

    private AuthGroupExample bindingExample(AuthGroup authGroup) {
        AuthGroupExample example = new AuthGroupExample();
        if (authGroup != null) {
            AuthGroupExample.Criteria criteria = example.createCriteria();
            if (authGroup.getStatus() != null) {
                criteria.andStatusEqualTo(authGroup.getStatus());
            }
            if (authGroup.getPlatform() != null) {
                criteria.andPlatformEqualTo(authGroup.getPlatform());
            }
            if (StringUtils.isNotBlank(authGroup.getName())) {
                criteria.andNameLike("%" + authGroup.getName() + "%");
            }
        }
        example.setOrderByClause(" id desc ");
        return example;
    }
}
