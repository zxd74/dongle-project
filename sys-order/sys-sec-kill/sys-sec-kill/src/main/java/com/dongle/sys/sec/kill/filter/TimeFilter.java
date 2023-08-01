package com.dongle.sys.sec.kill.filter;

import com.dongle.sys.sec.kill.config.SecKillActivity;
import com.dongle.sys.sec.kill.model.ProductLimit;
import com.dongle.sys.sec.kill.model.User;
import com.dongle.sys.sec.kill.utils.DateUtils;

public class TimeFilter implements Filter{

    @Override
    public boolean filter(User user, SecKillActivity activity) {
        if (activity.getLimit() == null) return true;
        ProductLimit limit = activity.getLimit();
        if (limit.getStart() == null && limit.getEnd() == null) return true;
        if (limit.getStart() == null) return DateUtils.compare(limit.getEnd(),user.getDate());
        if (limit.getEnd() == null) return DateUtils.compare(user.getDate(),limit.getStart());
        return DateUtils.compare(user.getDate(),limit.getStart()) && DateUtils.compare(limit.getEnd(),user.getDate());
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
