package io.netstacker.latte.model;

import lombok.Data;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "posts")
@Data
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    // TODO: Use Foreign Key to Users DB
    private String user;
    private String text;
    private String name;
    private String avatar;
    private Date created_at = new Date(System.currentTimeMillis());
}
