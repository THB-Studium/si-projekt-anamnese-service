package thb.siprojektanamneseservice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.codec.binary.Base64;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import thb.siprojektanamneseservice.model.Address;
import thb.siprojektanamneseservice.model.Person;
import thb.siprojektanamneseservice.model.Security;
import thb.siprojektanamneseservice.repository.*;
import thb.siprojektanamneseservice.rest.ApiConstants;

import javax.ws.rs.core.HttpHeaders;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ItBase {

    protected String token;
    protected RequestSpecification preLoadedGiven;

    @LocalServerPort
    protected int port;

    @Autowired
    protected PersonRepository personRepository;
    @Autowired
    protected SecurityRepository securityRepository;
    @Autowired
    protected AddressRepository addressRepository;
    @Autowired
    protected AllergyRepository allergyRepository;
    @Autowired
    protected DiagnosisRepository diagnosisRepository;
    @Autowired
    protected PasswordEncoder passwordEncoder;

    protected Person superAdmin;
    protected String superAdminPassword;
    protected Address myAddress;
    protected Security mySecurity;

    public void setup() throws Exception {
        RestAssured.port = port;

        // authenticate a default user
        authenticate();

        // a given() function pre-loaded with token
        preLoadedGiven = given().header("Authorization", String.format("Bearer %s", token));

    }

    private void authenticate() {
        // create default user:
        superAdmin = buildDefaultPerson();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization",
                "Basic " + new String(Base64.encodeBase64(String.format("%s:%s",
                        superAdmin.getUserName(), superAdminPassword).getBytes())));

        token = given()
                .headers(headers)
                .post(ApiConstants.SESSIONS_COLLECTION)
                .then()
                .log().body()
                .statusCode(200)
                .extract().body().path("access_token");

    }

    public void cleanup() throws Exception {
        // revoke the generated token
        revoke();

        diagnosisRepository.deleteAll();
        personRepository.deleteAll();
        addressRepository.deleteAll();
        securityRepository.deleteAll();
        allergyRepository.deleteAll();
    }

    private void revoke() {
        given()
                .log().all()
                .delete(ApiConstants.SESSIONS_ITEM, token)
                .then()
                .statusCode(200);
    }

    @SuppressWarnings("unchecked")
    public <T> T createResource(T entity, String path, Object... arg1) {
        String location = given().contentType(ContentType.JSON).body(entity).log().body().post(path, arg1).then().log()
                .body().statusCode(201).extract().header(HttpHeaders.LOCATION);
        return (T) given().get(location).then().log().body().statusCode(200).extract().as(entity.getClass());
    }

    private Person buildDefaultPerson() {
        // Security:
        mySecurity = new Security();
        mySecurity.setSecretQuestion("In welcher Straße sind Sie aufgewachsen?");
        mySecurity.setAnswer("Zanderstr");
        mySecurity = securityRepository.save(mySecurity);

        // Address:
        myAddress = new Address();
        myAddress.setStreetAndNumber("ZanderStr. 10");
        myAddress.setPostalCode("14770");
        myAddress.setCity("Brandenburg a.d.H");
        myAddress.setCountry("Brandenburg");
        myAddress = addressRepository.save(myAddress);


        // Person:
        Person myPerson = new Person();
        myPerson.setFirstName("first_name_" + UUID.randomUUID());
        myPerson.setLastName("last_name_" + UUID.randomUUID());
        myPerson.setUserName("admin_" + UUID.randomUUID());
        superAdminPassword = "admin_" + UUID.randomUUID();
        myPerson.setPassword(passwordEncoder.encode(superAdminPassword));

        myPerson.setAddress(myAddress);
        myPerson.setPhoneNumber("+493045789");
        myPerson.setEmail("steve@Electronicien.com");
        myPerson.setGender("M");
        myPerson.setMaritalStatus("single");
        myPerson.setHeight(170);
        myPerson.setWeight(78);
        myPerson.setType("personal");
        myPerson.setProfession("Informatiker");
        myPerson.setSecurity(mySecurity);

        return personRepository.saveAndFlush(myPerson);
    }

}
