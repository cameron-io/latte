package io.netstacker.latte.domain.models;

import lombok.Data;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "blog")
@Data
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;
    private String name;
    private final Date created_at = new Date(System.currentTimeMillis());

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
