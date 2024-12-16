package io.netstacker.latte.domain.models;

import java.sql.Date;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ACCOUNT")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String email;
    @Getter @Setter
    private String password;
    @Getter @Setter
    private String avatar;
    @Getter @Setter
    private Date created_at = new Date(System.currentTimeMillis());
    
    @OneToOne(
        mappedBy = "account",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "profile_id")
    @Getter @Setter
    private Profile profile;

    public Account() {}

    public Account(Long id) {
        this.id = id;
    }
}
