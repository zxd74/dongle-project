package com.dongle.book.collect.task;

import com.dongle.book.collect.model.BookInfo;
import com.dongle.book.collect.model.CpEnum;
import com.dongle.book.collect.service.PushService;
import com.dongle.book.collect.service.SyncService;
import com.dongle.book.collect.util.CpConstant;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Dongle
 * @desc 图书采集定时任务
 * @since 2021/7/31 9:45
 */
public class BookCollectTask {

    /**
     * 更新CP图书
     */
    public void updateBook(){
        if (CollectionUtils.isEmpty(CpConstant.CP_ID_LIST)){
            return;
        }
        String cpIds = CpConstant.CP_ID_LIST.toString().replace("[","").replace("]","");
        // 根据CP ID获取图书列表
        List<BookInfo> bookList = SyncService.queryAddBookListByCps(cpIds);
        // 推送图书
        pushBook(bookList);
    }

    /**
     * 添加CP图书
     */
    public void addBook(){
        if (CollectionUtils.isEmpty(CpConstant.CP_ID_LIST)){
            return;
        }
        String cpIds = CpConstant.CP_ID_LIST.toString().replace("[","").replace("]","");
        // 根据CP ID获取待增图书列表
        List<BookInfo> bookList = SyncService.queryAddBookListByCps(cpIds);
        // 推送图书
        pushBook(bookList);
    }

    /**
     * 推送图书
     * @param bookList
     */
    private void pushBook(List<BookInfo> bookList){
        if (CollectionUtils.isEmpty(bookList)){
            // 无需要采集的图书
            return;
        }
        // TODO 2 根据CP和图书ID同步图书任务，多线程方式
        for (BookInfo book:bookList){
            String cpId = book.getCpId();
            CpEnum cpEnum = CpEnum.getCp(cpId);
            if (cpEnum == null){
                // 理论不存在无CP，单避免对接支持疏忽
                return;
            }
            PushService pushService = new PushService(cpId, cpEnum.getName(), cpEnum.getClazz());
            pushService.pushBook(book.getCpBookId());
        }
    }
}
