package com.finnal.blogit.dto.request;

import lombok.Data;

@Data
public class TopicRequest {
    private Integer id;

    private String name;

    private String shortDescription;
}
