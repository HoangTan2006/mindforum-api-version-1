package com.MindForum.version1.repository;

import com.MindForum.version1.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    Set<Tag> findAllByTagNameIn(List<String> tags);
}