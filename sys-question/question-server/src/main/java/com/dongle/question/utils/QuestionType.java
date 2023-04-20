package com.dongle.question.utils;

public enum QuestionType {
    /**
     * 0 SINGLE 单选题
     * 1 MULTI 多选题
     * 2 FILL 填空题
     * 3 RESPONSE 解答题
     * 4 TITLE 作文题
     * 5 DRAW 画图题
     * 6 MATCH 连线题
     * 7 TRUE_OR_FALSE 判断题
     */
    SINGLE(0),MULTI(1),FILL(2),RESPONSE(3),TITLE(4),DRAW(5),MATCH(6),TRUE_OR_FALSE(7);

    private final int value;

    public int getValue() {
        return value;
    }

    QuestionType(int value){
        this.value = value;
    }
}
