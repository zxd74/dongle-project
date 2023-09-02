package com.dongle.stack.controller;

import com.dongle.stack.model.StockGroupModel;
import com.dongle.stack.service.StockGroupService;
import com.dongle.stack.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Dongle
 * @desc
 * @since 2023/9/1 13:13
 */
@RestController
@RequestMapping("api/stock-group")
public class StockGroupManage {

    @Autowired
    private StockGroupService groupService;
    @GetMapping("all")
    public List<StockGroupModel> all(){
        return groupService.all();
    }

    @PostMapping("options")
    public boolean options(int options,@RequestBody StockGroupModel model){
        boolean flag = true;
        if (options == Constants.OPTIONS_ADD || options == Constants.OPTIONS_UPDATE) flag = groupService.save(model);
        else if (options == Constants.OPTIONS_DELETE) flag = groupService.delete(model);
        return flag;
    }

    @GetMapping("/group-all")
    public StockGroupModel groupAll(int groupId){
        return groupService.groupAll(groupId);
    }

    @PostMapping("add-group-stock")
    public boolean addGroupStock(@RequestBody StockGroupModel model){
        if (model == null || model.getGroupId()<=0 || model.getStocks() == null || model.getStocks().isEmpty()) return false;
        return groupService.addGroupStock(model);
    }

    @PostMapping("del-group-stock")
    public boolean deleteGroupStock(@RequestBody StockGroupModel model){
        return groupService.deleteGroupStock(model);
    }
}
