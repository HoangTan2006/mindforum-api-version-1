package com.MindForum.version1.service.impl;

import com.MindForum.version1.entity.Tag;
import com.MindForum.version1.repository.TagRepository;
import com.MindForum.version1.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public Set<Tag> findAndCreateTag(List<String> tagNames) {
        Set<Tag> existingTags = tagRepository.findAllByTagNameIn(tagNames);

        //Lay danh sach ten cac tag da ton tai trong database
        List<String> existingTagNames = existingTags
                .stream()
                .map(Tag::getTagName)
                .toList();

        //Khoi tao danh sach cac tag moi
        Set<Tag> newTags = tagNames
                .stream()
                .filter(tagName -> !existingTagNames.contains(tagName))
                .map(Tag::new)
                .collect(Collectors.toSet());

        tagRepository.saveAll(newTags);

        //Gop danh sach tag moi va tag da ton tai
        existingTags.addAll(newTags);

        return existingTags;
    }
}
