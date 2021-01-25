package thb.siprojektanamneseservice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import thb.siprojektanamneseservice.model.Address;
import thb.siprojektanamneseservice.repository.AddressRepository;
import thb.siprojektanamneseservice.repository.PersonRepository;

import java.util.List;

@Component
public class PersonsList {
    private static final Logger log = LoggerFactory.getLogger(PersonsList.class);

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public PersonsList() {
    }

//    public void buildUser() {
//        log.info("------------- User build started ------------");
//
//        // ADRESSE:
//        List<Address> adresses = addressRepository.findAll();
//        if (adresses.isEmpty() || adresses == null) {
//            addressRepository.save(new Address("August-Bebel-Str. 15", 14770, "Brandenburg a.d.H", "Deutschland"));
//            adresses = addressRepository.findAll();
//        }
//
//        log.info("------------- Address is done ------------");
//
//        // USER:
//        User[] users = {
//                new User("Steve", "Ngalamo", "musterMail@muster.com", "11111111111", passwordEncoder.encode("password"), adresses.get(0)),
//                new User("Junior", "Wagueu", "musterMail@muster.com", "22222222222", passwordEncoder.encode("password"), adresses.get(0)),
//                new User("Flora", "Goufack", "musterMail@muster.com", "33333333333", passwordEncoder.encode("password"), adresses.get(0)),
//                new User("Dorline", "Damesse", "musterMail@muster.com", "4444444444", passwordEncoder.encode("password"), adresses.get(0)),
//                new User("Patricia", "Fotso", "musterMail@muster.com", "5555555555", passwordEncoder.encode("password"), adresses.get(0))
//        };
//
//        for (User user : users) {
//            User userFound = personRepository.findOneByBenutzernummer(user.getBenutzernummer());
//            if (userFound == null) {
//                personRepository.save(user);
//            }
//        }
//
//        log.info("------------- User build is done ------------");
//    }
}
