package com.mjc.school.repository.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tags")
@NoArgsConstructor
@Getter
@Setter
public class Tag implements BaseEntity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "tags", fetch = FetchType.EAGER)
    private List<News> newsList;

}
