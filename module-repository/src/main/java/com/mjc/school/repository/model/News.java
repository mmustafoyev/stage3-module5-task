package com.mjc.school.repository.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "news")
@NoArgsConstructor
@Data
public class News implements BaseEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String content;
    @CreatedDate
    private LocalDateTime createTime;
    @LastModifiedDate
    private LocalDateTime lastUpdateTime;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Author_Id")
    private Author author;
    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY, cascade = {CascadeType.REMOVE})
    private List<Comment> comments;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "newstags",
            joinColumns = {@JoinColumn(name = "News_Id")},
            inverseJoinColumns = {@JoinColumn(name = "Tag_id")})
    private List<Tag> tags;

}
