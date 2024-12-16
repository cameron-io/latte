package io.netstacker.latte.domain.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "social")
public class Social {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "social_id")
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String youtube;
    @Getter @Setter
    private String twitter;
    @Getter @Setter
    private String facebook;
    @Getter @Setter
    private String linkedin;
    @Getter @Setter
    private String instagram;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    @Getter @Setter
    private Profile profile;
}
