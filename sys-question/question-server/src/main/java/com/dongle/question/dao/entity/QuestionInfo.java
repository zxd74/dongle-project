package com.dongle.question.dao.entity;

import java.util.ArrayList;
import java.util.Arrays;

public class QuestionInfo {
    private String qid;

    private byte[] content;

    private byte[] answer;

    public String getQid() {
        return qid;
    }

    public void setQid(String qid) {
        this.qid = qid;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte[] getAnswer() {
        return answer;
    }

    public void setAnswer(byte[] answer) {
        this.answer = answer;
    }

    public static QuestionInfo.Builder builder() {
        return new QuestionInfo.Builder();
    }

    public static class Builder {
        private QuestionInfo obj;

        public Builder() {
            this.obj = new QuestionInfo();
        }

        public Builder qid(String qid) {
            obj.setQid(qid);
            return this;
        }

        public Builder content(byte[] content) {
            obj.setContent(content);
            return this;
        }

        public Builder answer(byte[] answer) {
            obj.setAnswer(answer);
            return this;
        }

        public QuestionInfo build() {
            return this.obj;
        }
    }

    public enum Column {
        qid("qid", "qid", "CHAR", false),
        content("content", "content", "LONGVARBINARY", false),
        answer("answer", "answer", "LONGVARBINARY", false);

        private static final String BEGINNING_DELIMITER = "`";

        private static final String ENDING_DELIMITER = "`";

        private final String column;

        private final boolean isColumnNameDelimited;

        private final String javaProperty;

        private final String jdbcType;

        public String value() {
            return this.column;
        }

        public String getValue() {
            return this.column;
        }

        public String getJavaProperty() {
            return this.javaProperty;
        }

        public String getJdbcType() {
            return this.jdbcType;
        }

        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        public static Column[] all() {
            return Column.values();
        }

        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }

        public String getAliasedEscapedColumnName() {
            return this.getEscapedColumnName();
        }
    }
}