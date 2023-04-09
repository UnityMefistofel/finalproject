package ru.maxima.finalproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//import ru.maxima.finalproject.models.User;
import ru.maxima.finalproject.models.Person;
import ru.maxima.finalproject.repositories.PersonRepository;

import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    public PersonRepository personRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = userRepository
//                    .findByUsername(username)
//                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        return UserDetailsImpl.build(user);

        Person currUser = personRepository.findByUsername(username).orElse(null);
        if (currUser == null) {
            throw new UsernameNotFoundException("Unknown user: "+username);
        }
        UserDetails user = User.builder()
                .username(currUser.getUsername())
                .password(currUser.getPassword())
                .authorities(
                        currUser.getRoles()
                                .stream()
                                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                                .collect(Collectors.toList())
                )
                .build();
        return user;

    }
}
