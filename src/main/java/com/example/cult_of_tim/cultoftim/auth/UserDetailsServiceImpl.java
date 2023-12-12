package com.example.cult_of_tim.cultoftim.auth;

import com.cult_of_tim.auth.cultoftimauth.converter.UserConverter;
import com.cult_of_tim.auth.cultoftimauth.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {


    UserRepository userRepository;

    UserConverter userConverter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var optionalUser = userRepository.findByUsername(username);

        if(optionalUser.isEmpty())
            throw new UsernameNotFoundException("User not found");

        var user = optionalUser.get();

        return new UserDetailsImpl(userConverter.toDTO(user));
    }
}
