package thb.siprojektanamneseservice.rest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import thb.siprojektanamneseservice.ItBase;
import thb.siprojektanamneseservice.model.Address;
import thb.siprojektanamneseservice.repository.AddressRepository;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
class AddressControllerIT  extends ItBase {

    @Autowired
    AddressRepository repository;

    Random random = new Random();
    Address address1;
    Address address2;

    @BeforeEach
    void setUp() throws Exception {
        super.setup();
        address1 = repository.save(build());
        address2 = repository.save(build());
    }

    @AfterEach
    void tearDown() throws Exception {
        super.cleanup();
    }

    private Address build(){
        Address build = new Address();
        build.setCity("Douala-"+random.nextLong());
        build.setPostalCode("12458-"+random.nextLong());
        build.setCountry("Kamerun-"+random.nextLong());
        build.setStreetAndNumber("Douala Street-"+random.nextLong());
        return  build;
    }

    @Test
    void listAllTest() {
        preLoadedGiven
                .log().all()
                .get(ApiConstants.ADDRESS_ROOT)
                .then()
                .log().body()
                .statusCode(200)
                .body("size()", is(equalTo(3)))
                .body("id", containsInAnyOrder(myAddress.getId().toString(), address1.getId().toString(), address2.getId().toString()))
                .body("city", containsInAnyOrder(myAddress.getCity(), address1.getCity(), address2.getCity()))
                .body("postalCode", containsInAnyOrder(myAddress.getPostalCode(), address1.getPostalCode(), address2.getPostalCode()))
                .body("country", containsInAnyOrder(myAddress.getCountry(), address1.getCountry(), address2.getCountry()))
                .body("streetAndNumber", containsInAnyOrder(myAddress.getStreetAndNumber(), address1.getStreetAndNumber(), address2.getStreetAndNumber()))
        ;
    }

    @Test
    @DisplayName("Test the create() method. It checks if an address can be created")
    void createTest() {
        Address create = build();
        String id = preLoadedGiven.contentType(ContentType.JSON).body(create).log()
                .body()
                .post(ApiConstants.ADDRESS_ROOT)
                .then()
                .log()
                .body()
                .statusCode(200).extract().body().path("id");

        Address actual = repository.findById(UUID.fromString(id)).get();
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getCity(), is(equalTo(create.getCity())));
    }

    @Test
    @DisplayName("Test the getOne() method")
    void getOneTest() {
        preLoadedGiven.get(ApiConstants.ADDRESS_ITEM, address1.getId()).then().log().body().statusCode(200)
                .body("id", is(equalTo(address1.getId().toString())))
                .body("city", is(equalTo(address1.getCity())))
                .body("postalCode", is(equalTo(address1.getPostalCode())))
                .body("country", is(equalTo(address1.getCountry())))
                .body("streetAndNumber", is(equalTo(address1.getStreetAndNumber())));
    }

    @Test
    @DisplayName("Test the getOne() method if the given id ist not found")
    void getOneNotFound() {
        preLoadedGiven.get(ApiConstants.ADDRESS_ITEM, UUID.randomUUID())
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Test the delete() method")
    void deleteTest() {
        preLoadedGiven.delete(ApiConstants.ADDRESS_ITEM, address1.getId())
                .then().statusCode(200);
        Optional<Address> actual = repository.findById(address1.getId());
        assertThat(actual, is(Optional.empty()));
    }

    @Test
    @DisplayName("Test the delete() method if the given id ist not found")
    void deleteNotFound() {
        preLoadedGiven.delete(ApiConstants.ADDRESS_ITEM, UUID.randomUUID())
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Test the update() method")
    void updateTest() {
        Address update = build();
        preLoadedGiven.contentType(ContentType.JSON).body(update).log().body()
                .put(ApiConstants.ADDRESS_ITEM, address1.getId())
                .then()
                .log()
                .body()
                .statusCode(200);

        Optional<Address> found = repository.findById(address1.getId());
        assertThat(found.get().getId(), is(equalTo(address1.getId())));
        assertThat(found.get().getCity(), is(equalTo(update.getCity())));
    }

    @Test
    @DisplayName("Test the update() method if the given id ist not found")
    void updateNotFound() {
        Address update = build();
        preLoadedGiven.contentType(ContentType.JSON).body(update)
                .put(ApiConstants.ADDRESS_ITEM, UUID.randomUUID())
                .then()
                .log()
                .body()
                .statusCode(404);
    }

    @Test
    @DisplayName("Test the update() method when the city is updated")
    void updateCityTest() {
        Address update = build();
        update.setCity(address1.getCity());
        preLoadedGiven.contentType(ContentType.JSON).body(update).log().body()
                .put(ApiConstants.ADDRESS_ITEM, address1.getId())
                .then()
                .log()
                .body()
                .statusCode(200);

        Address actual = repository.findById(address1.getId()).get();
        assertThat(actual.getCity(), is(equalTo(update.getCity())));
    }
}