package thb.siprojektanamneseservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import thb.siprojektanamneseservice.model.FamilyAnamnesis;
import thb.siprojektanamneseservice.model.Illness;
import thb.siprojektanamneseservice.model.Person;
import thb.siprojektanamneseservice.repository.FamilyAnamnesisRepository;
import thb.siprojektanamneseservice.repository.IllnessRepository;
import thb.siprojektanamneseservice.transfert.FamilyAnamnesisTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class FamilyAnamnesisServiceTest {

    @Mock
    FamilyAnamnesisRepository repository;
    @Mock
    IllnessRepository illnessRepository;
    @Mock
    PersonService personService;
    @InjectMocks
    FamilyAnamnesisService serviceUnderTest;

    FamilyAnamnesis anamnesis1;

    List<Illness> father;
    List<Illness> mother;
    Illness illness1;
    Illness illness2;
    UUID id;

    @BeforeEach
    void setUpTest() {
        MockitoAnnotations.openMocks(this);

        father = new ArrayList<>();
        mother = new ArrayList<>();
        illness1 = new Illness();
        illness2 = new Illness();
        anamnesis1 = new FamilyAnamnesis();

        id = UUID.fromString("c90c0562-7722-4336-be08-3911c3c16398");

        illness1.setName("COVID-19");
        illness2.setName("Malaria");

        father.add(illness1);
        mother.add(illness2);

        anamnesis1.setFather(father);
        anamnesis1.setMother(mother);
    }

    @Test
    void listAllTest() {
        FamilyAnamnesis anamnesis2 = anamnesis1;
        List<FamilyAnamnesis> anamnesisList = new ArrayList<>();

        anamnesisList.add(anamnesis2);
        anamnesisList.add(anamnesis1);

        when(repository.save(anamnesis2)).thenReturn(anamnesis2);
        when(repository.save(anamnesis1)).thenReturn(anamnesis1);

        when(repository.findAll()).thenReturn(anamnesisList);
        List<FamilyAnamnesis> familyAnamneses = serviceUnderTest.listAll();

        assertEquals(2, familyAnamneses.size());
    }

    @Test
    void getOneTest() {
        anamnesis1.setId(id);
        when(repository.save(anamnesis1)).thenReturn(anamnesis1);
        when(repository.findById(id)).thenReturn(java.util.Optional.ofNullable(anamnesis1));

        FamilyAnamnesis anamnesis = serviceUnderTest.getOne(id);

        assertEquals("Malaria", anamnesis.getMother().get(0).getName());
    }

    @Test
    void createFamilyAnamnesisAndReturnFatherAndMotherDiseasesTest() {
        FamilyAnamnesisTO anamnesisTO = new FamilyAnamnesisTO();
        anamnesisTO.setPatientId(id);
        anamnesisTO.setFather(father);
        anamnesisTO.setMother(mother);

        when(illnessRepository.save(illness1)).thenReturn(illness1);
        when(illnessRepository.save(illness2)).thenReturn(illness2);

        when(illnessRepository.findByName(illness1.getName())).thenReturn(illness1);
        when(illnessRepository.findByName(illness2.getName())).thenReturn(illness2);

        Person person = new Person();
        person.setId(id);
        when(personService.create(any())).thenReturn(person);
        when(personService.getOne(id)).thenReturn(person);

        anamnesis1.setPerson(person);
        when(repository.save(anamnesis1)).thenReturn(anamnesis1);

        FamilyAnamnesis anamnesis = serviceUnderTest.create(anamnesisTO);

        assertEquals("COVID-19", anamnesis.getFather().get(0).getName());
        assertEquals("Malaria", anamnesis.getMother().get(0).getName());
    }

    @Test
    void updateTest() {
        anamnesis1.setId(id);
        when(repository.save(anamnesis1)).thenReturn(anamnesis1);
        when(repository.findById(id)).thenReturn(java.util.Optional.ofNullable(anamnesis1));

        mother.add(illness1);
        anamnesis1.setMother(mother);

        when(repository.save(anamnesis1)).thenReturn(anamnesis1);
        when(repository.findById(id)).thenReturn(java.util.Optional.ofNullable(anamnesis1));

        FamilyAnamnesis anamnesis = serviceUnderTest.update(id, anamnesis1);

        assertEquals(2, anamnesis.getMother().size());
    }
}