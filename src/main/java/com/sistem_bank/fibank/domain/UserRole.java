package com.sistem_bank.fibank.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_roles", schema = "auth")
public class UserRole {
    @Id
    @SequenceGenerator(name = "user_roles_seq_gen", sequenceName = "user_roles_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_roles_seq_gen")
    private Integer id;
    private Integer user_id;
    private Integer role_id;
}
