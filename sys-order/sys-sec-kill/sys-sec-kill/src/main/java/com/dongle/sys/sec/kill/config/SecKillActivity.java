package com.dongle.sys.sec.kill.config;

import com.dongle.sys.sec.kill.model.Product;
import com.dongle.sys.sec.kill.model.ProductLimit;
import com.sun.istack.internal.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class SecKillActivity {

    private final Product product;
    private final ProductLimit limit;

    private volatile long productCount;
    private volatile long residue;
    private final Map<String, AtomicLong> saleMap = new ConcurrentHashMap<>();

    public SecKillActivity(@NotNull Product product, ProductLimit limit) {
        this.product = product;
        this.productCount = product.getCount();
        this.limit = limit;
    }

    public synchronized void grad(String id){
        productCount--;
        residue++;
        if (!saleMap.containsKey(id)) saleMap.put(id,new AtomicLong());
        saleMap.get(id).incrementAndGet();
    }

    public long getProductCount() {
        return productCount;
    }

    public long getResidue() {
        return residue;
    }

    public int getUserProducts(String id){
        return saleMap.getOrDefault(id,new AtomicLong()).intValue();
    }

    public Product getProduct() {
        return product;
    }

    public ProductLimit getLimit() {
        return limit;
    }
}
