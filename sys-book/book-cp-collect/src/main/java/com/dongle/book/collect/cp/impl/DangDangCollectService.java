package com.dongle.book.collect.cp.impl;

import com.dongle.book.collect.cp.CollectService;
import com.dongle.book.collect.model.BookChapter;
import com.dongle.book.collect.model.BookInfo;

import java.util.List;

/**
 * @author Dongle
 * @desc
 * @since 2021/7/31 9:40
 */
public class DangDangCollectService implements CollectService {

    private static final String URL = "";

    @Override
    public List<BookInfo> queryBookInfo(String cpBookId) {
        // TODO 请求图书信息
        return null;
    }

    @Override
    public List<BookChapter> queryBookChapterList(String cpBookId) {
        // TODO 请求图书章节列表
        return null;
    }

    @Override
    public BookChapter queryBookChapterInfo(String cpBookId, String cpChapterId) {
        // TODO 请求图书章节详情
        return null;
    }
}
