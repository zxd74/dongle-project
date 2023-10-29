package com.dongle.book.collect.service;

import com.dongle.book.collect.model.BookChapter;
import com.dongle.book.collect.model.BookInfo;
import com.dongle.book.collect.util.CpConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dongle
 * @desc 推送服务：采集CP图书及图书校验
 * @since 2021/7/31 10:04
 */
public class PushService {

    private static final Logger LOGGER = LoggerFactory.getLogger("PushService");

    private final String cpId;
    private final String name;
    private Class<?> clazz;

    public PushService(String cpId,String name,String url) {
        this.cpId = cpId;
        this.name = name;
        try {
            this.clazz = Class.forName(CpConstant.CP_CLASS_BASE_PACKAGE + url);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void pushBook(String cpBookId){
        List<BookInfo> bookList = queryBookInfo(cpBookId);
        if (CollectionUtils.isEmpty(bookList)){
            LOGGER.warn("此书已下架");
            return;
        }
        // TODO 保存图书信息
        SyncService.saveBook(bookList);
        // TODO 查询章列表
        List<BookChapter> chapterList = queryBookChapter(cpBookId);
        if (CollectionUtils.isEmpty(chapterList)){
            return;
        }
        // TODO 检查是否有需要更新的章节
        List<BookChapter> oldChapterList = SyncService.queryBookChapter(cpId,cpBookId);
        boolean isUpdateChapter = false;
        if (CollectionUtils.isEmpty(oldChapterList)){
            isUpdateChapter = true;
        }
        if (chapterList.size()!=oldChapterList.size()){
            isUpdateChapter = true;
        }
        // 需要更新的图书章节ID
        List<String> updateChapterIdList = new ArrayList<>();
        for (BookChapter chapter:chapterList){
            // 检查章节是否存在
            boolean isHave = false;
            for (BookChapter oldChapter:oldChapterList){
                // TODO 理论图书章节应该有章节ID，假如没有怎么处理
                if (chapter.getCpChapterId()!=null && chapter.getCpBookId().equals(oldChapter.getCpChapterId())){
                    isHave = true;
                    if (oldChapter.getWordCount()<=0){
                        isUpdateChapter = true;
                        // 当DB库中章节内容为空，则建议重新拉取更新
                        updateChapterIdList.add(oldChapter.getCpChapterId());
                    }else if (!oldChapter.getCpChapterName().equals(chapter.getCpChapterName())){
                        isUpdateChapter = true;
                    }
                    break;
                }
            }
            if (!isHave){
                isUpdateChapter = true;
                updateChapterIdList.add(chapter.getCpChapterId());
            }
        }
        // 检查是否需要更新章节
        if (!isUpdateChapter){
            LOGGER.info("CP:"+ name+",ID：" + cpId +",图书：【"+cpBookId+"】无需更新");
            return;
        }

        // TODO 重推章节列表
        SyncService.saveBookChapter(chapterList,cpBookId);
        // TODO 推送章节内容
        List<BookChapter> chapterInfoList = new ArrayList<>();
        for (String cpChapterId:updateChapterIdList){
            // 获取章节内容
            BookChapter chapterInfo = SyncService.queryBookChapterInfo(cpBookId,cpChapterId);
            if (chapterInfo!=null){
                // 如果以从sync查询到
                chapterInfo.setCpChapterId(cpChapterId);
                chapterInfoList.add(chapterInfo);
            }
        }
        // 新增章节内容
        if (chapterInfoList.size()>0){
            SyncService.saveBookChapterInfo(chapterInfoList,cpBookId);
            LOGGER.info(cpId+"__"+cpBookId+"__本次更新" + chapterInfoList.size() + "章内容成功");
        }

        LOGGER.info(cpId+"__"+cpBookId+"__本次更新" + updateChapterIdList.size() + "章成功");
    }

    /**
     * 获取图书信息
     * @param cpBookId
     * @return
     */
    private List<BookInfo> queryBookInfo(String cpBookId){
        try {
            Method method = clazz.getMethod("queryBookInfo",String.class);
            return (List<BookInfo>) method.invoke(clazz.newInstance(),cpBookId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获取CP方图书章节信息
     * @param cpBookId
     * @return
     */
    private List<BookChapter> queryBookChapter(String cpBookId){
        try {
            Method method = clazz.getMethod("queryBookChapterList",String.class);
            return (List<BookChapter>) method.invoke(clazz.newInstance(),cpBookId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获取CP方图书详情
     * @param cpBookId
     * @param cpChapterId
     * @return
     */
    private BookChapter queryBookChapterInfo(String cpBookId,String cpChapterId){
        try {
            Method method = clazz.getMethod("queryBookChapterList",String.class,String.class);
            return (BookChapter) method.invoke(clazz.newInstance(),cpBookId,cpChapterId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
