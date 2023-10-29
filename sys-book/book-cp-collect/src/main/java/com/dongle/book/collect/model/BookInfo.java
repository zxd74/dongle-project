package com.dongle.book.collect.model;

/**
 * @author Dongle
 * @desc
 * @since 2021/7/31 13:22
 */
public class BookInfo {

    private String cpId;
    private String bookId;
    private String cpBookId;
    private String authorName;

    public String getCpId() {
        return cpId;
    }

    public void setCpId(String cpId) {
        this.cpId = cpId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getCpBookId() {
        return cpBookId;
    }

    public void setCpBookId(String cpBookId) {
        this.cpBookId = cpBookId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
