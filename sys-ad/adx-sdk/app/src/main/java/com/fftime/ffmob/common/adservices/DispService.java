package com.fftime.ffmob.common.adservices;

import java.util.Arrays;
import java.util.Collection;

import com.fftime.ffmob.common.network.NetClient;
import com.fftime.ffmob.common.network.PlainNetRequest;
import com.fftime.ffmob.common.network.NetClient.Priority;
import com.fftime.ffmob.common.network.NetRequest.Method;
import com.fftime.ffmob.util.Constants;

public class DispService {
    private static final class Holder {
        private static final DispService INSTANCE = new DispService();
    }

    public static final DispService getInstance() {
        return Holder.INSTANCE;
    }

    private DispService() {
    }

    public void disp(Collection<String> dispURLS) {
        for (String dispURL : dispURLS) {
            ClickService.getInstance().dlmUrl(dispURL);
        }
    }

    public void disp(String dispurl) {
        this.disp(Arrays.asList(new String[]{dispurl}));
    }
}
