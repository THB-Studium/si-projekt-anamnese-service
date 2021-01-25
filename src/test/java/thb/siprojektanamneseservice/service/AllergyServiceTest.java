package thb.siprojektanamneseservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import thb.siprojektanamneseservice.model.Allergy;
import thb.siprojektanamneseservice.repository.AllergyRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AllergyServiceTest {

    @InjectMocks
    AllergyService serviceUnderTest;

    @Mock
    AllergyRepository repository;

    List<Allergy> allergies;
    Allergy allergy1;
    Allergy allergy2;

    @BeforeEach
    void setUpTest() {
        MockitoAnnotations.openMocks(this);

        allergies = new ArrayList<>();
        allergy1 = new Allergy();
        allergy2 = new Allergy();

        allergy1.setName("Pollen");
        allergy2.setName("Vaccine");
    }

    @Test
    void listAllTest() {
        when(repository.save(allergy1)).thenReturn(allergy1);
        when(repository.save(allergy2)).thenReturn(allergy2);

        allergies.add(allergy1);
        allergies.add(allergy2);

        when(repository.findAll()).thenReturn(allergies);
        List<Allergy> allergyList = serviceUnderTest.listAll();

        assertEquals(2, allergyList.size());
    }

    @Test
    void getOneTest() {
        allergy1.setId(UUID.fromString("c90c0562-7722-4336-be08-3911c3c16398"));
        when(repository.save(allergy1)).thenReturn(allergy1);

        when(repository.findById(allergy1.getId())).thenReturn(java.util.Optional.ofNullable(allergy1));

        Allergy allergy = serviceUnderTest.getOne(allergy1.getId());

        assertEquals("c90c0562-7722-4336-be08-3911c3c16398", allergy.getId().toString());
    }

    @Test
    void createTest() {
        when(repository.save(allergy1)).thenReturn(allergy1);
        Allergy allergy = serviceUnderTest.create(allergy1);

        assertEquals("Pollen", allergy.getName());
    }

    @Test
    void updateTest() {
        allergy1.setId(UUID.fromString("c90c0562-7722-4336-be08-3911c3c16398"));
        when(repository.save(allergy1)).thenReturn(allergy1);

        allergy1.setName(allergy2.getName());

        when(repository.save(allergy1)).thenReturn(allergy1);
        when(repository.findById(allergy1.getId())).thenReturn(java.util.Optional.ofNullable(allergy1));

        Allergy allergy = serviceUnderTest.update(allergy1.getId(), allergy1);

        assertEquals("Vaccine", allergy.getName());
    }
}