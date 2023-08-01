package com.dongle.sys.sec.kill.config;

import com.dongle.sys.sec.kill.filter.Filter;

import java.util.*;

public class ResourceConfig {

    public static List<Filter> getFilters(){
        List<Filter> filters = new ArrayList<>();
        ServiceLoader<Filter> loader = ServiceLoader.load(Filter.class);
        loader.forEach(filters::add);
        filters.sort(Comparator.comparingInt(Filter::getOrder));
        return filters;
    }
}
