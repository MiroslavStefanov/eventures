package org.softuni.eventures.repositories;

import org.softuni.eventures.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findFirstByUsername(String username);

    User findFirstByEmail(String email);
}
