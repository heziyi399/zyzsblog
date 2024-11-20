package com.zysblog.zysblog.dto.request;

import com.zysblog.zysblog.common.api.CloudApiRequest;
import lombok.Data;

@Data
public class EmailRequest extends CloudApiRequest {
    private String email;
}
