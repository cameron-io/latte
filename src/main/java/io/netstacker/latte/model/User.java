package io.netstacker.latte.model;

import lombok.Data;

import java.sql.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Min(6)
    @Max(64)
    @NotEmpty
    private String username;
    @Email
    @NotEmpty
    private String email;
    @Min(6)
    @Max(255)
    @NotEmpty
    private String password;
    private String avatar;
    private Date created_at = new Date(System.currentTimeMillis());
}
