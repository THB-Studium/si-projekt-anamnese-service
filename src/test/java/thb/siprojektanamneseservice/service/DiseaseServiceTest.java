package thb.siprojektanamneseservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import thb.siprojektanamneseservice.model.Disease;
import thb.siprojektanamneseservice.model.Illness;
import thb.siprojektanamneseservice.model.Person;
import thb.siprojektanamneseservice.model.constants.ExaminationValues;
import thb.siprojektanamneseservice.model.constants.IllnessValue;
import thb.siprojektanamneseservice.repository.DiseaseRepository;
import thb.siprojektanamneseservice.repository.IllnessRepository;
import thb.siprojektanamneseservice.transfert.DiseaseTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DiseaseServiceTest {

    @Mock
    DiseaseRepository repository;
    @Mock
    IllnessRepository illnessRepository;
    @Mock
    PersonService personService;
    @InjectMocks
    DiseaseService serviceUnderTest;

    Disease disease1;
    Illness illness1;
    Illness illness2;
    List<Illness> illnesses;
    UUID id;

    @BeforeEach
    void setUpTest() {
        MockitoAnnotations.openMocks(this);

        id = UUID.fromString("c90c0562-7722-4336-be08-3911c3c16398");
        illness1 = new Illness();
        illness2 = new Illness();
        disease1 = new Disease();
        illnesses = new ArrayList<>();

        illness2.setName(IllnessValue.VASCULAR_DISEASES.name());
        illness1.setName(IllnessValue.EAR_DISEASE.name());

        illnesses.add(illness1);
        illnesses.add(illness2);

        disease1.setUndergoneSurgery(true);
        disease1.setSurgeriesDetails(ExaminationValues.COMPUTER_TOMOGRAM.name());
        disease1.setPreExistingIllnesses(illnesses);

    }

    @Test
    void listAllTest() {
        Disease disease2 = disease1;
        List<Disease> diseases = new ArrayList<>();
        diseases.add(disease1);
        diseases.add(disease2);

        when(repository.save(disease1)).thenReturn(disease1);
        when(repository.save(disease2)).thenReturn(disease2);

        when(repository.findAll()).thenReturn(diseases);
        List<Disease> diseaseList = serviceUnderTest.listAll();

        assertEquals(2, diseaseList.size());
    }

    @Test
    void getOneTest() {
        disease1.setId(id);
        when(repository.save(disease1)).thenReturn(disease1);
        when(repository.findById(id)).thenReturn(java.util.Optional.ofNullable(disease1));

        Disease disease = serviceUnderTest.getOne(id);

        assertEquals("VASCULAR_DISEASES", disease.getPreExistingIllnesses().get(1).getName());
    }

    @Test
    void createDiseaseAndGetSurgeryDetailsTest() {
        DiseaseTO diseaseTO = new DiseaseTO();
        diseaseTO.setUndergoneSurgery(true);
        diseaseTO.setSurgeriesDetails(ExaminationValues.COMPUTER_TOMOGRAM.name());
        diseaseTO.setPreExistingIllnesses(illnesses);
        diseaseTO.setPatientId(id);

        when(illnessRepository.save(illness1)).thenReturn(illness1);
        when(illnessRepository.save(illness2)).thenReturn(illness2);

        when(illnessRepository.findByName(illness1.getName())).thenReturn(illness1);
        when(illnessRepository.findByName(illness2.getName())).thenReturn(illness2);

        Person person = new Person();
        person.setId(id);
        when(personService.create(any())).thenReturn(person);
        when(personService.getOne(id)).thenReturn(person);

        disease1.setPerson(person);
        when(repository.save(disease1)).thenReturn(disease1);

        Disease disease = serviceUnderTest.create(diseaseTO);

        assertEquals("COMPUTER_TOMOGRAM", disease.getSurgeriesDetails());
    }

    @Test
    void updateTest() {
        disease1.setId(id);
        when(repository.save(disease1)).thenReturn(disease1);
        when(repository.findById(id)).thenReturn(java.util.Optional.ofNullable(disease1));

        disease1.setSurgeriesDetails(ExaminationValues.PALPATION.name());
        when(repository.save(disease1)).thenReturn(disease1);
        when(repository.findById(id)).thenReturn(java.util.Optional.ofNullable(disease1));

        Disease disease = serviceUnderTest.update(id, disease1);

        assertEquals("PALPATION", disease.getSurgeriesDetails());
    }
}