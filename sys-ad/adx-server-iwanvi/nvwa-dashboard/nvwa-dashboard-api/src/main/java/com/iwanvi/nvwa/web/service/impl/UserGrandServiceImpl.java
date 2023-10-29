package com.iwanvi.nvwa.web.service.impl;

import java.util.Date;
import java.util.List;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.web.util.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.dao.UserGrandMapper;
import com.iwanvi.nvwa.dao.UserMapper;
import com.iwanvi.nvwa.dao.model.Company;
import com.iwanvi.nvwa.dao.model.User;
import com.iwanvi.nvwa.dao.model.UserExample;
import com.iwanvi.nvwa.dao.model.UserGrand;
import com.iwanvi.nvwa.dao.model.UserGrandExample;
import com.iwanvi.nvwa.web.service.UserGrandService;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class UserGrandServiceImpl implements UserGrandService {
	
	@Autowired
	private UserGrandMapper userGrandMapper;
	@Autowired
	private UserMapper userMapper;

	@Override
	public void insertBatchUserGrand(Integer uid, String[] aids,Integer userId, Integer type) {
		UserGrand userGrand = new UserGrand();
		userGrand.setCreateTime(new Date());
		userGrand.setUid(uid);
		userGrand.setCreateUser(userId);
		userGrand.setStatus(Constants.STATE_VALID);
		userGrand.setType(type);
		for(int i=0; i<aids.length; i++){
			userGrand.setAid(Integer.valueOf(aids[i]));
			userGrandMapper.insert(userGrand);
		}
	}

	@Override
	public void delete(Integer uid) {
		UserGrand userGrand = new UserGrand();
		UserGrandExample userGrandExample = new UserGrandExample();
		UserGrandExample.Criteria criteria = userGrandExample.createCriteria();
		criteria.andUidEqualTo(uid);
		userGrand.setStatus(Constants.STATE_INVALID);
		userGrandMapper.updateByExampleSelective(userGrand, userGrandExample);
	}

	@Transactional
	@Override
	public void update(Integer uid, Integer aid) {
		UserGrand userGrand = new UserGrand();
		userGrand.setAid(aid);
		userGrand.setUid(uid);
		UserGrandExample userGrandExample = new UserGrandExample();
		UserGrandExample.Criteria criteria = userGrandExample.createCriteria();
		criteria.andUidEqualTo(uid).andAidEqualTo(aid);
		userGrand.setStatus(Constants.STATE_VALID);
		userGrandMapper.updateByExampleSelective(userGrand, userGrandExample);
	}
	
	@Override
	public void insert(Integer uid, Integer aid, Integer userId, Integer type) {
		UserGrand userGrand = new UserGrand();
		userGrand.setCreateTime(new Date());
		userGrand.setUid(uid);
		userGrand.setCreateUser(userId);
		userGrand.setStatus(Constants.STATE_VALID);
		userGrand.setAid(aid);
		userGrand.setType(type);
		userGrandMapper.insert(userGrand);
	}
	
	@Override
	public List<UserGrand> get(Integer uid) {
		UserGrandExample userGrandExample = new UserGrandExample();
		UserGrandExample.Criteria criteria = userGrandExample.createCriteria();
		criteria.andUidEqualTo(uid);
		List<UserGrand> list = userGrandMapper.selectByExample(userGrandExample);
		return list;
	}

	@Override
	public List<UserGrand> getByAidAndType(Integer aid, Integer type) {
		UserGrandExample userGrandExample = new UserGrandExample();
		UserGrandExample.Criteria criteria = userGrandExample.createCriteria();
		criteria.andAidEqualTo(aid);
		criteria.andTypeEqualTo(type);
		List<UserGrand> list = userGrandMapper.selectByExample(userGrandExample);
		return list;
	}
	
	@Override
	public List<UserGrand> getByUidAndType(Integer uid, Integer type) {
		UserGrandExample userGrandExample = new UserGrandExample();
		UserGrandExample.Criteria criteria = userGrandExample.createCriteria();
		criteria.andStatusEqualTo(Constants.STATE_VALID);
		if (uid != null) {
			criteria.andUidEqualTo(uid);
		}
		if (type != null) {
			criteria.andTypeEqualTo(type);
		}
		List<UserGrand> list = userGrandMapper.selectByExample(userGrandExample);
		return list;
	}
	
	/**
	 * 通过uid和被授权类型type获取客户的id
	 */
	@Override
	public List<Integer> getIdListByUidAndType(Integer uid, Integer type) {
		List<Integer> companyIds = Lists.newArrayList();
		List<UserGrand> list = getByUidAndType(uid, type);
		if (list != null && list.size() > 0) {
			List<Integer> userIds = Lists.transform(list, (UserGrand input)-> (input.getAid()));
			UserExample userExample = new UserExample();
			userExample.createCriteria().andIdIn(userIds);
			List<User> users = userMapper.selectByExample(userExample);
			if (users != null && users.size() > 0) {
				companyIds = Lists.transform(users, (User user) -> (user.getCompany()));
			}
		}
		return companyIds;
	}

    @Override
    public List<UserGrand> selectForList(UserGrand userGrand) {
        UserGrandExample example = bindingExample(userGrand);
        return userGrandMapper.select(example);
    }

    @Transactional
    @Override
    public void add(UserGrand userGrand) {
        if (userGrand != null) {
            UserExample example = new UserExample();
            example.createCriteria().andCompanyEqualTo(userGrand.getAid()).andTypeEqualTo(Constants.USER_TYPE_CUST);
            List<User> userList = userMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(userList)) {
                throw new ServiceException("客户数据错误，没有发现客户的登录账号");
            }
            userGrand.setAid(userList.get(Constants.INTEGER_0).getId());
            UserGrandExample grandExample = new UserGrandExample();
            grandExample.createCriteria().andAidEqualTo(userGrand.getAid()).andUidEqualTo(userGrand.getUid())
                    .andStatusEqualTo(Constants.STATE_VALID);
            int count = userGrandMapper.countByExample(grandExample);
            if (count == 0) {
                userGrand.setStatus(Constants.STATE_VALID);
                userGrandMapper.insertSelective(userGrand);
            }
        }
    }

    @Override
    public void addForFs(UserGrand userGrand) {
        if (userGrand != null) {
            userGrand.setStatus(Constants.STATE_VALID);
            userGrandMapper.insertSelective(userGrand);
        }
    }

    @Transactional
    @Override
    public void deleteById(Integer id) {
        if (id != null) {
            UserGrand grand = new UserGrand();
            grand.setId(id);
            grand.setStatus(Constants.STATE_INVALID);
            userGrandMapper.updateByPrimaryKeySelective(grand);
        }
    }

    private UserGrandExample bindingExample(UserGrand userGrand) {
	    UserGrandExample example = new UserGrandExample();
	    if (userGrand != null) {
	        UserGrandExample.Criteria criteria = example.createCriteria();
	        if (userGrand.getAid() != null) {
                if (userGrand.getType() != null && WebConstants.AGENT_OBJECT_TYPE_ADVER == userGrand.getType()) {
                    UserExample example1 = new UserExample();
                    example1.createCriteria().andCompanyEqualTo(userGrand.getAid())
                            .andTypeEqualTo(Constants.USER_TYPE_CUST);
                    List<User> userList =  userMapper.selectByExample(example1);
                    if (!CollectionUtils.isEmpty(userList)) {
                        criteria.andAidEqualTo(userList.get(Constants.INTEGER_0).getId());
                    }
                } else {
                    criteria.andAidEqualTo(userGrand.getAid());
                }
            }
	        if (userGrand.getUid() != null) {
	            criteria.andUidEqualTo(userGrand.getUid());
            }
	        if (userGrand.getStatus() != null) {
	            criteria.andStatusEqualTo(userGrand.getStatus());
            } else {
                criteria.andStatusEqualTo(Constants.STATE_VALID);
            }
	        if (userGrand.getType() != null) {
	            criteria.andTypeEqualTo(userGrand.getType());
            }
        }
        example.setOrderByClause(" id desc ");
	    return example;
    }

	@Override
	public void delete(UserGrand userGrand) {
		UserGrandExample userGrandExample = new UserGrandExample();
		UserGrandExample.Criteria criteria = userGrandExample.createCriteria();
		criteria.andUidEqualTo(userGrand.getUid());
		criteria.andAidEqualTo(userGrand.getAid());
		userGrand.setStatus(Constants.STATE_INVALID);
		userGrandMapper.updateByExampleSelective(userGrand, userGrandExample);
	}
    
    

}
