package com.MindForum.version1.mapper;

import com.MindForum.version1.DTO.repsonse.QuestionResponse;
import com.MindForum.version1.DTO.repsonse.TagResponse;
import com.MindForum.version1.DTO.repsonse.UserResponse;
import com.MindForum.version1.entity.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionMapper {

    private final TagMapper tagMapper;
    private final UserMapper userMapper;

    public QuestionResponse toDTO(Question question) {
        //Get author
        UserResponse author = userMapper.toDTO(question.getUser());

        //Get tags
        List<TagResponse> tagResponses = question.getTags()
                .stream()
                .map(tagMapper::toDTO)
                .toList();

        return QuestionResponse.builder()
                .id(question.getId())
                .title(question.getTitle())
                .content(question.getContent())
                .tags(tagResponses)
                .createdAt(question.getCreatedAt())
                .author(author)
                .build();
    }
}
