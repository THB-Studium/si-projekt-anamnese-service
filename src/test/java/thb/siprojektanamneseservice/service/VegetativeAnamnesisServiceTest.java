package thb.siprojektanamneseservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import thb.siprojektanamneseservice.model.VegetativeAnamnesis;
import thb.siprojektanamneseservice.model.constants.VegetativeAnamnesisDecisionValues;
import thb.siprojektanamneseservice.repository.VegetativeAnamnesisRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


class VegetativeAnamnesisServiceTest {

    @Mock
    VegetativeAnamnesisRepository repository;
    @Mock
    PersonService personService;
    @InjectMocks
    VegetativeAnamnesisService serviceUnderTest;

    VegetativeAnamnesis vegetativeAnamnesis1;
    UUID id;

    @BeforeEach
    void setUpTest() {
        MockitoAnnotations.openMocks(this);

        id = UUID.fromString("c90c0562-7722-4336-be08-3911c3c16398");

        vegetativeAnamnesis1 = new VegetativeAnamnesis();
        vegetativeAnamnesis1.setUrination(VegetativeAnamnesisDecisionValues.NORMAL.name());
        vegetativeAnamnesis1.setBowelMovement(VegetativeAnamnesisDecisionValues.TEETHING_TROUBLES.name());
        vegetativeAnamnesis1.setAppetite(VegetativeAnamnesisDecisionValues.NORMAL.name());
        vegetativeAnamnesis1.setThirst(VegetativeAnamnesisDecisionValues.FREQUENTLY.name());
        vegetativeAnamnesis1.setInsomnia(true);
        vegetativeAnamnesis1.setSleepDisorders(false);
        vegetativeAnamnesis1.setDate(Date.from(Instant.now()));

    }

    @Test
    void listAllTest() {
        VegetativeAnamnesis vegetativeAnamnesis2 = vegetativeAnamnesis1;
        List<VegetativeAnamnesis> anamnesisList = new ArrayList<>();

        vegetativeAnamnesis2.setUrination(VegetativeAnamnesisDecisionValues.BURNING.name());
        anamnesisList.add(vegetativeAnamnesis2);
        anamnesisList.add(vegetativeAnamnesis1);

        when(repository.save(vegetativeAnamnesis2)).thenReturn(vegetativeAnamnesis2);
        when(repository.save(vegetativeAnamnesis1)).thenReturn(vegetativeAnamnesis1);

        when(repository.findAll()).thenReturn(anamnesisList);
        List<VegetativeAnamnesis> vegetativeAnamneses = serviceUnderTest.listAll();

        assertEquals(2, vegetativeAnamneses.size());
    }

    @Test
    void getOneTest() {
        vegetativeAnamnesis1.setId(id);
        when(repository.save(vegetativeAnamnesis1)).thenReturn(vegetativeAnamnesis1);
        when(repository.findById(id)).thenReturn(java.util.Optional.ofNullable(vegetativeAnamnesis1));

        VegetativeAnamnesis anamnesis = serviceUnderTest.getOne(id);

        assertEquals("NORMAL", anamnesis.getAppetite());
    }

    @Test
    void updateTest() {
        vegetativeAnamnesis1.setId(id);
        when(repository.save(vegetativeAnamnesis1)).thenReturn(vegetativeAnamnesis1);
        when(repository.findById(id)).thenReturn(java.util.Optional.ofNullable(vegetativeAnamnesis1));

        vegetativeAnamnesis1.setUrination(VegetativeAnamnesisDecisionValues.INCREASED.name());
        when(repository.save(vegetativeAnamnesis1)).thenReturn(vegetativeAnamnesis1);
        when(repository.findById(id)).thenReturn(java.util.Optional.ofNullable(vegetativeAnamnesis1));

        VegetativeAnamnesis anamnesis = serviceUnderTest.update(vegetativeAnamnesis1.getId(), vegetativeAnamnesis1);

        assertEquals("INCREASED", anamnesis.getUrination(), "This test the update methode");
    }
}