package thb.siprojektanamneseservice.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import thb.siprojektanamneseservice.model.CustomUserDetails;
import thb.siprojektanamneseservice.repository.PersonRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    public CustomUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String benutzernummer) throws UsernameNotFoundException {
        return new CustomUserDetails(
                personRepository.findByUserName(benutzernummer));
    }

}
