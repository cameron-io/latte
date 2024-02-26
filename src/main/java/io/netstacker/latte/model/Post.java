package io.netstacker.latte.model;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Table(name = "posts")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String user;
    private String text;
    private String name;
    private String avatar;
}
