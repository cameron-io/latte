package io.netstacker.latte.domain.models;

import java.sql.Date;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "BLOG")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String text;
    @Getter @Setter
    private String name;
    @Getter
    private final Date created_at = new Date(System.currentTimeMillis());

    @ManyToOne(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "account_id")
    @Getter @Setter
    private Account account;
}
