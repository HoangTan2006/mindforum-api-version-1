package com.MindForum.version1.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tags")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag extends AbstractEntity<Integer> {
    @Column(name = "tag_name", nullable = false, unique = true)
    private String tagName;
}