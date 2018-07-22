package org.softuni.eventures.repositories;

import org.softuni.eventures.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Role findFirstByAuthority(String authority);
}
