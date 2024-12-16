package io.netstacker.latte.domain.models;

import java.sql.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "blog")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long id;
    private String text;
    private String name;
    private final Date created_at = new Date(System.currentTimeMillis());

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;
}
