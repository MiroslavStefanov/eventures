package org.softuni.eventures.services;

import org.softuni.eventures.domain.models.binding.UserLoginBindingModel;
import org.softuni.eventures.domain.models.binding.UserRegisterBindingModel;
import org.softuni.eventures.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    String saveUser(UserRegisterBindingModel userRegisterBindingModel);

    String logInUser(UserLoginBindingModel userLoginBindingModel);

    String getUserName(String userId);

    List<UserServiceModel> getAll();

    UserServiceModel getUser(String userId);
}
