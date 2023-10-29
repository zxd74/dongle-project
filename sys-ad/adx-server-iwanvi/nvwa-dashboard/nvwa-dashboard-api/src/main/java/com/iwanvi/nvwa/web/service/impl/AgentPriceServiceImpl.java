package com.iwanvi.nvwa.web.service.impl;

import com.iwanvi.nvwa.common.exception.ServiceException;
import com.iwanvi.nvwa.common.utils.Constants;
import com.iwanvi.nvwa.dao.AdPositionMapper;
import com.iwanvi.nvwa.dao.AgentPriceMapper;
import com.iwanvi.nvwa.dao.FlowSourceMapper;
import com.iwanvi.nvwa.dao.model.*;
import com.iwanvi.nvwa.dao.model.support.Page;
import com.iwanvi.nvwa.web.service.AdDicService;
import com.iwanvi.nvwa.web.service.AgentPriceService;
import com.iwanvi.nvwa.web.service.AreaGroupService;
import com.iwanvi.nvwa.web.service.CompanyService;
import com.iwanvi.nvwa.web.util.WebConstants;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AgentPriceServiceImpl implements AgentPriceService {

	@Autowired
	private AgentPriceMapper agentPriceMapper;
	@Autowired
	private AreaGroupService areaGroupService;
	@Autowired
	private AdDicService adDicService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private AdPositionMapper adPositionMapper;
	@Autowired
	private FlowSourceMapper flowSourceMapper;

	@Transactional
	@Override
	public void add(AgentPrice agentPrice) {
		List<AreaGroup> areas = areaGroupService.selectForList(null);
		List<Dictionary> industries = adDicService.getDicByGroupId(Constants.ADVERTIDER_INDUSTRY);
//        List<Dictionary> osList = adDicService.getDicByGroupId(WebConstants.OS_TYPE);

		if (agentPrice != null && agentPrice.getPid() != null && agentPrice.getPrice() != null
				&& agentPrice.getProfitMargin() != null) {
			if (agentPrice.getAid() == null) {
				agentPrice.setSysFlag(Constants.STATE_VALID);
			} else {
				agentPrice.setSysFlag(Constants.STATE_INVALID);
			}
			agentPrice.setStatus(Constants.STATE_VALID);
			for (AreaGroup area : areas) {
				for (Dictionary industry : industries) {
//                    for (Dictionary os : osList) {
//                        agentPrice.setOs(os.getId());
					agentPrice.setAreaLevel(area.getId());
					agentPrice.setIndustryid(industry.getId());
					agentPrice.setPayType(WebConstants.SELL_TYPE_CPC);
					if (!checkPriceInDb(agentPrice)) {
						agentPriceMapper.insertSelective(agentPrice);
						// 如果是系统底价 则同步给代理商
						if (Constants.STATE_VALID == agentPrice.getSysFlag()) {
							syncAgentPrice(agentPrice);
						}
					}

					agentPrice.setPayType(WebConstants.SELL_TYPE_CPM);
					if (!checkPriceInDb(agentPrice)) {
						agentPriceMapper.insertSelective(agentPrice);
						// 如果是系统底价 则同步给代理商
						if (Constants.STATE_VALID == agentPrice.getSysFlag()) {
							syncAgentPrice(agentPrice);
						}
					}
//                    }
				}
			}
		} else {
			throw new ServiceException("广告位、底价、利润率均必填。");
		}
	}

	@Transactional
	@Override
	public void update(AgentPrice agentPrice) {
		if (agentPrice != null && agentPrice.getId() != null) {
			agentPrice.setAid(null);
			agentPrice.setMid(null);
			agentPrice.setPid(null);
			agentPrice.setOs(null);
			agentPrice.setAreaLevel(null);
			agentPrice.setIndustryid(null);
			agentPrice.setSysFlag(null);
			agentPrice.setPayType(null);
			agentPriceMapper.updateByPrimaryKeySelective(agentPrice);
		}
	}

	@Transactional
	@Override
	public void delete(Integer id) {
		AgentPrice agentPrice = new AgentPrice();
		agentPrice.setId(id);
		agentPrice.setStatus(Constants.STATE_INVALID);
		agentPriceMapper.updateByPrimaryKeySelective(agentPrice);
	}

	@Override
	public AgentPrice get(Integer id) {
		if (id != null) {
			return agentPriceMapper.selectByPrimaryKey(id);
		}
		return null;
	}

	@Override
	public List<AgentPrice> selectForList(AgentPrice agentPrice) {
		AgentPriceExample example = bindingExample(agentPrice);
		return agentPriceMapper.selectByExample(example);
	}

	@Override
	public Page<AgentPrice> selectForPage(AgentPrice agentPrice, Integer cp, Integer ps) {
		Page<AgentPrice> page;
		AgentPriceExample example = bindingExample(agentPrice);
		int count = agentPriceMapper.countByExample(example);
		List<AgentPrice> list = Lists.newArrayList();
		if (cp != null && ps != null) {
			page = new Page<AgentPrice>(count, cp, ps);
		} else {
			page = new Page<AgentPrice>(count);
		}
		example.setStart(page.getStartPosition());
		example.setLimit(page.getPageSize());
		list = agentPriceMapper.selectAgentPrice(example);
		page.bindData(list);
		return page;
	}

	@Transactional
	@Override
	public void syncPrice(Integer id) {
		if (id != null) {
			AgentPrice agentPrice = agentPriceMapper.selectByPrimaryKey(id);
			agentPrice.setAid(null);
			agentPrice.setSysFlag(Constants.STATE_INVALID);
			AgentPriceExample example = bindingExample(agentPrice);
			AgentPrice newPrice = new AgentPrice();
			newPrice.setPrice(agentPrice.getPrice());
			agentPriceMapper.updateByExampleSelective(newPrice, example);
		}
	}

	@Transactional
	@Override
	public void syncProfit(Integer id) {
		if (id != null) {
			AgentPrice agentPrice = agentPriceMapper.selectByPrimaryKey(id);
			agentPrice.setAid(null);
			agentPrice.setSysFlag(Constants.STATE_INVALID);
			AgentPriceExample example = bindingExample(agentPrice);
			AgentPrice newPrice = new AgentPrice();
			newPrice.setProfitMargin(agentPrice.getProfitMargin());
			agentPriceMapper.updateByExampleSelective(newPrice, example);
		}
	}

	@Transactional
	@Override
	public void copyPrice(Integer aid) {
		if (aid != null) {
			AgentPrice price = new AgentPrice();
			price.setSysFlag(Constants.STATE_VALID);
			List<AgentPrice> sysPrices = selectForList(price);
			for (AgentPrice sysPrice : sysPrices) {
				sysPrice.setAid(aid);
				sysPrice.setId(null);
				agentPriceMapper.insertSelective(sysPrice);
			}
		}
	}

	private Integer getMediaIdByPid(Integer pid) {
		if (pid != null) {
			AdPosition position = adPositionMapper.selectByPrimaryKey(pid);
			String mediaUuid = position.getFlowUuid();
			FlowSourceExample example = new FlowSourceExample();
			example.createCriteria().andMediaUuidEqualTo(mediaUuid);
			List<FlowSource> mediaList = flowSourceMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(mediaList)) {
				return mediaList.get(Constants.INTEGER_0).getId();
			}
		}
		return null;
	}

	@Transactional
	@Override
	public void addPriceWithArea(Integer areaId) {
		if (areaId != null) {
			List<Integer> pids = agentPriceMapper.selectSysPricePids();
			List<Dictionary> industries = adDicService.getDicByGroupId(Constants.ADVERTIDER_INDUSTRY);
			AgentPrice price;
			for (Integer pid : pids) {
				for (Dictionary industry : industries) {
					price = new AgentPrice();
					price.setPid(pid);
					price.setMid(getMediaIdByPid(pid));
					price.setIndustryid(industry.getId());
					price.setAreaLevel(areaId);
					price.setSysFlag(Constants.STATE_VALID);
					price.setPayType(WebConstants.SELL_TYPE_CPC);
					price.setPrice(Constants.INTEGER_1);
					price.setProfitMargin(0.1d);
					agentPriceMapper.insertSelective(price);
					syncAgentPrice(price);

					price.setPayType(WebConstants.SELL_TYPE_CPM);
					agentPriceMapper.insertSelective(price);
					syncAgentPrice(price);
				}
			}
		}
	}

	@Transactional
	@Override
	public void addPriceWithIndustry(Integer industry) {
		if (industry != null) {
			List<Integer> pids = agentPriceMapper.selectSysPricePids();
			List<AreaGroup> areas = areaGroupService.selectForList(null);
			AgentPrice price;
			for (Integer pid : pids) {
				for (AreaGroup area : areas) {
					price = new AgentPrice();
					price.setPid(pid);
					price.setMid(getMediaIdByPid(pid));
					price.setIndustryid(industry);
					price.setAreaLevel(area.getId());
					price.setSysFlag(Constants.STATE_VALID);
					price.setPayType(WebConstants.SELL_TYPE_CPM);
					price.setPrice(Constants.INTEGER_1);
					price.setProfitMargin(0.1d);
					agentPriceMapper.insertSelective(price);
					syncAgentPrice(price);

					price.setPayType(WebConstants.SELL_TYPE_CPC);
					agentPriceMapper.insertSelective(price);
					syncAgentPrice(price);
				}
			}
		}
	}

	@Transactional
	@Override
	public void rmPriceWithArea(Integer areaId) {
		if (areaId != null) {
			AgentPriceExample example = new AgentPriceExample();
			example.createCriteria().andAreaLevelEqualTo(areaId);
			agentPriceMapper.deleteByExample(example);
		}
	}

	@Transactional
	@Override
	public void rmPriceWithIndustry(Integer industry) {
		if (industry != null) {
			AgentPriceExample example = new AgentPriceExample();
			example.createCriteria().andIndustryidEqualTo(industry);
			agentPriceMapper.deleteByExample(example);
		}
	}

    @Override
    public void addPriceByAid(Integer aid) {
        if (aid != null) {
            AgentPriceExample example = new AgentPriceExample();
            example.createCriteria().andSysFlagEqualTo(Constants.STATE_VALID).andStatusEqualTo(Constants.STATE_VALID);
            List<AgentPrice> sysPriceList = agentPriceMapper.selectByExample(example);
            for (AgentPrice sysPrice : sysPriceList) {
                sysPrice.setId(null);
                sysPrice.setAid(aid);
                sysPrice.setSysFlag(Constants.STATE_INVALID);
                agentPriceMapper.insertSelective(sysPrice);
            }
        }
    }

    private AgentPriceExample bindingExample(AgentPrice agentPrice) {
		AgentPriceExample example = new AgentPriceExample();
		if (agentPrice != null) {
			AgentPriceExample.Criteria criteria = example.createCriteria();
			if (agentPrice.getIndustryid() != null) {
				criteria.andIndustryidEqualTo(agentPrice.getIndustryid());
			}
			if (agentPrice.getPid() != null) {
				criteria.andPidEqualTo(agentPrice.getPid());
			} else {
				AdPositionExample pExample = new AdPositionExample();
				AdPositionExample.Criteria pCriteria = pExample.createCriteria();
//                if (agentPrice.getOs()!= null) {
//                    pCriteria.andTerminalEqualTo(agentPrice.getOs());
//                }
				if (agentPrice.getMid() != null) {
					FlowSource media = flowSourceMapper.selectByPrimaryKey(agentPrice.getMid());
					if (media != null) {
						pCriteria.andFlowUuidEqualTo(media.getMediaUuid());
					}
				}
				pCriteria.andStatusEqualTo(Constants.STATE_VALID);
				List<AdPosition> positions = adPositionMapper.selectByExample(pExample);
				List<Integer> pids = FluentIterable.from(positions).transform((AdPosition position) -> {
					return position.getId();
				}).toList();
				if (!CollectionUtils.isEmpty(pids)) {
					criteria.andPidIn(pids);
				} else {
					criteria.andPidIn(Arrays.asList(0));
				}
			}
			if (agentPrice.getMid() != null) {
				criteria.andMidEqualTo(agentPrice.getMid());
			}
			if (agentPrice.getOs() != null) {
				criteria.andOsEqualTo(agentPrice.getOs());
			}
			if (agentPrice.getSysFlag() != null) {
				criteria.andSysFlagEqualTo(agentPrice.getSysFlag());
			}
			if (agentPrice.getAid() != null) {
				criteria.andAidEqualTo(agentPrice.getAid());
			}
			if (agentPrice.getAreaLevel() != null) {
				criteria.andAreaLevelEqualTo(agentPrice.getAreaLevel());
			}
			if (agentPrice.getPayType() != null) {
				criteria.andPayTypeEqualTo(agentPrice.getPayType());
			}
			if (agentPrice.getStatus() != null) {
				criteria.andStatusEqualTo(agentPrice.getStatus());
			} else {
				criteria.andStatusEqualTo(Constants.STATE_VALID);
			}
		}
		example.setOrderByClause(" id desc ");
		return example;
	}

	private boolean checkPriceInDb(AgentPrice price) {
		boolean isExisted = false;
		AgentPriceExample example = bindingExample(price);
		int count = agentPriceMapper.countByExample(example);
		if (count > Constants.INTEGER_0) {
			isExisted = true;
		}
		return isExisted;
	}

	/**
	 * 系统底价复制给代理商
	 * 
	 * @param sysPrice
	 */
	private void syncAgentPrice(AgentPrice sysPrice) {
		AgentPrice price = new AgentPrice();
		BeanUtils.copyProperties(sysPrice, price);
		Company company = new Company();
		company.setType(WebConstants.COMPANY_TYPE_AGENT);
		List<Company> agents = companyService.selectForList(company);
		price.setSysFlag(Constants.STATE_INVALID);
		for (Company agent : agents) {
			if (agent != null && agent.getId() != null) {
				price.setAid(agent.getId());
				agentPriceMapper.insertSelective(price);
			}
		}
	}
}
