package thb.siprojektanamneseservice.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import thb.siprojektanamneseservice.model.Security;
import thb.siprojektanamneseservice.repository.SecurityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SecurityServiceTest {

    @InjectMocks
    SecurityService serviceUnderTest;

    @Mock
    SecurityRepository repository;

    List<Security> securities;
    Security security1;
    Security security2;

    @BeforeEach
    void setUpTest() {
        MockitoAnnotations.openMocks(this);

        securities = new ArrayList<>();
        security1 = new Security();
        security2 = new Security();

        security1.setSecretQuestion("My Boss");
        security1.setAnswer("The Lord");
        security2.setSecretQuestion("Who is the Savior");
        security2.setAnswer("Jesus Christ");
    }

    @Test
    void listAllTest() {
        when(repository.save(security1)).thenReturn(security1);
        when(repository.save(security2)).thenReturn(security2);

        securities.add(security1);
        securities.add(security2);

        when(repository.findAll()).thenReturn(securities);
        List<Security> securityList = serviceUnderTest.listAll();

        assertEquals(2, securityList.size());
    }

    @Test
    void getOneTest() {
        security1.setId(UUID.fromString("c90c0562-7722-4336-be08-3911c3c16398"));
        when(repository.save(security1)).thenReturn(security1);

        when(repository.findById(security1.getId())).thenReturn(java.util.Optional.ofNullable(security1));

        Security security = serviceUnderTest.getOne(security1.getId());

        assertEquals("c90c0562-7722-4336-be08-3911c3c16398", security.getId().toString());
    }

    @Test
    void createTest() {
        when(repository.save(security1)).thenReturn(security1);
        Security security = serviceUnderTest.create(security1);

        assertEquals("My Boss", security.getSecretQuestion());
        assertEquals("The Lord", security.getAnswer());
    }

    @Test
    void updateTest() {
        security1.setId(UUID.fromString("c90c0562-7722-4336-be08-3911c3c16398"));
        when(repository.save(security1)).thenReturn(security1);

        security1.setSecretQuestion(security2.getSecretQuestion());
        security1.setAnswer(security2.getAnswer());

        when(repository.save(security1)).thenReturn(security1);
        when(repository.findById(security1.getId())).thenReturn(java.util.Optional.ofNullable(security1));

        Security security = serviceUnderTest.update(security1.getId(), security1);

        assertEquals("Jesus Christ", security.getAnswer());
        assertEquals("Who is the Savior", security.getSecretQuestion());
    }
}