package io.netstacker.latte.model;

import lombok.Data;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "blog")
@Data
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String text;
    private String name;
    private String avatar;
    private final Date created_at = new Date(System.currentTimeMillis());

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonProperty(access = Access.WRITE_ONLY)
    private User user;
}
