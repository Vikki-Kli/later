package org.example.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 32)
    private String name;
    private String email;
    @Column(name="registration_date")
    private Instant registrationDate;
    @Enumerated(EnumType.STRING)
    private UserState state;
}
