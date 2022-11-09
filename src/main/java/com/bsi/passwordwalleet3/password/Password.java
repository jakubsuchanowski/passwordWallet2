package com.bsi.passwordwalleet3.password;

import com.bsi.passwordwalleet3.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "password")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class Password{

    @Id
//    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name="password", nullable = false)
    private String password;

    @NonNull
    @ManyToOne
//    @Column(name="id_user", nullable = false)
    private User user;


//    @Column(name="web_address")
    private String webAddress;

//    @Column(name="description")
    private String description;

//    @Column(name="login", length = 30)
    private String login;

}