package com.dongle.book.collect.model;

/**
 * @author Dongle
 * @desc
 * @since 2021/7/31 18:43
 */
public class BookChapter {

    private String cpBookId;
    private String cpChapterId;
    private String cpChapterName;
    private long wordCount;
    private String content;

    public String getCpBookId() {
        return cpBookId;
    }

    public void setCpBookId(String cpBookId) {
        this.cpBookId = cpBookId;
    }

    public String getCpChapterId() {
        return cpChapterId;
    }

    public void setCpChapterId(String cpChapterId) {
        this.cpChapterId = cpChapterId;
    }

    public String getCpChapterName() {
        return cpChapterName;
    }

    public void setCpChapterName(String cpChapterName) {
        this.cpChapterName = cpChapterName;
    }

    public long getWordCount() {
        return wordCount;
    }

    public void setWordCount(long wordCount) {
        this.wordCount = wordCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
