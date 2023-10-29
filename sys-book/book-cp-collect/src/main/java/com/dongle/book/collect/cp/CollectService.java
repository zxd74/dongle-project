package com.dongle.book.collect.cp;

import com.dongle.book.collect.model.BookChapter;
import com.dongle.book.collect.model.BookInfo;

import java.util.List;

/**
 * @author Kevin
 */
public interface CollectService {

    /**
     * 通过CP的图书ID查询采集图书
     * @param cpBookId
     * @return
     */
    List<BookInfo> queryBookInfo(String cpBookId);

    /**
     * 查询图书章节列表
     * @param cpBookId
     * @return
     */
    List<BookChapter> queryBookChapterList(String cpBookId);

    /**
     * 查询图书章节详情
     * @param cpBookId
     * @param cpChapterId
     * @return
     */
    BookChapter queryBookChapterInfo(String cpBookId,String cpChapterId);
}
