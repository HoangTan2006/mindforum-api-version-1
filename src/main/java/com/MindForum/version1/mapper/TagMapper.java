package com.MindForum.version1.mapper;

import com.MindForum.version1.DTO.repsonse.TagResponse;
import com.MindForum.version1.entity.Tag;
import org.springframework.stereotype.Service;

@Service
public class TagMapper {
    public TagResponse toDTO(Tag tag) {
        return TagResponse.builder()
                .id(tag.getId())
                .tagName(tag.getTagName())
                .build();
    }
}
