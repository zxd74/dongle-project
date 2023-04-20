package com.dongle.question.core.model;

import lombok.Data;

@Data
public class QuestionModel {
    private String id;
    private String title;
    private Integer type;
    private String desc;
    private String content;
    private String answer;
}
