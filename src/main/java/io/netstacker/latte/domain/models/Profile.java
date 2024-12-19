package io.netstacker.latte.domain.models;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PROFILE")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String company;
    @Getter @Setter
    private String website;
    @Getter @Setter
    private String location;
    @Getter @Setter
    private String status;
    @Getter @Setter
    private List<String> skills;
    @Getter @Setter
    private String bio;
    @Getter @Setter
    private String githubusername;
    @Getter
    private final Date created_at = new Date(System.currentTimeMillis());

    @OneToOne(
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    @JoinColumn(name = "account_id")
    @Getter @Setter
    private Account account;

    @OneToMany(
        mappedBy = "profile",
        cascade = CascadeType.ALL
    )
    @Getter @Setter
    private List<Experience> experience;

    @OneToMany(
        mappedBy = "profile",
        cascade = CascadeType.ALL
    )
    @Getter @Setter
    private List<Education> education;

    @OneToOne(
        mappedBy = "profile",
        cascade = CascadeType.ALL
    )
    @Getter @Setter
    private Social social;

    public Profile() {}

    public Profile(Long id) {
        this.id = id;
    }
}
