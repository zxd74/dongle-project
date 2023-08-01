package com.dongle.sys.sec.kill.service;

import com.dongle.sys.sec.kill.config.ResourceConfig;
import com.dongle.sys.sec.kill.config.SecKillActivity;
import com.dongle.sys.sec.kill.filter.Filter;
import com.dongle.sys.sec.kill.model.User;

import java.util.List;

public class SecKillService {

    private final SecKillActivity secKillActivity;
    private final List<Filter> filters;

    public SecKillService(SecKillActivity secKillActivity) {
        this.secKillActivity = secKillActivity;
        this.filters = ResourceConfig.getFilters();
    }

    public synchronized void grad(User user){
        boolean flag = true;
        for (Filter filter:this.filters){
            if (!(flag = filter.filter(user, secKillActivity))){
                break;
            }
        }
        if (flag){
            secKillActivity.grad(user.getId());
        }
        System.out.println(user.getId() + "抢完之后，产品剩余及已售数量：" + secKillActivity.getProductCount() + "," + secKillActivity.getResidue());
    }
}
