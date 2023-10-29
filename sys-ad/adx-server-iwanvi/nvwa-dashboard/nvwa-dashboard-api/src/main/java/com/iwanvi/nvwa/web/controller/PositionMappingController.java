package com.iwanvi.nvwa.web.controller;

import com.iwanvi.nvwa.dao.model.AdPosition;
import com.iwanvi.nvwa.dao.model.AdPositionMapping;
import com.iwanvi.nvwa.web.service.PositionMapperingService;
import com.iwanvi.nvwa.web.util.NvwaRespBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/position-mapping")
public class PositionMappingController {

    @Autowired
    private PositionMapperingService positionMapperingService;

    @NvwaRespBody
    @PostMapping("/list")
    public List<AdPositionMapping> getList(@RequestBody AdPositionMapping adPositionMapping) {
        return positionMapperingService.selectForList(adPositionMapping);
    }


}
