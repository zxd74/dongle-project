package com.dongle.adx.manager.ctroller;

import com.dongle.adx.common.util.ResponseUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : 郑晓东
 * @version : v1.0
 * @description ：平台管理
 * @since : 2019-10-14 18:34
 */
@RestController
@RequestMapping("/platform/manager")
public class PlatformManagerController {

    @GetMapping("/index")
    @ResponseBody
    public ResponseUtils index(){
        return new ResponseUtils();
    }
}
