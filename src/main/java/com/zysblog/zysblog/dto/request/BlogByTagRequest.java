package com.zysblog.zysblog.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zysblog.zysblog.common.api.CloudApiRequest;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;

@Data
public class BlogByTagRequest extends CloudApiRequest {
    @JsonProperty("tagUid")
    @NotNull
    private String tagUid;
    @JsonProperty("currentPage")
    private Long currentPage;
    @JsonProperty("pageSize")
    private Long pageSize;
}
