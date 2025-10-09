package com.sistem_bank.fibank.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token", schema = "auth")
@Getter
@Setter
@Builder
@AllArgsConstructor
public class RefreshToken {
    @Id
    @SequenceGenerator(name = "token_seq", sequenceName = "auth.refresh_token_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false, unique = true)
    private String jti;

    private LocalDateTime expirationDate;
    private boolean revoked = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public RefreshToken() {}
}
