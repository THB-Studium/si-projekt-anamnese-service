package thb.siprojektanamneseservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import thb.siprojektanamneseservice.model.MedicationInTake;
import thb.siprojektanamneseservice.repository.MedicationInTakeRepository;
import thb.siprojektanamneseservice.transfert.MedicationInTakeTO;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class MedicationInTakeServiceTest {

    @Mock
    MedicationInTakeRepository repository;
    @Mock
    PersonService personService;
    @InjectMocks
    MedicationInTakeService serviceUnderTest;

    MedicationInTake medicationInTake1;
    MedicationInTake medicationInTake2;
    MedicationInTakeTO medicationInTakeTO;

    @BeforeEach
    void setUpTest() {
        MockitoAnnotations.openMocks(this);

        medicationInTake1 = new MedicationInTake();
        medicationInTake2 = new MedicationInTake();
        medicationInTakeTO = new MedicationInTakeTO();

        medicationInTake1.setBloodDiluent(true);
        medicationInTake1.setStartDate(Date.from(Instant.now()));
        medicationInTake1.setDosage("5 ml/Glass");
        medicationInTake1.setDesignation("Paracetamol");

        medicationInTake2.setBloodDiluent(false);
        medicationInTake2.setStartDate(Date.from(Instant.now()));
        medicationInTake2.setDosage("3 Mal/Tag");
        medicationInTake2.setDesignation("Hydroxychloroquine");

        medicationInTakeTO.setBloodDiluent(false);
        medicationInTakeTO.setStartDate(Date.from(Instant.now()));
        medicationInTakeTO.setDosage("3 Mal/Tag");
        medicationInTakeTO.setDesignation("Hydroxychloroquine");

    }

    @Test
    void listAllTest() {
        List<MedicationInTake> medicationInTakes = new ArrayList<>();
        medicationInTakes.add(medicationInTake1);
        medicationInTakes.add(medicationInTake2);

        when(repository.save(medicationInTake1)).thenReturn(medicationInTake1);
        when(repository.save(medicationInTake2)).thenReturn(medicationInTake2);

        when(repository.findAll()).thenReturn(medicationInTakes);
        List<MedicationInTake> medicationInTakeList = serviceUnderTest.listAll();

        assertEquals(2, medicationInTakeList.size());
    }

    @Test
    void getOneTest() {

        medicationInTake1.setId(UUID.fromString("c90c0562-7722-4336-be08-3911c3c16398"));
        when(repository.save(medicationInTake1)).thenReturn(medicationInTake1);
        when(repository.findById(medicationInTake1.getId())).thenReturn(java.util.Optional.ofNullable(medicationInTake1));

        MedicationInTake medicationInTake = serviceUnderTest.getOne(medicationInTake1.getId());

        assertEquals("Paracetamol", medicationInTake.getDesignation());
    }

    @Test
    void createMedicationAndGetDosageAndDesignationAnDateTest() {

        when(repository.save(medicationInTake2)).thenReturn(medicationInTake2);

        MedicationInTake medicationInTake = serviceUnderTest.create(medicationInTakeTO);

        assertEquals("3 Mal/Tag", medicationInTake.getDosage());
        assertEquals("Hydroxychloroquine", medicationInTake.getDesignation());
        assertEquals(medicationInTake2.getStartDate().toString(), medicationInTake.getStartDate().toString());
    }

    @Test
    void updateTest() {
        medicationInTake2.setId(UUID.fromString("c90c0562-7722-4336-be08-3911c3c16398"));
        when(repository.save(medicationInTake2)).thenReturn(medicationInTake2);

        medicationInTake2.setDesignation(medicationInTake1.getDesignation());
        medicationInTake2.setBloodDiluent(medicationInTake1.isBloodDiluent());

        when(repository.findById(medicationInTake2.getId())).thenReturn(java.util.Optional.ofNullable(medicationInTake2));

        MedicationInTake medicationInTake = serviceUnderTest.update(medicationInTake2.getId(), medicationInTake2);

        assertEquals("Paracetamol", medicationInTake.getDesignation());
        assertTrue(medicationInTake.isBloodDiluent());
    }
}