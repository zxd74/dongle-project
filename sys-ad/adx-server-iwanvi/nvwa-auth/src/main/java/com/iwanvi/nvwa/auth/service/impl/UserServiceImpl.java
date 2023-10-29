package com.iwanvi.nvwa.auth.service.impl;

import com.iwanvi.nvwa.auth.model.AuthsVo;
import com.iwanvi.nvwa.auth.service.AuthsService;
import com.iwanvi.nvwa.auth.service.UserService;
import com.iwanvi.nvwa.auth.util.WebConstants;
import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.MD5Utils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.common.utils.UUIDUtils;
import com.iwanvi.nvwa.dao.UserMapper;
import com.iwanvi.nvwa.dao.model.User;
import com.iwanvi.nvwa.dao.model.UserExample;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthsService authsService;
    @Autowired
    private RedisDao redisDao;

    @Override
    public User get(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Transactional
    @Override
    public void add(User user) {
        if (user != null) {
            if (!checkNameIsExistedInDb(user.getUserName())) {
                boolean success = false;
                while (!success) {
                    String uuid = UUIDUtils.getUUID();
                    if (!checkUuidIsExistedInDb(uuid)) {
                        user.setUuid(uuid);
                        user.setStatus(Constants.STATE_VALID);
                        userMapper.insertSelective(user);
                        success = true;
                    }
                }
            }
        }
    }

    @Transactional
    @Override
    public void update(User user) {
        if (user != null && user.getId() != null) {
            user.setUuid(null);
            user.setUserName(null);
            userMapper.updateByPrimaryKey(user);
        }
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        if (id != null) {
            User user = new User();
            user.setId(id);
            user.setStatus(Constants.STATE_INVALID);
            userMapper.updateByPrimaryKey(user);
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
        page.bindData(list);
        return page;
    }

    @Override
    public Map<String, Object> login(String account, String pwd, Integer platform) {
        if (StringUtils.isNotBlank(account) && StringUtils.isNotBlank(pwd)) {
            if (checkNameIsExistedInDb(account)) {
                UserExample example = new UserExample();
                example.createCriteria().andUserNameEqualTo(account)
                        .andPasswordEqualTo(pwd)
                        .andStatusEqualTo(Constants.STATE_VALID)
                .andIsDeleteEqualTo(Constants.STATE_INVALID);
                List<User> userList = userMapper.selectByExample(example);
                if (CollectionUtils.isEmpty(userList)) {
                    throw new ServiceException("密码错误");
                } else {
                    User user = userList.get(Constants.INTEGER_0);
                    List<AuthsVo> authsVos = authsService.getUserAuths(user.getId(), platform);
                    String token = new Date().getTime() + user.getUuid();
                    Map<String, Object> userMap = Maps.newHashMap();
                    userMap.put("u",user);
                    userMap.put("a",authsVos);
                    userMap.put("t",token);
                    redisDao.set(StringUtils.buildString(Constants.TOKEN_KEY, token), JsonUtils.TO_JSON(userMap),
                            WebConstants.EXPIRED_TIME);
                    return userMap;
                }

            } else {
                throw new ServiceException("用户不存在");
            }
        } else {
            throw new ServiceException("用户名、密码不能为空");
        }
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
            if (user.getCompany() != null) {
                criteria.andCompanyEqualTo(user.getCompany());
            }
        }
        return example;
    }
}
