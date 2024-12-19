package io.netstacker.latte.domain.models;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "EXPERIENCE")
public class Experience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "experience_id")
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String title;
    @Getter @Setter
    private String company;
    @Getter @Setter
    private String location;
    @Getter @Setter
    private Date from_date;
    @Getter @Setter
    private Date to_date;
    @Getter @Setter
    private Boolean current = false;
    @Getter @Setter
    private String description;
    
    @ManyToOne(
        cascade = CascadeType.ALL,
        fetch = FetchType.LAZY
    )
    @JoinColumn(name = "profile_id")
    @Getter @Setter
    private Profile profile;
}
