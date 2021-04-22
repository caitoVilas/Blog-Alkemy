package com.alkemy.blog.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE posts SET deleted=true WHERE id=?")
@Where(clause = "deleted = false")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty
    @Column(length = 100)
    private String title;
    @Column(length = 1500)
    @NotEmpty
    private String content;
    @NotNull
    @NotEmpty
    @Column(length = 100)
    private String category;
    @Column(name = "create_at")
    private LocalDate createAt;
    private String image;
    private boolean deleted;



    public Post(String title, String content, String category, LocalDate createAt) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.createAt = createAt;
    }

}
