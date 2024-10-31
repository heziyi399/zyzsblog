package com.zysblog.zysblog.dto.vo;

import lombok.Data;

@Data
public class Mail {
    private String from;
    private String text;
    private String to;
    private String subject;
}
