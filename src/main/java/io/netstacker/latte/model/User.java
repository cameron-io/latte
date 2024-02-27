package io.netstacker.latte.model;

import lombok.Data;

import java.sql.Date;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min = 6, max = 64)
    @NotBlank
    private String username;
    @Email
    @NotBlank
    private String email;
    @Size(min = 6, max = 255)
    @NotBlank
    private String password;
    private String avatar;
    private Date created_at = new Date(System.currentTimeMillis());
}
