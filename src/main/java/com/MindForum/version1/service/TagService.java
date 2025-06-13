package com.MindForum.version1.service;

import com.MindForum.version1.entity.Tag;

import java.util.List;
import java.util.Set;

public interface TagService {
    Set<Tag> findAndCreateTag(List<String> tagNames);
}
