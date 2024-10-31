package com.zysblog.zysblog.dto.vo;

import lombok.Data;

@Data
public class SimpleResult {
    Boolean Result = true;

    public SimpleResult() {
    }

    public SimpleResult(Boolean result) {
        Result = result;
    }
}
