package io.netstacker.latte.model;

import lombok.Data;

import java.sql.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "posts")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // TODO: Use Foreign Key to Users DB
    private String user;
    @NotBlank
    private String text;
    private String name;
    private String avatar;
    private final Date created_at = new Date(System.currentTimeMillis());
}
