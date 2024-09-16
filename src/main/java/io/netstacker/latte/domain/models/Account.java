package io.netstacker.latte.domain.models;

import lombok.Data;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "account")
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;
    private String name;
    private String email;
    private String password;
    private String avatar;
    private Date created_at = new Date(System.currentTimeMillis());
    
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public Account() {}

    public Account(Long id) {
        this.id = id;
    }
}
