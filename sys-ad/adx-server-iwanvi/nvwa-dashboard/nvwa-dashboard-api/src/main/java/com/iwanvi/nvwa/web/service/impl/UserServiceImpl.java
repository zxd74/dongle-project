package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.MD5Utils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.common.utils.UUIDUtils;
import com.iwanvi.nvwa.dao.*;
import com.iwanvi.nvwa.dao.model.*;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.UserService;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserAuthsMapper userAuthsMapper;
    @Autowired
    private AuthGroupMapper authGroupMapper;
    @Autowired
    private AuthsMapper authsMapper;
    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public User get(Integer id) {
        User user =  userMapper.selectByPrimaryKey(id);
        UserAuthsExample example = new UserAuthsExample();
        example.createCriteria().andUidEqualTo(id);
        List<UserAuths> authsList = userAuthsMapper.selectByExample(example);
        for (UserAuths auths : authsList) {
            Auths auth = authsMapper.selectByPrimaryKey(auths.getAid());
            if (auth != null) {
                auths.setName(auth.getName());
            }
        }
        user.setAuths(fillAndSortAuth(authsList));
        AuthGroup group = authGroupMapper.selectByPrimaryKey(user.getType());
        user.setGroupName(group.getName());
        user.setGroupType(group.getType());
        return user;
    }
    private List<UserAuths> fillAndSortAuth(List<UserAuths> auths) {
        List<UserAuths> authsVos = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(auths)) {
            Map<Integer,UserAuths> authsMap = Maps.newHashMap();
            for (UserAuths auth : auths) {
                authsMap.put(auth.getAid(), auth);
            }
            Auths menu;
            for (UserAuths auth : authsMap.values()) {
                menu = authsMapper.selectByPrimaryKey(auth.getAid());
                if (menu.getPid() == Constants.INTEGER_0) {
                    authsVos.add(auth);
                    continue;
                }
                UserAuths pvo = authsMap.get(menu.getPid());
                List<UserAuths> childList = pvo.getChilds();
                if (!CollectionUtils.isEmpty(childList)){
                    childList.add(auth);
                } else {
                    childList = new ArrayList<UserAuths>();
                    childList.add(auth);
                    pvo.setChilds(childList);
                }
            }
        }
        return authsVos;
    }

    @Transactional
    @Override
    public void add(User user) {
        if (user != null) {
            if (!checkNameIsExistedInDb(user.getUserName())) {

                User createUser = userMapper.selectByPrimaryKey(user.getCreateUser());
                if (Constants.USER_TYPE_AGENT == createUser.getType() && Constants.USER_TYPE_AGENT_OPERATE == user.getType()) {
                    user.setCompany(createUser.getCompany());
                } else {
                    AuthGroup group = authGroupMapper.selectByPrimaryKey(user.getType());
                    //直客运营默认代理商为 爱卡直客代理商
                    if (Constants.USER_TYPE_DIRECT_OPERATE == group.getType()) {
                        user.setCompany(WebConstants.KA_AGENT_ID);
                    }
                }
                boolean success = false;
                while (!success) {
                    String uuid = UUIDUtils.getUUID().substring(Constants.INTEGER_0,Constants.INTEGER_6);
                    if (!checkUuidIsExistedInDb(uuid)) {
                        user.setUuid(uuid);
                        user.setIsDelete(Constants.STATE_INVALID);
                        user.setPassword(MD5Utils.MD5(user.getPassword()));
                        user.setStatus(Constants.STATE_VALID);
                        userMapper.insertSelective(user);
                        success = true;
                    }
                }
                List<UserAuths> auths = user.getAuths();
                if (!CollectionUtils.isEmpty(auths)) {
                    userMapper.countByExample(null);
                    Set<Integer> pidSet = Sets.newHashSet();
                    for (UserAuths auth : auths) {
                        auth.setUid(user.getId());
                        auth.setStatus(Constants.STATE_INVALID);
                        userAuthsMapper.insertSelective(auth);
                        if (!CollectionUtils.isEmpty(auth.getChilds())) {
                            for (UserAuths authc : auth.getChilds()) {
                                authc.setUid(user.getId());
                                if (authc.getStatus() == Constants.STATE_VALID) {
                                    pidSet.add(auth.getAid());
                                }
                                userAuthsMapper.insertSelective(authc);
                            }
                        }
                    }
                    if (!CollectionUtils.isEmpty(pidSet)) {
                        UserAuthsExample example = new UserAuthsExample();
                        example.createCriteria().andUidEqualTo(user.getId()).andAidIn(Lists.newArrayList(pidSet));
                        UserAuths auth = new UserAuths();
                        auth.setStatus(Constants.STATE_VALID);
                        userAuthsMapper.updateByExampleSelective(auth, example);
                    }
                }
            } else {
                throw new ServiceException("已有重名用户");
            }
        }
    }

    @Transactional
    @Override
    public void update(User user) {
        if (user != null && user.getId() != null) {
            if (StringUtils.isNotBlank(user.getPassword())) {
                user.setPassword(MD5Utils.MD5(user.getPassword()));
            } else {
                user.setPassword(null);
            }
            user.setUuid(null);
            user.setUserName(null);
            userMapper.updateByPrimaryKeySelective(user);
            List<UserAuths> auths = user.getAuths();
            if (!CollectionUtils.isEmpty(auths) && WebConstants.USER_XCAR_AGENT != user.getId()) {
                userMapper.countByExample(null);
                Set<Integer> pidSet = Sets.newHashSet();
                for (UserAuths auth : auths) {
                    auth.setStatus(Constants.STATE_INVALID);
                    userAuthsMapper.updateByPrimaryKeySelective(auth);
                    if (!CollectionUtils.isEmpty(auth.getChilds())) {
                        for (UserAuths authc : auth.getChilds()) {
                            if (authc.getStatus() == Constants.STATE_VALID) {
                                pidSet.add(auth.getAid());
                            }
                            userAuthsMapper.updateByPrimaryKeySelective(authc);
                        }
                    }
                }
                if (!CollectionUtils.isEmpty(pidSet)) {
                    UserAuthsExample example = new UserAuthsExample();
                    example.createCriteria().andUidEqualTo(user.getId()).andAidIn(Lists.newArrayList(pidSet));
                    UserAuths auth = new UserAuths();
                    auth.setStatus(Constants.STATE_VALID);
                    userAuthsMapper.updateByExampleSelective(auth, example);
                }
            }
        }
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        if (id != null) {
            User user = new User();
            user.setId(id);
            user.setIsDelete(Constants.STATE_VALID);
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    @Override
    public List<User> selectForList(User user) {
        UserExample example = bindingExample(user);
        return userMapper.selectByExample(example);
    }

    @Override
    public Page<User> selectForPage(User user, Integer cp, Integer ps) {
        Page<User> page;
        UserExample example = bindingExample(user);
        int count = userMapper.countByExample(example);
        List<User> list = Lists.newArrayList();
        if (cp != null && ps != null) {
            page = new Page<User>(count, cp, ps);
        } else {
            page = new Page<User>(count);
        }
        example.setStart(page.getStartPosition());
        example.setLimit(page.getPageSize());
        list = userMapper.selectByExample(example);
        fillList(list);
        page.bindData(list);
        return page;
    }

    private void fillList(List<User> list) {
        for (User user : list) {
            AuthGroup group = authGroupMapper.selectByPrimaryKey(user.getType());
            user.setGroupName(group.getName());
            user.setGroupType(group.getType());
            if (user.getCompany() != null) {
                Company company = companyMapper.selectByPrimaryKey(user.getCompany());
                if (company != null) {
                    user.setCompanyName(company.getFullName());
                }
            }
        }
    }

    @Override
    public List<Integer> selectUidsByAgentId(Integer uid) {
        List<Integer> uids = Lists.newArrayList();
        if (uid != null) {
            UserExample userExample = new UserExample();
            userExample.createCriteria().andCreateUserEqualTo(uid).andStatusEqualTo(Constants.STATE_VALID);
            List<User> users = userMapper.selectByExample(userExample);
            if (users != null && users.size() > 0) {
                List<Integer> userIds = Lists.transform(users, User :: getId);
                uids.addAll(userIds);
                for (Integer userId : userIds) {
                    uids.addAll(selectUidsByAgentId(userId));
                }
            }
        }
        return uids;
    }

    @Override
    public boolean isAdmin(Integer id) {
        boolean isAdmin = false;
        if (id != null) {
            User user = userMapper.selectByPrimaryKey(id);
            AuthGroup group = authGroupMapper.selectByPrimaryKey(user.getType());
            if (user != null && Constants.USER_TYPE_ADMIN == group.getType()) {
                isAdmin = true;
            }
        }
        return isAdmin;
    }

    @Override
    public List<Integer> getAgentAdminIdByAid(Integer id) {
        AuthGroupExample groupExample = new AuthGroupExample();
        //万维的系统 由于没有代理商 所以新建客户时授权给超级管理员
        groupExample.createCriteria().andStatusEqualTo(Constants.STATE_VALID).andTypeEqualTo(Constants.USER_TYPE_ADMIN);
        List<AuthGroup> groups = authGroupMapper.selectByExample(groupExample);
        List<Integer> gids = Lists.transform(groups,(AuthGroup::getId));
        UserExample example = new UserExample();
        example.createCriteria().andCompanyEqualTo(id).andTypeIn(gids)
                .andStatusEqualTo(Constants.STATE_VALID);
        List<User> users = userMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(users)) {
            return Lists.transform(users,(User::getId));
        }
        return null;
    }

    @Override
    public void resetPassword(String oldPwd, String newPwd, Integer uid) {
        if (uid  != null) {
            if (StringUtils.isNotBlank(oldPwd) && StringUtils.isNotBlank(newPwd)) {
                User user = userMapper.selectByPrimaryKey(uid);
                if (user != null) {
                    String pwdDb = user.getPassword();
                    if (MD5Utils.MD5(oldPwd).equals(pwdDb)) {
                        User userNew = new User();
                        userNew.setId(uid);
                        userNew.setPassword(MD5Utils.MD5(newPwd));
                        userMapper.updateByPrimaryKeySelective(userNew);
                    } else {
                        throw new ServiceException("原密码错误");
                    }
                } else {
                    throw new ServiceException("用户不存在");
                }
            } else {
                throw new ServiceException("新旧密码都不能为空");
            }
        } else {
            throw new ServiceException("用户未登录");
        }
    }

    @Override
    public Integer getAgType(Integer uid) {
        if (uid != null) {
            User user = userMapper.selectByPrimaryKey(uid);
            Integer cid = user.getCompany();
            if (cid != null) {
                Company company = companyMapper.selectByPrimaryKey(cid);
                if (WebConstants.COMPANY_TYPE_AGENT == company.getType()) {
                    return company.getAgType();
                }
            }
        }
        return null;
    }

    /**
     * 检查用户名是否已存在
     * @param name
     * @return
     */
    private boolean checkNameIsExistedInDb(String name) {
        boolean isExisted = false;
        if (StringUtils.isNotBlank(name)) {
            UserExample example = new UserExample();
            example.createCriteria().andUserNameEqualTo(name);
            int count = userMapper.countByExample(example);
            if (count > 0) {
                isExisted = true;
            }
        }
        return isExisted;
    }

    /**
     * 检查UUID是否已存在
     * @param uuid
     * @return
     */
    private boolean checkUuidIsExistedInDb(String uuid) {
        boolean isExisted = false;
        if (StringUtils.isNotBlank(uuid)) {
            UserExample example = new UserExample();
            example.createCriteria().andUuidEqualTo(uuid);
            int count = userMapper.countByExample(example);
            if (count > 0) {
                isExisted = true;
            }
        }
        return isExisted;
    }

    private UserExample bindingExample(User user) {
        UserExample example = new UserExample();
        if (user != null) {
            UserExample.Criteria criteria = example.createCriteria();
            if (user.getId() != null) {
                criteria.andIdEqualTo(user.getId());
            }
            if (StringUtils.isNotBlank(user.getRealName())) {
                criteria.andRealNameLike("%" + user.getRealName() + "%");
            }
            if (StringUtils.isNotBlank(user.getUserName())) {
                criteria.andUserNameLike("%" + user.getUserName() + "%");
            }
            if (user.getCompany() != null) {
                criteria.andCompanyEqualTo(user.getCompany());
            }
            if (user.getStatus() != null) {
                criteria.andStatusEqualTo(user.getStatus());
            }
            if (user.getIsDelete() != null) {
                criteria.andIsDeleteEqualTo(user.getIsDelete());
            } else {
                criteria.andIsDeleteEqualTo(Constants.STATE_INVALID);
            }
            if (user.getCreateUser() != null) {
                criteria.andCreateUserEqualTo(user.getCreateUser());
            }
        }
        example.setOrderByClause(" id desc ");
        return example;
    }

}
