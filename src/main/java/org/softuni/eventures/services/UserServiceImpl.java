package org.softuni.eventures.services;

import org.modelmapper.ModelMapper;
import org.softuni.eventures.domain.entities.User;
import org.softuni.eventures.domain.models.binding.UserLoginBindingModel;
import org.softuni.eventures.domain.models.binding.UserRegisterBindingModel;
import org.softuni.eventures.domain.models.service.UserServiceModel;
import org.softuni.eventures.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String saveUser(UserRegisterBindingModel userRegisterBindingModel) {
        if(userRegisterBindingModel == null || !userRegisterBindingModel.doesMatch())
            return null;

        User user;

        try{
            user = this.modelMapper.map(userRegisterBindingModel, User.class);
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));
            if(this.userRepository.count() == 0) {
                user.getAuthorities().add(this.roleService.getRole("ADMIN"));
            } else {
                user.getAuthorities().add(this.roleService.getRole("USER"));
            }
            user = this.userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return user.getId();
    }

    @Override
    public String logInUser(UserLoginBindingModel userLoginBindingModel) {
        if(userLoginBindingModel == null)
            return null;
        User user = this.userRepository.findFirstByUsername(userLoginBindingModel.getUsername());
        if(user == null || !user.getPassword().equals(userLoginBindingModel.getPassword()))
            return null;
        else
            return user.getId();
    }

    @Override
    public String getUserName(String userId) {
        if (userId == null)
            return  null;

        Optional<User> userCandidate = this.userRepository.findById(userId);
        return userCandidate.map(User::getUsername).orElse(null);
    }

    @Override
    public List<UserServiceModel> getAll() {
        return this.userRepository
                .findAll()
                .stream()
                .map(user -> this.modelMapper.map(user, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserServiceModel getUser(String userId) {
        if (userId == null)
            return  null;

        Optional<User> userCandidate = this.userRepository.findById(userId);
        return userCandidate.map(user->this.modelMapper.map(user, UserServiceModel.class)).orElse(null);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userRepository.findFirstByUsername(s);

        if(user == null) {
            user = this.userRepository.findFirstByEmail(s);
        }

        if(user == null)
            throw new UsernameNotFoundException("There is no user with username/email \"" + s + "\"");

        return user;
    }
}
