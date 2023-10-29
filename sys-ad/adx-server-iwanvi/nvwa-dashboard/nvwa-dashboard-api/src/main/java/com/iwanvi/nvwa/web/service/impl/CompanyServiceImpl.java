package com.iwanvi.nvwa.web.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.iwanvi.nvwa.dao.*;
import com.iwanvi.nvwa.dao.model.*;
import com.iwanvi.nvwa.web.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.redis.server.RedisDao;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.common.utils.DateUtils;
import com.iwanvi.nvwa.common.utils.JsonUtils;
import com.iwanvi.nvwa.common.utils.StringUtils;
import com.iwanvi.nvwa.common.utils.UUIDUtils;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.util.WebConstants;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class CompanyServiceImpl implements CompanyService {
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private UserGrandMapper userGrandMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private AdxRelationService adxRelationService;
	@Autowired
	private AuthGroupMapper authGroupMapper;
	@Autowired
	private GroupAuthsMapper groupAuthsMapper;
	@Autowired
	private UserAuthsMapper userAuthsMapper;
	@Autowired
	private AdxRelationMapper adxRelationMapper;
	@Autowired
	private SysCrontabService sysCrontabService;
	@Autowired
	private FlowSourceMapper flowSourceMapper;
    @Autowired
	private AgentPriceService agentPriceService;
    @Autowired
    private IndustryMapper industryMapper;
	@Transactional
	@Override
	public void add(Company company) {
		if (company != null) {
			User user = new User();
			if (StringUtils.isBlank(company.getUserName()) && StringUtils.isBlank(company.getPassword())) {
				throw new ServiceException("用户名、密码不能为空");
			} else {
				if (checkNameIsexistedInDb(company)) {
					throw new ServiceException("公司：" + company.getFullName() + "已存在");
				}
				user.setUserName(company.getUserName());
				user.setPassword(company.getPassword());
				user.setStatus(Constants.STATE_VALID);
				user.setCreateUser(company.getCreateUser());
				user.setCreateTime(new Date());
				AuthGroup group = null;
				if (WebConstants.COMPANY_TYPE_AGENT == company.getType()) {
					AuthGroupExample groupExample = new AuthGroupExample();
					groupExample.createCriteria().andTypeEqualTo(Constants.USER_TYPE_AGENT)
							.andStatusEqualTo(Constants.STATE_VALID);
					List<AuthGroup> groups = authGroupMapper.selectByExample(groupExample);
					group = groups.get(Constants.INTEGER_0);
					user.setType(group.getId());
					company.setAid(null);
					company.setBidDiscount(null);
					company.setPayDiscount(null);
					company.setReadonly(null);
				} else if (WebConstants.COMPANY_TYPE_ADVER == company.getType()) {
					AuthGroupExample groupExample = new AuthGroupExample();
					groupExample.createCriteria().andTypeEqualTo(Constants.USER_TYPE_CUST)
							.andStatusEqualTo(Constants.STATE_VALID);
					List<AuthGroup> groups = authGroupMapper.selectByExample(groupExample);
					group = groups.get(Constants.INTEGER_0);
					user.setType(group.getId());
					User creater = userMapper.selectByPrimaryKey(user.getCreateUser());
					AuthGroup authGroup = authGroupMapper.selectByPrimaryKey(creater.getType());
					// 直客代理商创建客户时，代理商默认为爱卡直客代理商，超管创建客户时已选择代理商
					if (Constants.USER_TYPE_DIRECT_OPERATE == authGroup.getType()) {
						company.setAid(WebConstants.KA_AGENT_ID);
					} else if (Constants.USER_TYPE_ADMIN != authGroup.getType()) {
						company.setAid(creater.getCompany());
					} else if (Constants.USER_TYPE_ADMIN == authGroup.getType()) {
						company.setAid(WebConstants.KA_AGENT_ID);
					}
					company.setProfitMargin(null);
				}
				company.setStatus(Constants.STATE_VALID);
				company.setBalanceStatus(Constants.STATE_INVALID);
				company.setIsDelete(Constants.STATE_INVALID);
				boolean success = false;
				while (!success) {
					String uuid = UUIDUtils.getUUID().substring(Constants.INTEGER_0, Constants.INTEGER_6);
					if (!checkUuidIsExistedInDb(uuid)) {
						company.setUuid(uuid);
						companyMapper.insertSelective(company);
						companyMapper.countByExample(null);
						if (WebConstants.COMPANY_TYPE_AGENT == company.getType()) {
							sysCrontabService.addSysCrontab(company.getId(), Constants.OBJ_AGENT, Constants.OP_ADD);
                            agentPriceService.addPriceByAid(company.getId());
						}
//						redisDao.set(WebConstants.REDIS_COMPANY_KEY_PREFIX + company.getId(),
//								JsonUtils.TO_JSON(company));
						success = true;
					}
				}
				user.setCompany(company.getId());
				userService.add(user);
				if (group != null) {
					GroupAuthsExample groupAuthsExample = new GroupAuthsExample();
					groupAuthsExample.createCriteria().andGidEqualTo(group.getId());
					List<GroupAuths> groupAuths = groupAuthsMapper.selectByExample(groupAuthsExample);
					UserAuths auth;
					for (GroupAuths groupAuth : groupAuths) {
						auth = new UserAuths();
						auth.setAid(groupAuth.getAid());
						auth.setUid(user.getId());
						auth.setReadonly(Constants.STATE_INVALID);
						auth.setStatus(groupAuth.getStatus());
						userAuthsMapper.insertSelective(auth);
					}
				}
				// 新建广告主时 默认授权
				if (WebConstants.COMPANY_TYPE_ADVER == company.getType()) {
				    //新建广告主时所有媒体默认审核通过。
//                    List<FlowSource> mediaList = flowSourceMapper.selectByExample(null);
//                    if (!CollectionUtils.isEmpty(mediaList)) {
                        AdxRelation adxRelation = null;
//                        for (FlowSource media : mediaList) {
                            adxRelation = new AdxRelation();
                            adxRelation.setObjId(company.getId());
                            adxRelation.setObjType(Constants.OBJ_ADVERTISER);
                            adxRelation.setAdxType(Constants.INTEGER_1);//中文万维
                            adxRelation.setStatus(Constants.STATE_VALID);
                            adxRelation.setAuditState(Constants.STATE_WAIT_AUDIT);
                            adxRelationMapper.insert(adxRelation);
//                        }
//                    }


					userMapper.countByExample(null);
					UserGrand userGrand = new UserGrand();
					userGrand.setAid(user.getId());
					userGrand.setType(WebConstants.AGENT_OBJECT_TYPE_ADVER);
					userGrand.setStatus(Constants.STATE_VALID);
					userGrand.setUid(company.getCreateUser());
					userGrandMapper.insertSelective(userGrand);

					// 给代理商管理员授权
					List<Integer> agentAdminIds = userService.getAgentAdminIdByAid(company.getAid());
                    if (!CollectionUtils.isEmpty(agentAdminIds)) {
                        for (Integer agentAdminId : agentAdminIds) {
                            if (agentAdminId != company.getCreateUser()) {
                                userGrand = new UserGrand();
                                userGrand.setAid(user.getId());
                                userGrand.setType(WebConstants.AGENT_OBJECT_TYPE_ADVER);
                                userGrand.setStatus(Constants.STATE_VALID);
                                userGrand.setUid(agentAdminId);
                                userGrandMapper.insertSelective(userGrand);
                            }
                        }
                    }
                }
			}
		}
	}

	@Transactional
	@Override
	public void update(Company company) {
		if (company != null && company.getId() != null) {
			company.setUuid(null);
			company.setFullName(null);
			companyMapper.updateByPrimaryKeySelective(company);
			updateSysCrontab(company);
			company = companyMapper.selectByPrimaryKey(company.getId());
//			redisDao.set(WebConstants.REDIS_COMPANY_KEY_PREFIX + company.getId(), JsonUtils.TO_JSON(company));
		}
	}

	@Transactional
	@Override
	public void delete(Integer id) {
		if (id != null) {
			Company company = new Company();
			company.setId(id);
			company.setIsDelete(Constants.STATE_VALID);
			companyMapper.updateByPrimaryKeySelective(company);
//			redisDao.del(WebConstants.REDIS_COMPANY_KEY_PREFIX + id);
			UserExample example = new UserExample();
			example.createCriteria().andCompanyEqualTo(id);
			User user = new User();
			user.setIsDelete(Constants.STATE_VALID);
			userMapper.updateByExampleSelective(user, example);
		}
	}

	@Override
	public Company get(Integer id) {
		if (id != null) {
			Company company;
//			String companyKey = WebConstants.REDIS_COMPANY_KEY_PREFIX + id;
//			String companyJson = redisDao.get(companyKey);
//			if (StringUtils.isNotBlank(companyJson)) {
//				company = JsonUtils.TO_OBJ(companyJson, Company.class);
//			} else {
				company = companyMapper.selectByPrimaryKey(id);
//				redisDao.set(companyKey, JsonUtils.TO_JSON(company));
//			}
			AdxRelation relation = new AdxRelation();
			relation.setObjId(id);
			relation.setObjType(Constants.OBJ_ADVERTISER);
			List<AdxRelation> relations = adxRelationService.selectList(relation);
			company.setRelations(relations);
			return company;
		}
		return null;
	}

    @Override
    public Company get(String uuid) {
	    if (StringUtils.isNotBlank(uuid)) {
	        CompanyExample example = new CompanyExample();
	        example.createCriteria().andUuidEqualTo(uuid);
	        List<Company> list = companyMapper.selectByExample(example);
	        if (!CollectionUtils.isEmpty(list)) {
	            return list.get(Constants.INTEGER_0);
            }
        }
        return null;
    }

    @Override
	public List<Company> selectForList(Company company) {
		CompanyExample example = bindingExample(company);
		return companyMapper.selectByExample(example);
	}

	@Override
	public Page<Company> selectForPage(Company company, Integer cp, Integer ps) {
		Page<Company> page;
		CompanyExample example = bindingExample(company);
        if (WebConstants.COMPANY_TYPE_ADVER == company.getType()) {
            if (company.getCreateUser() != null && !userService.isAdmin(company.getCreateUser())) {
                List<Integer> companyIds = getCompanyIdListByGrandUid(company.getCreateUser());
                if (!CollectionUtils.isEmpty(companyIds)) {
                    example.getOredCriteria().get(Constants.INTEGER_0).andIdIn(companyIds);
                } else {
                    return null;
                }
            }
        }
		int count = companyMapper.countByExample(example);
		List<Company> list = Lists.newArrayList();
		if (cp != null && ps != null) {
			page = new Page<Company>(count, cp, ps);
		} else {
			page = new Page<Company>(count);
		}
		example.setStart(page.getStartPosition());
		example.setLimit(page.getPageSize());
		list = companyMapper.selectByExampleWithBLOBs(example);
		if (WebConstants.COMPANY_TYPE_ADVER == company.getType()) {
			fillList(list);
		}
        for (Company com : list) {
            if (com != null && StringUtils.isNotBlank(com.getUuid())) {
                String moneyKey = StringUtils.buildString(WebConstants.KEY_REDIS_MONEY_TOTAL, com.getUuid());
                String money = redisDao.get(moneyKey);
                if (StringUtils.isNotBlank(money)) {
                    com.setMoney(Double.parseDouble(money)/100000);
                } else {
                    com.setMoney(0d);
                }
            }
        }
		page.bindData(list);
		return page;
	}

	@Override
	public Map<String, Object> getBalance(Integer id) {
		Company company = companyMapper.selectByPrimaryKey(id);
		Map<String, Object> balanceMap = Maps.newHashMap();
		if (company != null) {
			String uuid = companyMapper.selectByPrimaryKey(id).getUuid();
			String TOTAL_KEY = StringUtils.buildString(WebConstants.KEY_REDIS_MONEY_TOTAL, uuid);
			double cost =  (redisDao.get(TOTAL_KEY) == null ? 0 : Double.parseDouble(redisDao.get(TOTAL_KEY)));
			cost = cost / 1000 / 100;
			balanceMap.put("balance", (double)Math.round(cost * 100) / 100);
		}
		return balanceMap;
	}

	@Override
	public Map<String, Object> getSumCost(Integer id, String startDay, String endDay) {
		long costNow = 0;
		long costBefore = 0;
		Map<String, Object> respMap = Maps.newHashMap();
		Date startDate = DateUtils.parse(startDay, DateUtils.SHORT_FORMAT);
		Date endDate = DateUtils.parse(endDay, DateUtils.SHORT_FORMAT);
		Long countDate = DateUtils.getDValue2Day(startDate, endDate);
		Date startDateBefore = DateUtils.getPreDaysDate(startDate, countDate.intValue());
		Date endDateBefore = DateUtils.getPreDaysDate(startDate, Constants.INTEGER_1);
		Company company = companyMapper.selectByPrimaryKey(id);
		if (company != null) {
			String uuid = company.getUuid();
			for (Date curDate = new Date(startDate.getTime()); curDate.compareTo(endDate) <= 0; curDate = DateUtils
					.getNextDaysDate(curDate, 1)) {
				String curDay = DateUtils.format(curDate, DateUtils.SHORT_FORMAT);
				String COST_KEY = StringUtils.buildString(WebConstants.KEY_REDIS_MONEY_COST, uuid, curDay);
				// 此时获得的花费单位 ：分的千分之一
				costNow += redisDao.get(COST_KEY) == null ? 0 : Long.parseLong(redisDao.get(COST_KEY));
			}
			for (Date curDate = new Date(startDateBefore.getTime()); curDate
					.compareTo(endDateBefore) <= 0; curDate = DateUtils.getNextDaysDate(curDate, 1)) {
				String curDayBefore = DateUtils.format(curDate, DateUtils.SHORT_FORMAT);
				String COST_KEY = StringUtils.buildString(WebConstants.KEY_REDIS_MONEY_COST, uuid, curDayBefore);
				costBefore += redisDao.get(COST_KEY) == null ? 0 : Long.parseLong(redisDao.get(COST_KEY));
			}
			respMap.put("sumNow", costNow);
			respMap.put("sumBefore", costBefore);
			return respMap;
		} else {
			return null;
		}

	}

	/**
	 * 检查UUID是否已存在
	 * 
	 * @param uuid
	 * @return
	 */
	private boolean checkUuidIsExistedInDb(String uuid) {
		boolean isExisted = false;
		if (StringUtils.isNotBlank(uuid)) {
			CompanyExample example = new CompanyExample();
			example.createCriteria().andUuidEqualTo(uuid);
			int count = companyMapper.countByExample(example);
			if (count > 0) {
				isExisted = true;
			}
		}
		return isExisted;
	}

	private boolean checkNameIsexistedInDb(Company company) {
		boolean isExisted = false;
		if (StringUtils.isNotBlank(company.getFullName()) && company.getType() != null) {
			CompanyExample example = new CompanyExample();
			CompanyExample.Criteria criteria = example.createCriteria();
			criteria.andFullNameEqualTo(company.getFullName()).andTypeEqualTo(company.getType())
					.andStatusEqualTo(Constants.STATE_VALID);
			if (WebConstants.COMPANY_TYPE_ADVER == company.getType()) {
				criteria.andAidEqualTo(company.getAid());
			}
			int count = companyMapper.countByExample(example);
			if (count > 0) {
				isExisted = true;
			}
		}
		return isExisted;
	}

	private CompanyExample bindingExample(Company company) {
		CompanyExample example = new CompanyExample();
		if (company != null) {
			CompanyExample.Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(company.getFullName())) {
				criteria.andFullNameLike("%" + company.getFullName() + "%");
			}
			if (StringUtils.isNotBlank(company.getShortName())) {
				criteria.andShortNameLike("%" + company.getShortName() + "%");
			}
			if (company.getIndustryType() != null) {
				criteria.andIndustryTypeEqualTo(company.getIndustryType());
			}
			if (company.getStatus() != null) {
				criteria.andStatusEqualTo(company.getStatus());
			}
			if (company.getIsDelete() != null) {
				criteria.andIsDeleteEqualTo(company.getIsDelete());
			} else {
				criteria.andIsDeleteEqualTo(Constants.STATE_INVALID);
			}
			if (company.getType() != null) {
				criteria.andTypeEqualTo(company.getType());

			}
			if (company.getCreateUser() != null && WebConstants.COMPANY_TYPE_AGENT == company.getType()) {
				criteria.andCreateUserEqualTo(company.getCreateUser());
			}
		}
		example.setOrderByClause(" id desc ");
		return example;
	}

	private List<Integer> getCompanyIdListByGrandUid(Integer uid) {
		List<Integer> companyIds = Lists.newArrayList();
		UserGrandExample grandExample = new UserGrandExample();
		grandExample.createCriteria().andTypeEqualTo(WebConstants.AGENT_OBJECT_TYPE_ADVER).andUidEqualTo(uid)
				.andStatusEqualTo(Constants.STATE_VALID);
		List<UserGrand> grandList = userGrandMapper.selectByExample(grandExample);
		if (!CollectionUtils.isEmpty(grandList)) {
			List<Integer> aidList = Lists.transform(grandList, UserGrand::getAid);
			UserExample userExample = new UserExample();
			userExample.createCriteria().andIdIn(aidList).andStatusEqualTo(Constants.STATE_VALID);
			List<User> userList = userMapper.selectByExample(userExample);
			companyIds = Lists.transform(userList, User::getCompany);
		}
		return companyIds;
	}

	private void fillList(List<Company> list) {
		if (!CollectionUtils.isEmpty(list)) {
			Company agent = null;
            Industry industry = null;
			StringBuilder auditStr;
			for (Company company : list) {
                auditStr = new StringBuilder();
				if (company != null) {
					agent = get(company.getAid());
					if (agent != null) {
						company.setAgName(agent.getFullName());
					}
					industry = industryMapper.selectByPrimaryKey(company.getIndustryType());
					if (industry != null) {
						company.setIndustry(industry.getName());
					}
					AdxRelation relation = new AdxRelation();
					relation.setObjType(Constants.OBJ_ADVERTISER);
					relation.setObjId(company.getId());
					relation.setStatus(Constants.STATE_VALID);
					List<AdxRelation> relations = adxRelationService.selectList(relation);
					if (!CollectionUtils.isEmpty(relations)) {
                        for (AdxRelation adxRelation : relations) {
                            FlowSource media = flowSourceMapper.selectByPrimaryKey(adxRelation.getAdxType());
                            auditStr.append(media.getMediaName());
                            auditStr.append(Constants.SIGN_COLON);
                            if (Constants.STATE_VALID == adxRelation.getAuditState()) {
                                auditStr.append("通过");
                            } else if (Constants.STATE_WAIT_AUDIT == adxRelation.getAuditState()) {
                                auditStr.append("待审核");
                            } else if (Constants.STATE_FAILURE_AUDIT == adxRelation.getAuditState()) {
                                auditStr.append("未通过");
                            } else if (Constants.STATE_REJECT_AUDIT == adxRelation.getAuditState()) {
                                auditStr.append("未上传");
                            } else {
                                auditStr.append("未提审");
                            }
                            auditStr.append(Constants.SIGN_SEMICOLON);
                        }
                        company.setAuditComment(auditStr.toString());
					}
				}
			}
		}
	}

	@Override
	public List<Integer> getCompanyIdsByWithUid(Integer uid) {
		return getCompanyIdListByGrandUid(uid);
	}
	
	@Override
	public List<Integer> getAiKaCompanyIdsByWithUid(Integer uid) {
		List<Integer> companyIds = getCompanyIdListByGrandUid(uid);
		CompanyExample companyExample = new CompanyExample();
		companyExample.createCriteria().andIdIn(companyIds);
		List<Company> companies = companyMapper.selectByExample(companyExample);
		return Lists.transform(companies, Company::getOutCid);
	}

	@Override
	public Integer getAiKaCompanyIdByWithUid(Integer uid) {
		Integer companyId = userMapper.selectByPrimaryKey(uid).getCompany();
		return companyId;
	}

	@Override
	public Page<Company> auditPages(Company company, Integer cp, Integer ps) {
		Page<Company> page;
		int count = adxRelationMapper.selectCountAuditAdver(company, Constants.FLOW_MEDIA_AIKA,
				Constants.OBJ_ADVERTISER);
		if (count > 0) {
			page = new Page<>(count, cp, ps);
			List<Company> companies = adxRelationMapper.selectAuditAdverPage(company, Constants.FLOW_MEDIA_AIKA,
					Constants.OBJ_ADVERTISER, page.getStartPosition(), ps);
			page.setData(companies);
		} else {
			page = new Page<>(count);
		}
		return page;
	}

	@Override
	public Company auditInfo(Integer id) {
		Company company = companyMapper.selectByPrimaryKey(id);
		AdxRelationExample adxRelationExample = new AdxRelationExample();
		adxRelationExample.createCriteria().andAdxTypeEqualTo(Constants.OBJ_ADVERTISER)
				.andStatusEqualTo(Constants.STATE_VALID).andAdxTypeEqualTo(Constants.FLOW_MEDIA_AIKA)
				.andObjIdEqualTo(company.getId());
		List<AdxRelation> adxRelations = adxRelationMapper.selectByExample(adxRelationExample);
		if (adxRelations != null && adxRelations.size() > 0) {
			//填充审核信息
			AdxRelation adxRelation = adxRelations.get(0);
			company.setAuditStatus(adxRelation.getAuditState());
			company.setAuditComment(adxRelation.getAuditComments());
		}
		if (company.getIndustryType() != null) {
			Industry industry = industryMapper.selectByPrimaryKey(company.getIndustryType());
			company.setIndustry(industry.getName());
		}
		return company;
	}

	private void updateSysCrontab(Company company) {
		Company oldCompany = companyMapper.selectByPrimaryKey(company.getId());
		Integer objType = null;
		if (WebConstants.COMPANY_TYPE_ADVER == oldCompany.getType()) {
			objType = Constants.OBJ_ADVERTISER;
		}else if (WebConstants.COMPANY_TYPE_AGENT == oldCompany.getType()) {
			objType = Constants.OBJ_AGENT;
		}
		if (objType != null) {
			sysCrontabService.addSysCrontabCheckCount(company.getId(), Constants.OBJ_ADVERTISER, Constants.OP_UPDATE);
		}
	}

}
