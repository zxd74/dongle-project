package com.dongle.sys.sec.kill.filter;

import com.dongle.sys.sec.kill.config.SecKillActivity;
import com.dongle.sys.sec.kill.model.User;

public class NumberFilter implements Filter{
    @Override
    public boolean filter(User user, SecKillActivity activity) {
        return activity.getUserProducts(user.getId())<activity.getLimit().getSingleNumber();
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
