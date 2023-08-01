package com.dongle.sys.sec.kill.filter;

import com.dongle.sys.sec.kill.config.SecKillActivity;
import com.dongle.sys.sec.kill.model.User;

public class BlackFilter implements Filter{
    @Override
    public boolean filter(User user, SecKillActivity activity) {
        return true;
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
