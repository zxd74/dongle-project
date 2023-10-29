package com.iwanvi.adserv.ngx.router.broker.liebao;

import com.iwanvi.adserv.ngx.cfg.MinervaCfg;
import com.iwanvi.adserv.ngx.router.WinPriceCodec;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author: 郑晓东
 * @date: 2019-06-11 11:33
 * @version: v1.0
 * @Description:
 */
public class LieBaoWinPriceCodec implements WinPriceCodec {

    private static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final String ALGORITHM = "AES";

    // 猎豹价格加密秘钥
    private static final String key = MinervaCfg.get().getConfigProperty("liebao.ekey");


    @Override
    public double decode(String price, String token) {
        return Double.valueOf(price);
    }

    @Override
    public String encode(double price, String token) {
        //猎豹转换价格单位从分到元
        return String.valueOf(price/100d);
    }

}
