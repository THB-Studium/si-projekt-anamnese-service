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
import thb.siprojektanamneseservice.model.Security;
import thb.siprojektanamneseservice.repository.SecurityRepository;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class SecurityControllerIT extends ItBase {

    @Autowired
    SecurityRepository repository;

    Random random = new Random();
    Security security1;
    Security security2;

    @BeforeEach
    void setUp() throws Exception {
        super.setup();
        security1 = repository.save(build());
        security2 = repository.save(build());
    }

    private Security build() {
        Security security = new Security();
        security.setSecretQuestion("What to believe?" + "-" + random.nextInt());
        security.setAnswer("John 3:16" + "-" + random.nextInt());
        return security;
    }

    @AfterEach
    void tearDown() throws Exception {
        super.cleanup();
    }

    @Test
    void listAllTest() {
        preLoadedGiven.get(ApiConstants.SECURITY_ROOT).then().log().body().statusCode(200)
                .body("size()", is(equalTo(3)))
                .body("id", containsInAnyOrder(mySecurity.getId().toString(), security1.getId().toString(), security2.getId().toString()))
                .body("secretQuestion", containsInAnyOrder(mySecurity.getSecretQuestion(), security1.getSecretQuestion(), security2.getSecretQuestion()))
                .body("answer", containsInAnyOrder(mySecurity.getAnswer(), security1.getAnswer(), security2.getAnswer()));
    }

    @Test
    @DisplayName("Test the create() method. It checks if an address can be created")
    void createTest() {
        Security create = build();
        String id = preLoadedGiven.contentType(ContentType.JSON).body(create).log()
                .body()
                .post(ApiConstants.SECURITY_ROOT)
                .then()
                .log()
                .body()
                .statusCode(200).extract().body().path("id");

        Security actual = repository.findById(UUID.fromString(id)).get();
        assertThat(actual, is(notNullValue()));
        assertThat(actual.getAnswer(), is(equalTo(create.getAnswer())));
    }

    @Test
    @DisplayName("Test the getOne() method")
    void getOneTest() {
        preLoadedGiven.get(ApiConstants.SECURITY_ITEM, security1.getId()).then().log().body().statusCode(200)
                .body("id", is(equalTo(security1.getId().toString())))
                .body("secretQuestion", is(equalTo(security1.getSecretQuestion())))
                .body("answer", is(equalTo(security1.getAnswer())));
    }

    @Test
    @DisplayName("Test the getOne() method if the given id ist not found")
    void getOneNotFound() {
        preLoadedGiven.get(ApiConstants.SECURITY_ITEM, UUID.randomUUID())
                .then()
                .statusCode(404);
    }

    @Test
    void deleteTest() {
        preLoadedGiven.delete(ApiConstants.SECURITY_ITEM, security1.getId())
                .then().statusCode(200);
        Optional<Security> actual = repository.findById(security1.getId());
        assertThat(actual, is(Optional.empty()));
    }

    @Test
    @DisplayName("Test the delete() method if the given id ist not found")
    void deleteNotFound() {
        preLoadedGiven.delete(ApiConstants.SECURITY_ITEM, UUID.randomUUID())
                .then()
                .statusCode(404);
    }

    @Test
    void updateTest() {
        Security update = build();
        preLoadedGiven.contentType(ContentType.JSON).body(update).log().body()
                .put(ApiConstants.SECURITY_ITEM, security1.getId())
                .then()
                .log()
                .body()
                .statusCode(200);

        Optional<Security> found = repository.findById(security1.getId());
        assertThat(found.get().getId(), is(equalTo(security1.getId())));
        assertThat(found.get().getSecretQuestion(), is(equalTo(update.getSecretQuestion())));
        assertThat(found.get().getAnswer(), is(equalTo(update.getAnswer())));
    }

    @Test
    @DisplayName("Test the update() method if the given id ist not found")
    void updateNotFound() {
        Security update = build();
        preLoadedGiven.contentType(ContentType.JSON).body(update)
                .put(ApiConstants.SECURITY_ITEM, UUID.randomUUID())
                .then()
                .log()
                .body()
                .statusCode(404);
    }

    @Test
    @DisplayName("Test the update() method when the security question is updated")
    void updateQuestionTest() {
        Security update = build();
        update.setSecretQuestion(security1.getSecretQuestion());
        preLoadedGiven.contentType(ContentType.JSON).body(update).log().body()
                .put(ApiConstants.SECURITY_ITEM, security1.getId())
                .then()
                .log()
                .body()
                .statusCode(200);

        Security actual = repository.findById(security1.getId()).get();
        assertThat(actual.getSecretQuestion(), is(equalTo(update.getSecretQuestion())));
    }
}