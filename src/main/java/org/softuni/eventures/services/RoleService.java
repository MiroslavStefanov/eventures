package org.softuni.eventures.services;


import org.softuni.eventures.domain.entities.Role;

import java.util.List;

public interface RoleService {
    Role getRole(String name);

    List<String> getAllRoleNames();
}
