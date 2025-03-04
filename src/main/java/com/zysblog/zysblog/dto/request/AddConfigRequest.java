package com.zysblog.zysblog.dto.request;

import com.zysblog.zysblog.common.api.CloudApiRequest;
import lombok.Data;

@Data
public class AddConfigRequest extends CloudApiRequest {
    private String key;
    private String value;
}
