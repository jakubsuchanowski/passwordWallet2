package com.bsi.passwordwalleet3.user;


import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

//@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);

//   User findByLogin(String login);
}
