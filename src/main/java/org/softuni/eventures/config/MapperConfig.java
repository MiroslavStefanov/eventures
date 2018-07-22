package org.softuni.eventures.config;

import org.modelmapper.ModelMapper;
import org.softuni.eventures.domain.entities.User;
import org.softuni.eventures.domain.models.binding.UserLoginBindingModel;
import org.softuni.eventures.domain.models.binding.UserRegisterBindingModel;
import org.softuni.eventures.domain.models.service.UserServiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    private final ModelMapper mapper;

    @Autowired
    public MapperConfig() {
        mapper = new ModelMapper();
        this.configure();
    }

    private void configure() {
        //User
        userRegisterBindingMapping();
        userLoginBindingMapping();
        userServiceMapping();
    }

    //User
    private void userRegisterBindingMapping() {
        this.mapper.createTypeMap(UserRegisterBindingModel.class, User.class);
    }

    private void userLoginBindingMapping() {
        this.mapper.createTypeMap(UserLoginBindingModel.class, User.class);
    }

    private void userServiceMapping() {
        this.mapper.createTypeMap(User.class, UserServiceModel.class);
    }

    @Bean
    public ModelMapper getModelMapper() {
        return mapper;
    }
}
