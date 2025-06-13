package com.MindForum.version1.mapper;

import com.MindForum.version1.DTO.repsonse.AnswerResponse;
import com.MindForum.version1.DTO.repsonse.UserResponse;
import com.MindForum.version1.entity.Answer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerMapper {

    private final UserMapper userMapper;

    public AnswerResponse toDTO(Answer answer) {
        UserResponse author = userMapper.toDTO(answer.getUser());

        return AnswerResponse.builder()
                .id(answer.getId())
                .user(author)
                .content(answer.getContent())
                .score(answer.getScore())
                .createdAt(answer.getCreatedAt())
                .build();
    }
}
