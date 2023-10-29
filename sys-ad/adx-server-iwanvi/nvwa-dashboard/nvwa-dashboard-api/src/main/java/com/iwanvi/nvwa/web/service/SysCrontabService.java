package com.iwanvi.nvwa.web.service;

import com.iwanvi.nvwa.dao.model.SysCrontab;

public interface SysCrontabService {
	
    boolean addSysCrontab(Integer objectId, Integer objectType, Integer opType);
    
    boolean addSysCrontabCheckCount(Integer objectId, Integer objectType, Integer opType);
    
    Integer insertSysCrontab(SysCrontab sysCrontab);
    
    int countCrontab(Integer objectId, Integer objectType);
    
    public boolean inserSysCrontab(Integer objectId, Integer objectType, Integer opType);
    
    boolean updateSysCrontabByEntity(Integer objectId, Integer objectType, Integer opType);
	
}
