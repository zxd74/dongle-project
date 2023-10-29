package com.fftime.ffmob.aggregation.ads;

import com.fftime.ffmob.aggregation.baidu.BaiduADFactory;
import com.fftime.ffmob.aggregation.fftime.FFTADFactory;
import com.fftime.ffmob.aggregation.gdt.GDTADFactory;
import com.fftime.ffmob.aggregation.toutiao.TTADFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ADFactorys {
    private static final ConcurrentMap</**广告平台标识*/String, /**广告工厂*/ADFactory> AD_FACTORY_REGISTRY = new ConcurrentHashMap<>();
    static {
        register(new BaiduADFactory());
        register(new GDTADFactory());
        register(new TTADFactory());
        register(new FFTADFactory());
    }
    public static void register(ADFactory adFactory){
        AD_FACTORY_REGISTRY.putIfAbsent(adFactory.getName(),adFactory);
    }


    public static ADFactory getADFactory(String id) {
        return AD_FACTORY_REGISTRY.get(id);
    }
}
