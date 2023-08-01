package com.dongle.sys.sec.kill.filter;

import com.dongle.sys.sec.kill.config.SecKillActivity;
import com.dongle.sys.sec.kill.model.User;
import com.sun.istack.internal.NotNull;

public interface Filter {

    boolean filter(@NotNull User user,@NotNull SecKillActivity activity);

    int getOrder();
}
