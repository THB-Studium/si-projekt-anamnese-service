package thb.siprojektanamneseservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import thb.siprojektanamneseservice.model.*;
import thb.siprojektanamneseservice.model.constants.AllergyValues;
import thb.siprojektanamneseservice.model.constants.PersonTypes;
import thb.siprojektanamneseservice.repository.*;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class SystemInit {

    private final AppSettings settings;
    private final PersonRepository personRepository;
    private final SecurityRepository securityRepository;
    private final AddressRepository addressRepository;
    private final AllergyRepository allergyRepository;
    private final IllnessRepository illnessRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SystemInit(AppSettings settings, PersonRepository personRepository, SecurityRepository securityRepository, AddressRepository addressRepository, AllergyRepository allergyRepository, IllnessRepository illnessRepository, PasswordEncoder passwordEncoder) {
        this.settings = settings;
        this.personRepository = personRepository;
        this.securityRepository = securityRepository;
        this.addressRepository = addressRepository;
        this.allergyRepository = allergyRepository;
        this.illnessRepository = illnessRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if(settings.isInitData()) {
            buildAllergy();
            buildSecurity();
            buildAdress();
            buildIllness();

            // Persons:
            buildPerson("admin_first_name", "admin_last_name",
                    PersonTypes.PERSONAL.toString().toLowerCase(), settings.getAuthorizationUsername(), settings.getAuthorizationSecret());
            buildPerson("Steve", "Carmel", PersonTypes.PERSONAL.toString().toLowerCase(), "steveCarmel", "steve");
            buildPerson("Daniele", "Fokam", PersonTypes.PERSONAL.toString().toLowerCase(), "danieleFokam", "daniele");
            buildPerson("Flora", "Goufack", PersonTypes.PERSONAL.toString().toLowerCase(), "floraGoufack", "flora");
            buildPerson("Mistra", "Forest", PersonTypes.PERSONAL.toString().toLowerCase(), "mistraForest", "mistra");
            buildPerson("Patient1_first", "Patient1_last", PersonTypes.PATIENT.toString().toLowerCase(), "patient1", "pat#001");
            buildPerson("Patient2_first", "Patient2_last", PersonTypes.PATIENT.toString().toLowerCase(), "patient2", "pat#002");
            buildPerson("Patient3_first", "Patient3_last", PersonTypes.PATIENT.toString().toLowerCase(), "patient3", "pat#003");
        }
    }


    private void buildPerson(String first, String last, String type, String userName, String password) {
        if (personRepository.findByUserName(userName) == null) {
            Person myPerson = new Person();
            myPerson.setFirstName(first);
            myPerson.setLastName(last);
            myPerson.setUserName(userName);
            myPerson.setPassword(passwordEncoder.encode(password));

            List<Address> addresses = addressRepository.findAll();
            List<Security> securities = securityRepository.findAll();

            myPerson.setAddress(addresses.isEmpty()? null: addresses.iterator().next());
            myPerson.setPhoneNumber("+493045789");
            myPerson.setEmail("steve@Electronicien.com");
            myPerson.setGender("M");
            myPerson.setMaritalStatus("single");
            myPerson.setHeight(170);
            myPerson.setWeight(78);
            myPerson.setType(type);
            myPerson.setProfession("Informatiker");
            myPerson.setSecurity(securities.isEmpty()? null: securities.iterator().next());

            if (type.equals(PersonTypes.PATIENT.toString().toLowerCase())) {
                myPerson.setAllergies(allergyRepository.findAll());
            }
            if (myPerson.getFirstName().equals("Patient1_first")) {
                myPerson.setRecorded(true);
            }

            personRepository.saveAndFlush(myPerson);
        }
    }

    private void buildAdress() {
        if (addressRepository.findAll().size() == 0) {
            Address myAddress = new Address();
            myAddress.setStreetAndNumber("ZanderStr. 10");
            myAddress.setPostalCode("14770");
            myAddress.setCity("Brandenburg a.d.H");
            myAddress.setCountry("Brandenburg");
            addressRepository.saveAndFlush(myAddress);
        }
    }

    private void buildSecurity() {
        if (securityRepository.findAll().size() == 0) {
            Security mySecurity = new Security();
            mySecurity.setSecretQuestion("In welcher Straße sind Sie aufgewachsen?");
            mySecurity.setAnswer("Zanderstr");
            securityRepository.saveAndFlush(mySecurity);
        }
    }

    private void buildAllergy() {
        String[] allergiesNames = {
                AllergyValues.ANIMAL_HAIR.toString(),
                AllergyValues.ANTIBIOTICS.toString(),
                AllergyValues.FRUCTOSE.toString()
        };


        for (String name : allergiesNames) {
            if (allergyRepository.findByName(name) == null) {
                allergyRepository.saveAndFlush(new Allergy(null, name.toLowerCase()));
            }
        }
    }

    private void buildIllness() {
        String[] illnessNames = {
                "Blutgerinnungsstörung", "Ohrerkrankung", "Augenerkrankung", "Magen- und(oder) Darmerkrankung",
                "Herzkrankheit", "Gelenkerkrankung", "Nierenerkrankungen", "Lebererkrankung", "Lungenerkrankung",
                "Geisteskrankheit oder psychische Krankheit", "Hauterkrankung", "Schilddrüsenerkrankung",
                "Harnsäure-Stoffwechselstörung", "Gefäßerkrankungen"
        };

        for (String name : illnessNames) {
            if (illnessRepository.findByName(name) == null) {
                illnessRepository.saveAndFlush(new Illness(null, name));
            }
        }
    }
}
