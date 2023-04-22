package com.dongle.question.utils;

public enum QuestionType {
    /**
     * 1 SINGLE 单选题
     * 2 MULTI 多选题
     * 3 FILL 填空题
     * 4 RESPONSE 解答题
     * 5 TITLE 作文题
     * 6 DRAW 画图题
     * 7 MATCH 连线题
     * 8 TRUE_OR_FALSE 判断题
     */
    SINGLE(1),MULTI(2),FILL(3),RESPONSE(4),TITLE(5),DRAW(6),MATCH(7),TRUE_OR_FALSE(8);

    private final int value;

    public int getValue() {
        return value;
    }

    QuestionType(int value){
        this.value = value;
    }
}
