package io.netstacker.latte.domain.models;

import lombok.Data;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String name;
    private String email;
    private String password;
    private String avatar;
    private Date created_at = new Date(System.currentTimeMillis());

    public User() {}

    public User(Long id) {
        this.id = id;
    }
}
