package com.bsi.passwordwalleet3.password;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

//@Repository
public interface PasswordRepo extends CrudRepository<Password, Long> {
    List<Password> findAllByUserId(Long id);
    Optional<Password> findById(Long id);
}
