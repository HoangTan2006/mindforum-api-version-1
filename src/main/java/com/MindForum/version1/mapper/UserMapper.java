package com.MindForum.version1.mapper;

import com.MindForum.version1.DTO.repsonse.UserResponse;
import com.MindForum.version1.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper {
    public UserResponse toDTO(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
