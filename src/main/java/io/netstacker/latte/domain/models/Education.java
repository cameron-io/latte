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
@Table(name = "EDUCATION")
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_id")
    @Getter @Setter
    private Long id;
    @Getter @Setter
    private String school;
    @Getter @Setter
    private String degree;
    @Getter @Setter
    private String field_of_study;
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
