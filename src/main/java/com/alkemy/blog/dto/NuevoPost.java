package com.alkemy.blog.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class NuevoPost {

    private String title;
    private String content;
    private String category;
    private LocalDate createAt;

}
