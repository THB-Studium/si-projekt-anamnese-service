package thb.siprojektanamneseservice.rest;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import thb.siprojektanamneseservice.ItBase;
import thb.siprojektanamneseservice.model.*;
import thb.siprojektanamneseservice.model.constants.AllergyValues;
import thb.siprojektanamneseservice.model.constants.ExaminationValues;
import thb.siprojektanamneseservice.model.constants.GenderValues;
import thb.siprojektanamneseservice.model.constants.MaritalStatusValues;
import thb.siprojektanamneseservice.repository.*;
import thb.siprojektanamneseservice.transfert.DiagnosisTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
class DiagnosisControllerIT extends ItBase {

    @Autowired
    DiagnosisRepository repository;
    @Autowired
    SecurityRepository securityRepository;
    @Autowired
    AllergyRepository allergyRepository;
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    PersonRepository personRepository;

    Random random = new Random();

    Diagnosis diagnosis1;
    Diagnosis diagnosis2;

    Person person1;
    Person person2;

    Allergy allergy1;
    Allergy allergy2;

    List<Allergy> allergies;

    Address address1;
    Address address2;

    Security security1;
    Security security2;

    @BeforeEach
    void setUp() throws Exception {
        super.setup();

        allergies = new ArrayList<>();
        allergy1 = allergyRepository.save(buildAllergy());
        allergy2 = allergyRepository.save(buildAllergy());
        allergies.add(allergy1);
        allergies.add(allergy2);

        address1 = addressRepository.save(buildAddress());
        address2 = addressRepository.save(buildAddress());

        security1 = securityRepository.save(buildSecurity());
        security2 = securityRepository.save(buildSecurity());

        person1 = buildPerson();
        person1.setAllergies(allergies);
        person1.setAddress(address1);
        person1.setSecurity(security1);
        person1 = personRepository.save(person1);

        person2 = buildPerson();
        person2.setAllergies(allergies);
        person2.setAddress(address2);
        person2.setSecurity(security2);
        person2 = personRepository.save(person2);

        diagnosis1 = buildDiagnosis();
        diagnosis1.setPerson(person1);
        diagnosis1 = repository.save(diagnosis1);

        diagnosis2 = buildDiagnosis();
        diagnosis2.setPerson(person2);
        diagnosis2 = repository.save(diagnosis2);

    }

    @AfterEach
    void tearDown() throws Exception {
        super.cleanup();
    }

    @Test
    void listAllTest() {
        preLoadedGiven.get(ApiConstants.DIAGNOSIS_ROOT).then().log().body().statusCode(200)
                .body("size()", is(equalTo(2)))
                .body("id", containsInAnyOrder(diagnosis1.getId().toString(), diagnosis2.getId().toString()))
                .body("examinationName", containsInAnyOrder(diagnosis1.getExaminationName(), diagnosis2.getExaminationName()))
                .body("bodyRegion", containsInAnyOrder(diagnosis1.getBodyRegion(), diagnosis2.getBodyRegion()));
    }

    @Test
    @DisplayName("Test the create() method. It checks if a diagnosis can be created")
    void createTest() throws ParseException {
        DiagnosisTO create = buildDiagnosisTO();
        create.setPersonId(person1.getId());

        String id = preLoadedGiven.contentType(ContentType.JSON).body(create)
                .log().body()
                .post(ApiConstants.DIAGNOSIS_ROOT).then().log().body().statusCode(200)
                .extract().body().path("id");

        Optional<Diagnosis> actual = repository.findById(UUID.fromString(id));

        assertThat(true, is(actual.isPresent()));
        assertThat(actual.get().getExaminationName(), is(equalTo(create.getExaminationName())));
        assertThat(actual.get().getBodyRegion(), is(equalTo(create.getBodyRegion())));
    }

    @Test
    @DisplayName("Test the getOne() method")
    void getOneTest() {
        preLoadedGiven.get(ApiConstants.DIAGNOSIS_ITEM, diagnosis1.getId()).then().log()
                .body().statusCode(200)
                .body("id", is(equalTo(diagnosis1.getId().toString())))
                .body("examinationName", is(equalTo(diagnosis1.getExaminationName())))
                .body("bodyRegion", is(equalTo(diagnosis1.getBodyRegion())));
    }

    @Test
    @DisplayName("Test the getOne() method if the given id ist not found")
    void getOneNotFound() {
        preLoadedGiven.get(ApiConstants.DIAGNOSIS_ITEM, UUID.randomUUID())
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Test the delete() method")
    void deleteTest() {
        preLoadedGiven.delete(ApiConstants.DIAGNOSIS_ITEM, diagnosis2.getId())
                .then().statusCode(200);
        Optional<Diagnosis> actual = repository.findById(diagnosis2.getId());
        assertThat(actual, is(Optional.empty()));
    }

    @Test
    @DisplayName("Test the delete() method if the given id ist not found")
    void deleteNotFound() {
        preLoadedGiven.delete(ApiConstants.DIAGNOSIS_ITEM, UUID.randomUUID())
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Test the update() method when given an id and the diagnosis to be updated")
    void updateTest() throws ParseException {
        Diagnosis update = buildDiagnosis();
        preLoadedGiven.contentType(ContentType.JSON).body(update).log().body()
                .put(ApiConstants.DIAGNOSIS_ITEM, diagnosis2.getId())
                .then()
                .log()
                .body()
                .statusCode(200);

        Optional<Diagnosis> found = repository.findById(diagnosis2.getId());
        assertThat(found.get().getId(), is(equalTo(diagnosis2.getId())));
        assertThat(found.get().getBodyRegion(), is(equalTo(update.getBodyRegion())));
        assertThat(found.get().getExaminationName(), is(equalTo(update.getExaminationName())));
    }

    @Test
    @DisplayName("Test the update() method if the given id ist not found")
    void updateNotFound() throws ParseException {
        Diagnosis update = buildDiagnosis();
        preLoadedGiven.contentType(ContentType.JSON).body(update)
                .put(ApiConstants.DIAGNOSIS_ITEM, UUID.randomUUID())
                .then()
                .log()
                .body()
                .statusCode(404);
    }

    @Test
    @DisplayName("Test the update() method when the city is updated")
    void updateExaminationNameTest() throws ParseException {
        Diagnosis update = buildDiagnosis();
        update.setExaminationName(diagnosis1.getExaminationName());
        preLoadedGiven.contentType(ContentType.JSON).body(update).log().body()
                .put(ApiConstants.DIAGNOSIS_ITEM, diagnosis1.getId())
                .then()
                .log()
                .body()
                .statusCode(200);

        Optional<Diagnosis> actual = repository.findById(diagnosis1.getId());
        assertThat(true, is(actual.isPresent()));
        assertThat(actual.get().getExaminationName(), is(equalTo(update.getExaminationName())));
    }

    private Security buildSecurity() {
        Security security = new Security();
        security.setSecretQuestion("What to believe?" + "-" + random.nextInt(3));
        security.setAnswer("John 3:16" + "-" + random.nextInt(1));
        return security;
    }

    private Person buildPerson() {
        Person person = new Person();
        person.setFirstName("Tamo" + "-" + random.nextInt());
        person.setLastName("Tani" + "-" + random.nextInt());
        person.setProfession("Entrepreneur" + "-" + random.nextInt());
        person.setPhoneNumber("124598762" + "-" + random.nextInt());
        person.setEmail("tamo@si-project.com" + "-" + random.nextInt());
        person.setGender(GenderValues.Man.name() + "-" + random.nextInt());
        person.setMaritalStatus(MaritalStatusValues.SINGLE.name() + "-" + random.nextInt());
        person.setChildren(random.nextBoolean());
        person.setHeight(random.nextInt(5));
        person.setWeight(random.nextFloat());
        person.setType("Personal" + "-" + random.nextInt());
        person.setUserName("tamaTani" + "-" + random.nextInt());
        person.setPassword("secretPwd" + "-" + random.nextInt());
        person.setRecorded(random.nextBoolean());
        return person;
    }

    private Allergy buildAllergy() {
        Allergy allergy = new Allergy();
        allergy.setName(AllergyValues.ANIMAL_HAIR.name() + "-" + random.nextInt());
        return allergy;
    }

    private Address buildAddress() {
        Address build = new Address();
        build.setCity("Douala-" + random.nextInt());
        build.setPostalCode("12458-" + random.nextInt());
        build.setCountry("Kamerun-" + random.nextInt());
        build.setStreetAndNumber("Douala Street-" + random.nextInt());
        return build;
    }

    private Diagnosis buildDiagnosis() throws ParseException {
        Diagnosis build = new Diagnosis();
        build.setBodyRegion("Head" + "-" + random.nextInt());
        build.setExaminationName(ExaminationValues.Magnetic_Resonance_Imaging.name() + "-" + random.nextInt());
        build.setExaminationDate(new SimpleDateFormat("dd-MM-yyyy").parse("07-01-2021"));

        return build;
    }

    private DiagnosisTO buildDiagnosisTO() throws ParseException {
        DiagnosisTO build = new DiagnosisTO();
        build.setBodyRegion("Head" + "-" + random.nextInt());
        build.setExaminationName(ExaminationValues.Magnetic_Resonance_Imaging.name() + "-" + random.nextInt());
        build.setExaminationDate(new SimpleDateFormat("dd-MM-yyyy").parse("07-01-2021"));

        return build;
    }

}