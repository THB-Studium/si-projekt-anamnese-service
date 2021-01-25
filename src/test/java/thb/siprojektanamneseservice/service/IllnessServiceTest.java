package thb.siprojektanamneseservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import thb.siprojektanamneseservice.model.Illness;
import thb.siprojektanamneseservice.repository.IllnessRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class IllnessServiceTest {

    @InjectMocks
    IllnessService serviceUnderTest;

    @Mock
    IllnessRepository repository;

    List<Illness> illnesses;
    Illness illness1;
    Illness illness2;

    @BeforeEach
    void setUpTest() {
        MockitoAnnotations.openMocks(this);

        illnesses = new ArrayList<>();
        illness1 = new Illness();
        illness2 = new Illness();

        illness1.setName("COVID-19");
        illness2.setName("Malaria");
    }

    @Test
    void listAllTest() {
        when(repository.save(illness1)).thenReturn(illness1);
        when(repository.save(illness2)).thenReturn(illness2);

        illnesses.add(illness1);
        illnesses.add(illness2);

        when(repository.findAll()).thenReturn(illnesses);
        List<Illness> illnessList = serviceUnderTest.listAll();

        assertEquals(2, illnessList.size());
    }

    @Test
    void getOneTest() {
        illness1.setId(UUID.fromString("c90c0562-7722-4336-be08-3911c3c16398"));
        when(repository.save(illness1)).thenReturn(illness1);

        when(repository.findById(illness1.getId())).thenReturn(java.util.Optional.ofNullable(illness1));

        Illness illness = serviceUnderTest.getOne(illness1.getId());

        assertEquals("c90c0562-7722-4336-be08-3911c3c16398", illness.getId().toString());
    }

    @Test
    void createTest() {
        when(repository.save(illness1)).thenReturn(illness1);
        Illness illness = serviceUnderTest.create(illness1);

        assertEquals("COVID-19", illness.getName());
    }

    @Test
    void updateTest() {
        illness1.setId(UUID.fromString("c90c0562-7722-4336-be08-3911c3c16398"));
        when(repository.save(illness1)).thenReturn(illness1);

        illness1.setName(illness2.getName());

        when(repository.save(illness1)).thenReturn(illness1);
        when(repository.findById(illness1.getId())).thenReturn(java.util.Optional.ofNullable(illness1));

        Illness illness = serviceUnderTest.update(illness1.getId(), illness1);

        assertEquals("Malaria", illness.getName());
    }
}