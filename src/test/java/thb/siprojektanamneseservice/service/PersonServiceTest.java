package thb.siprojektanamneseservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import thb.siprojektanamneseservice.model.Address;
import thb.siprojektanamneseservice.model.Allergy;
import thb.siprojektanamneseservice.model.Person;
import thb.siprojektanamneseservice.model.Security;
import thb.siprojektanamneseservice.repository.AddressRepository;
import thb.siprojektanamneseservice.repository.AllergyRepository;
import thb.siprojektanamneseservice.repository.PersonRepository;
import thb.siprojektanamneseservice.repository.SecurityRepository;
import thb.siprojektanamneseservice.transfert.PersonTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


class PersonServiceTest {

    @Mock
    private PersonRepository personRepository;
    @Mock
    private AllergyRepository allergyRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private SecurityRepository securityRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PersonService underTest;

    PersonTO personTO ;
    Allergy allergy;
    Address address;
    Security security;
    String password;
    String encodedPassword;
    Person person;

    List<String> allergyNames;
    List<Allergy> allergies;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        person = new Person();
        personTO = new PersonTO();
        allergy = new Allergy();
        allergies = new ArrayList<>();
        address = new Address();
        security = new Security();

        password = "password_" + UUID.randomUUID().toString();
        encodedPassword = "encodedPassword_" + UUID.randomUUID().toString();



        allergyNames = new ArrayList<>();
        allergyNames.add("Pollen");

        personTO.setLastName("Massa");
        personTO.setSecretQuestion("security");
        personTO.setAnswer("Address");
        personTO.setProfession("personTO.getProfession()");
        personTO.setChildren(true);

        personTO.setEmail("personTO.getEmail()");
        personTO.setFirstName("personTO.getFirstName()");
        personTO.setHeight(124);
        personTO.setWeight(12.5f);

        personTO.setGender("M");
        personTO.setLastName("lastName_" + UUID.randomUUID().toString());
        personTO.setMaritalStatus("personTO.getMaritalStatus()");

        personTO.setUserName("userName_" + UUID.randomUUID().toString());
        personTO.setPassword(password);

        personTO.setPhoneNumber("personTO.getPhoneNumber()");
        personTO.setRecorded(false);
        personTO.setType("personTO.getType()");
        personTO.setStreetAndNumber("Mystreet 1");
        personTO.setCity("my city");
        personTO.setPostalCode("12456");
        personTO.setCountry("My country");
        personTO.setAllergyNames(allergyNames);

        person.setLastName("Massa");
        person.setProfession("personTO.getProfession()");
        person.setChildren(true);

        person.setEmail("personTO.getEmail()");
        person.setFirstName("personTO.getFirstName()");
        person.setHeight(124);
        person.setWeight(12.5f);

        person.setGender("personTO.getGender()");
        person.setLastName("personTO.getLastName()");
        person.setMaritalStatus("personTO.getMaritalStatus()");
        person.setUserName("personTO.getUserName()");

        person.setPassword(encodedPassword);
        person.setPhoneNumber("personTO.getPhoneNumber()");
        person.setRecorded(false);
        person.setType("personTO.getType()");

        address.setStreetAndNumber(personTO.getStreetAndNumber());
        address.setCity(personTO.getCity());
        address.setCountry(personTO.getCountry());
        address.setPostalCode(personTO.getPostalCode());

        allergy.setName(personTO.getAllergyNames().get(0));
        security.setSecretQuestion(personTO.getSecretQuestion());
        security.setAnswer(personTO.getAnswer());

        person.setSecurity(security);
        allergies.add(allergy);
        person.setAllergies(allergies);

        person.setAddress(address);
    }

    @Test
    void createPersonAndCheckEmailOfCreatedTest() {
//        when(securityRepository.save(security)).thenReturn(security); // TODO
//
//        when(allergyRepository.findByName(personTO.getAllergyNames().get(0))).thenReturn(allergy);
//        when(allergyRepository.save(allergy)).thenReturn(allergy);
//
//        when(addressRepository.save(address)).thenReturn(address);
//
//        when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
//
//        when(personRepository.save(person)).thenReturn(person);
//
//        Person personCreated = underTest.create(personTO);
//
//        assertEquals("personTO.getEmail()", personCreated.getEmail(), personTO.getEmail());
    }

    @Test
    void getOneTest() {
        person.setId(UUID.fromString("c90c0562-7722-4336-be08-3911c3c16398"));
        when(personRepository.save(person)).thenReturn(person);
        when(personRepository.findById(person.getId())).thenReturn(java.util.Optional.ofNullable(person));

        Person person1 = underTest.getOne(person.getId());

        assertEquals("c90c0562-7722-4336-be08-3911c3c16398", person1.getId().toString());
    }

    @Test
    void updateTest() {
        person.setId(UUID.fromString("c90c0562-7722-4336-be08-3911c3c16398"));
        when(personRepository.save(person)).thenReturn(person);

        person.setProfession("Informaticien");
        when(personRepository.save(person)).thenReturn(person);
        when(personRepository.findById(person.getId())).thenReturn(java.util.Optional.ofNullable(person));

        Person person1 = underTest.update(person.getId(), person);

        assertEquals("Informaticien", person1.getProfession());
    }

    @Test
    void listAllTest() {
        Person person1 = person;
        person1.setType("Personal");
        List<Person> people = new ArrayList<>();
        people.add(person1);
        people.add(person);

        when(personRepository.save(person)).thenReturn(person);
        when(personRepository.save(person1)).thenReturn(person1);

        when(personRepository.findAll()).thenReturn(people);
        List<Person> personList = underTest.listAll();

        assertEquals(2, personList.size());
    }
}