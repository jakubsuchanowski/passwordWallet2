package com.bsi.passwordwalleet3.repository;

import com.bsi.passwordwalleet3.entity.Password;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<Password, Long> {
}
