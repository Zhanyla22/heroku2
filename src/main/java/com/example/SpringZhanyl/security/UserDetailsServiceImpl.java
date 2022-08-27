package com.example.SpringZhanyl.security;

import com.example.SpringZhanyl.Entity.User;
import com.example.SpringZhanyl.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User users = userRepository.findByEmail(email).orElseThrow(()->
                new UsernameNotFoundException("User doesnt exist"));
        return SecurityUser.fromUser(users);
    }

}
