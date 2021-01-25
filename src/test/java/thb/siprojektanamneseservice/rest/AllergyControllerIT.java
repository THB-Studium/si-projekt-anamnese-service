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
import thb.siprojektanamneseservice.model.Allergy;
import thb.siprojektanamneseservice.model.constants.AllergyValues;
import thb.siprojektanamneseservice.repository.AllergyRepository;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
class AllergyControllerIT extends ItBase {

    @Autowired
    AllergyRepository repository;

    Random random = new Random();
    Allergy allergy1;
    Allergy allergy2;

    @BeforeEach
    void setUp() throws Exception {
        super.setup();
        allergy1 = repository.save(build());
        allergy2 = repository.save(build());
    }

    @AfterEach
    void tearDown() throws Exception {
        super.cleanup();
    }

    private Allergy build() {
        Allergy allergy = new Allergy();
        allergy.setName(AllergyValues.ANIMAL_HAIR.name() + "-" + random.nextInt());
        return allergy;
    }

    @Test
    @DisplayName("Test the create() method. It checks if an allergy can be created")
    void createTest() {
        Allergy create = build();
        String name = preLoadedGiven.contentType(ContentType.JSON).body(create).log().body()
                .post(ApiConstants.ALLERGY_TYPE_ROOT)
                .then().log().body().statusCode(200)
                .extract().body().path("name");

        Allergy actual = repository.findByName(name);
        assertThat(actual, is(notNullValue()));
    }

    @Test
    void listAllTest() {
        preLoadedGiven.get(ApiConstants.ALLERGY_TYPE_ROOT).then().log().body().statusCode(200)
                .body("size()", is(equalTo(2)))
                .body("id", containsInAnyOrder(allergy1.getId().toString(), allergy2.getId().toString()))
                .body("name", containsInAnyOrder(allergy1.getName(), allergy2.getName()));
    }

    @Test
    @DisplayName("Test the getOne() method")
    void getOneTest() {
        preLoadedGiven.get(ApiConstants.ALLERGY_TYPE_ITEM, allergy1.getId()).then().log().body().statusCode(200)
                .body("id", is(equalTo(allergy1.getId().toString())))
                .body("name", is(equalTo(allergy1.getName())));
    }

    @Test
    @DisplayName("Test the getOne() method if the given id ist not found")
    void getOneNotFound() {
        preLoadedGiven.get(ApiConstants.ALLERGY_TYPE_ITEM, UUID.randomUUID())
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Test the delete() method")
    void deleteTest() {
        preLoadedGiven.delete(ApiConstants.ALLERGY_TYPE_ITEM, allergy1.getId())
                .then().statusCode(200);
        Optional<Allergy> actual = repository.findById(allergy1.getId());
        assertThat(actual, is(Optional.empty()));
    }

    @Test
    @DisplayName("Test the delete() method if the given id ist not found")
    void deleteNotFound() {
        preLoadedGiven.delete(ApiConstants.ALLERGY_TYPE_ITEM, UUID.randomUUID())
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Test the update() method")
    void updateTest() {
        Allergy update = build();
        preLoadedGiven.contentType(ContentType.JSON).body(update).log().body()
                .put(ApiConstants.ALLERGY_TYPE_ITEM, allergy1.getId())
                .then()
                .log()
                .body()
                .statusCode(200);

        Optional<Allergy> found = repository.findById(allergy1.getId());
        assertThat(found.get().getId(), is(equalTo(allergy1.getId())));
        assertThat(found.get().getName(), is(equalTo(update.getName())));
    }

    @Test
    @DisplayName("Test the update() method if the given id ist not found")
    void updateNotFound() {
        Allergy update = build();
        preLoadedGiven.contentType(ContentType.JSON).body(update)
                .put(ApiConstants.ALLERGY_TYPE_ITEM, UUID.randomUUID())
                .then()
                .log()
                .body()
                .statusCode(404);
    }

    @Test
    @DisplayName("Test the update() method when the city is updated")
    void updateNameTest() {
        Allergy update = build();
        update.setName(allergy1.getName());
        preLoadedGiven.contentType(ContentType.JSON).body(update).log().body()
                .put(ApiConstants.ALLERGY_TYPE_ITEM, allergy1.getId())
                .then()
                .log()
                .body()
                .statusCode(200);

        Allergy actual = repository.findById(allergy1.getId()).get();
        assertThat(actual.getName(), is(equalTo(update.getName())));
    }
}