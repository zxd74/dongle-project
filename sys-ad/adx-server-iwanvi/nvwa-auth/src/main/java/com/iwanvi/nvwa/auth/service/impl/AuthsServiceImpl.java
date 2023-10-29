package com.iwanvi.nvwa.auth.service.impl;

import com.iwanvi.nvwa.auth.model.AuthsVo;
import com.iwanvi.nvwa.auth.service.AuthsService;
import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.dao.AuthsMapper;
import com.iwanvi.nvwa.dao.model.Auths;
import com.iwanvi.nvwa.dao.model.AuthsExample;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class AuthsServiceImpl implements AuthsService {
    @Autowired
    private AuthsMapper authsMapper;

    @Transactional
    @Override
    public void add(Auths auths) {
        if (auths != null
                && StringUtils.isNotBlank(auths.getName())
                && StringUtils.isNotBlank(auths.getPath())
                && auths.getPlatform() != null) {
            if (!pathIsExistedInDb(auths)) {
                auths.setStatus(Constants.STATE_VALID);
                authsMapper.insertSelective(auths);
            } else {
                throw new ServiceException("数据库中已存在。");
            }
        }
    }

    @Transactional
    @Override
    public void update(Auths auths) {
        if (auths != null
                && auths.getId() != null
                && StringUtils.isNotBlank(auths.getPath())
                && auths.getPlatform() != null) {
            if (!pathIsExistedInDb(auths)) {
                auths.setName(null);
                authsMapper.updateByPrimaryKeySelective(auths);
            }
        }
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        if (id != null ) {
            Auths auths = new Auths();
            auths.setId(id);
            auths.setStatus(Constants.STATE_INVALID);
            authsMapper.updateByPrimaryKeySelective(auths);
        }
    }

    @Override
    public Auths get(Integer id) {
        if (id != null) {
            return authsMapper.selectByPrimaryKey(id);
        }
        return null;
    }

    @Override
    public List<AuthsVo> selectForList(Auths auths) {
        AuthsExample example = bindingExample(auths);
        List<Auths> authsList = authsMapper.selectByExample(example);
        List<AuthsVo> authsVos = fillAndSortAuth(authsList);
        return authsVos;
    }

    @Override
    public Page<Auths> selectForPage(Auths auths, Integer cp, Integer ps) {
        Page<Auths> page;
        AuthsExample example = bindingExample(auths);
        int count = authsMapper.countByExample(example);
        List<Auths> list = Lists.newArrayList();
        if (cp != null && ps != null) {
            page = new Page<Auths>(count, cp, ps);
        } else {
            page = new Page<Auths>(count);
        }
        example.setStart(page.getStartPosition());
        example.setLimit(page.getPageSize());
        list = authsMapper.selectByExample(example);
        page.bindData(list);
        return page;
    }

    @Override
    public List<AuthsVo> getUserAuths(Integer userId, Integer platform) {
        if (userId != null) {
            List<Auths> auths = authsMapper.selectByUserIdAndPlatform(userId, platform);
            List<AuthsVo> authsVos = fillAndSortAuth(auths);
            return authsVos;
        }
        return null;
    }

    @Override
    public List<AuthsVo> getGroupAuths(Integer gid, Integer platform) {
        if (gid != null) {
            List<Auths> auths = authsMapper.selectByGroupIdAndPlatform(gid, platform);
            List<AuthsVo> authsVos = fillAndSortAuth(auths);
            return authsVos;
        }
        return null;

    }

    private List<AuthsVo> fillAndSortAuth(List<Auths> auths) {
        List<AuthsVo> authsVos = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(auths)) {
            Map<Integer,AuthsVo> authsMap = Maps.newHashMap();
            AuthsVo vo;
            for (Auths auth : auths) {
                vo = new AuthsVo();
                BeanUtils.copyProperties(auth, vo);
                authsMap.put(auth.getId(), vo);
            }
            for (AuthsVo auth : authsMap.values()) {
                if (auth.getPid() == Constants.INTEGER_0) {
                    authsVos.add(auth);
                    continue;
                }
                AuthsVo pvo = authsMap.get(auth.getPid());
                List<AuthsVo> childList = pvo.getChilds();
                if ( childList != null && childList.size() > Constants.INTEGER_0 ){
                    childList.add(auth);
                    Collections.sort(childList, new Comparator<AuthsVo>() {
                        @Override
                        public int compare(AuthsVo o1, AuthsVo o2) {
                            if ( o1.getOrders() > o2.getOrders() ) {
                                return 1;
                            }
                            return -1;
                        }
                    });
                } else {
                    childList = new ArrayList<AuthsVo>();
                    childList.add(auth);
                    pvo.setChilds(childList);
                }
            }
        }
        Collections.sort(authsVos, new Comparator<AuthsVo>() {
            @Override
            public int compare(AuthsVo o1, AuthsVo o2) {
                if ( o1.getOrders() > o2.getOrders() ) {
                    return 1;
                }
                return -1;
            }
        });
        return authsVos;
    }

    private boolean pathIsExistedInDb(Auths auths) {
        boolean isExisted = false;
        if (StringUtils.isNotBlank(auths.getPath()) && auths.getPlatform() != null) {
            AuthsExample example = new AuthsExample();
            example.createCriteria().andPlatformEqualTo(auths.getPlatform()).andPathEqualTo(auths.getPath())
                    .andStatusEqualTo(Constants.STATE_VALID);
            int count = authsMapper.countByExample(example);
            if (count > Constants.INTEGER_0) {
                List<Auths> list = authsMapper.selectByExample(example);
                if (list.get(Constants.INTEGER_0).getId() != auths.getId()) {
                    isExisted = true;
                }
            }
        }
        return isExisted;
    }

    private AuthsExample bindingExample(Auths auths) {
        AuthsExample example = new AuthsExample();
        if (auths != null) {
            AuthsExample.Criteria criteria = example.createCriteria();
            if (auths.getPlatform() != null) {
                criteria.andPlatformEqualTo(auths.getPlatform());
            }
            if (StringUtils.isNotBlank(auths.getName())) {
                criteria.andNameLike("%" + auths.getName() + "%");
            }
            if (auths.getStatus() != null) {
                criteria.andStatusEqualTo(auths.getStatus());
            } else {
                criteria.andStatusEqualTo(Constants.STATE_VALID);
            }
        }
        example.setOrderByClause(" id desc ");
        return example;
    }
}
