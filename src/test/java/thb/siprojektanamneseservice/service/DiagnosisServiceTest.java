package thb.siprojektanamneseservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import thb.siprojektanamneseservice.model.Diagnosis;
import thb.siprojektanamneseservice.model.Person;
import thb.siprojektanamneseservice.model.constants.ExaminationValues;
import thb.siprojektanamneseservice.repository.DiagnosisRepository;
import thb.siprojektanamneseservice.transfert.DiagnosisTO;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class DiagnosisServiceTest {

    @Mock
    DiagnosisRepository repository;
    @Mock
    PersonService personService;
    @InjectMocks
    DiagnosisService serviceUnderTest;

    Diagnosis diagnosis1;
    UUID id;

    @BeforeEach
    void setUpTest() {
        MockitoAnnotations.openMocks(this);

        id = UUID.fromString("c90c0562-7722-4336-be08-3911c3c16398");
        diagnosis1 = new Diagnosis();

        diagnosis1.setExaminationName(ExaminationValues.Magnetic_Resonance_Imaging.name());
        diagnosis1.setExaminationDate(Date.from(Instant.now()));
        diagnosis1.setBodyRegion("Head");
    }

    @Test
    void listAllTest() {
        Diagnosis diagnosis2 = diagnosis1;
        List<Diagnosis> diagnoses = new ArrayList<>();
        diagnoses.add(diagnosis1);
        diagnoses.add(diagnosis2);

        when(repository.save(diagnosis1)).thenReturn(diagnosis1);
        when(repository.save(diagnosis2)).thenReturn(diagnosis2);

        when(repository.findAll()).thenReturn(diagnoses);
        List<Diagnosis> diagnosisList = serviceUnderTest.listAll();

        assertEquals(2, diagnosisList.size());
    }

    @Test
    void getOneTest() {
        diagnosis1.setId(id);
        when(repository.save(diagnosis1)).thenReturn(diagnosis1);
        when(repository.findById(id)).thenReturn(java.util.Optional.ofNullable(diagnosis1));

        Diagnosis diagnosis = serviceUnderTest.getOne(id);

        assertEquals("Magnetic_Resonance_Imaging", diagnosis.getExaminationName());
    }

    @Test
    void updateTest() {
        diagnosis1.setId(id);
        when(repository.save(diagnosis1)).thenReturn(diagnosis1);

        diagnosis1.setExaminationName(ExaminationValues.PALPATION.name());
        diagnosis1.setBodyRegion("Foot");

        when(repository.findById(id)).thenReturn(java.util.Optional.ofNullable(diagnosis1));

        Diagnosis diagnosis = serviceUnderTest.update(id, diagnosis1);

        assertEquals("Foot", diagnosis.getBodyRegion());
    }
}