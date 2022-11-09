package com.bsi.passwordwalleet3.user;

import lombok.*;

import javax.persistence.*;

    @Entity
    @Table(name="user")
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @RequiredArgsConstructor
    public class User {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
//        @Column(name = "id", nullable = false)
        private Long id;

//        @Column(name = "login", nullable = false, length = 30)
        @NonNull
        private String login;

//        @Column(name = "password_hash", nullable = false, length = 512)
        @NonNull
        private String passwordHash;

//        @Column(name = "salt", length = 20)
        private String salt;

//        @Column(name = "is_password_kept_as_hash")
        private Boolean isPasswordKeptAsHash;
    }
