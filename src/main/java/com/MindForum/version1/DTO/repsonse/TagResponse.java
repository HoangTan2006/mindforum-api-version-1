package com.MindForum.version1.DTO.repsonse;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class TagResponse {
    private Integer id;
    private String tagName;
}
