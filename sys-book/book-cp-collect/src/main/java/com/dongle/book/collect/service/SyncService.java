package com.dongle.book.collect.service;

import com.dongle.book.collect.model.BookChapter;
import com.dongle.book.collect.model.BookInfo;
import com.dongle.book.collect.util.CpConstant;
import com.dongle.book.collect.util.HttpUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dongle
 * @desc 同步服务，用于查询或更新已有图书信息
 * @since 2021/8/1 8:45
 */
public class SyncService {

//    public static SyncService INSTANCE = new SyncService();
//
//    private SyncService() {}
//
//    public static SyncService getSync(){
//        return INSTANCE;
//    }

    /**
     * TODO 根据CP查询采集图书列表
     * @param cpIds CP ID集合,逗号分隔
     * @return
     */
    public static List<BookInfo> queryBookListByCp(String cpIds){
        return null;
    }
    public static List<BookInfo> queryBookListByCps(String cpIds){
        return null;
    }

    /**
     * TODO 根据CP查询待新增图书列表
     * @param cpIds CP ID集合,逗号分隔
     * @return
     */
    public static List<BookInfo> queryAddBookListByCp(String cpIds){
        return null;
    }
    public static List<BookInfo> queryAddBookListByCps(String cpIds){
        List<BookInfo> bookInfoList = new ArrayList<>();
        String url = CpConstant.BOOK_SERVICE_QUERY_BOOK_BY_CP_ADDRESS + "?cp=" + cpIds + "&verify=1&time=" + CpConstant.TIME;
        JSONObject jsonObject = HttpUtils.readUrlToGet(url);
        int errCode = jsonObject.getInt(CpConstant.JSON_FIELD_ERR_CODE);
        if (errCode == 0) {
            JSONArray list = jsonObject.getJSONArray(CpConstant.JSON_FIELD_DATA);
            if (!CollectionUtils.isEmpty(list)){
                for (Object data:list){
                    JSONObject json = (JSONObject) data;
                    BookInfo bookInfo = new BookInfo();
                    // TODO 接收图书信息
                    bookInfo.setCpBookId(json.getString(CpConstant.JSON_FIELD_BOOK_ID));
                    bookInfo.setAuthorName(json.getString(CpConstant.JSON_FIELD_AUTHOR_NAME));
                    bookInfoList.add(bookInfo);
                }
            }
        }
        return bookInfoList;
    }

    /**
     * TODO 查询指定CP图书的章节列表
     * @param cpId
     * @param bookId
     * @return
     */
    public static List<BookChapter> queryBookChapter(String cpId,String bookId){
        return null;
    }

    /**
     * TODO 查询已有图书的章节详情
     * @param cpBookId
     * @param cpChapterId
     * @return
     */
    public static BookChapter queryBookChapterInfo(String cpBookId,String cpChapterId){
        return null;
    }

    /**
     * TODO 保存更新图书信息
     * @param bookList
     */
    public static void saveBook(List<BookInfo> bookList){

    }

    /**
     * TODO 更新图书章节列表
     * @param chapterIdList
     * @param cpBookId
     */
    public static void saveBookChapter(List<BookChapter> chapterIdList,String cpBookId){

    }

    /**
     * TODO 更新图书章节内容
     * @param chapterList
     * @param cpBookId
     */
    public static void saveBookChapterInfo(List<BookChapter> chapterList,String cpBookId){

    }
}
