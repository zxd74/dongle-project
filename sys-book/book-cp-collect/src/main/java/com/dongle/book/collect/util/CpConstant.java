package com.dongle.book.collect.util;

import com.dongle.book.collect.model.CpEnum;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author Dongle
 * @desc
 * @since 2021/7/31 9:37
 */
public class CpConstant {

    public static final String BOOK_SERVICE_ADDRESS = "http://book-platform-sync-service.cread.com/";
    public static final String BOOK_SERVICE_QUERY_ADDRESS = BOOK_SERVICE_ADDRESS + "query";
    public static final String BOOK_SERVICE_PUSH_ADDRESS = BOOK_SERVICE_ADDRESS + "push";
    public static final String BOOK_SERVICE_QUERY_BOOK_BY_CP_ADDRESS = BOOK_SERVICE_ADDRESS + "queryBookListByCpIds";
    public static final String BOOK_SERVICE_QUERY_BOOK_BY_ID_ADDRESS = BOOK_SERVICE_ADDRESS + "queryBookByBookId?bookId=";
    public static final String BOOK_SERVICE_QUERY_ADD_BOOK_BY_CP_ADDRESS = BOOK_SERVICE_ADDRESS + "queryBookListToAddByCpIds";
    public static final String BOOK_SERVICE_QUERY_UPDATE_BOOK_BY_CP_ADDRESS = BOOK_SERVICE_ADDRESS + "queryBookListToReGraspByCpIds";
    public static final String BOOK_SERVICE_UPDATE_OPT_RECODE_ADDRESS = BOOK_SERVICE_ADDRESS + "updateOperationRecord";
    public static final String TIME = "480";

    public static final List<String> CP_ID_LIST = Arrays.stream(CpEnum.values()).map(CpEnum::getCpId).collect(Collectors.toList());
    public static final String CP_CLASS_BASE_PACKAGE = "com.dongle.book.collect.cp.impl";

    public static final String JSON_FIELD_BOOK_ID = "bookid";
    public static final String JSON_FIELD_ERR_CODE = "errcode";
    public static final String JSON_FIELD_DATA = "data";
    public static final String JSON_FIELD_AUTHOR_NAME = "author_name";

    public static final String DEFAULT_ERROR_CODE = "-1";

    public static final String CP_DANG_DANG = "10000";
}
