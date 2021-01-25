package thb.siprojektanamneseservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import thb.siprojektanamneseservice.model.Address;
import thb.siprojektanamneseservice.repository.AddressRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


class AddressServiceTest {

    @InjectMocks
    AddressService serviceUnderTest;

    @Mock
    AddressRepository repository;

    List<Address> addresses;
    Address address1;
    Address address2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        addresses = new ArrayList<>();
        address1 = new Address();
        address2 = new Address();

        address1.setStreetAndNumber("Berliner str. 12");
        address1.setPostalCode("10245");
        address1.setCountry("Germany");
        address1.setCity("Berlin");

        address2.setStreetAndNumber("Douala str. 12");
        address2.setPostalCode("10245");
        address2.setCountry("Kamerun");
        address2.setCity("Douala");
    }

    @Test
    void createTest() {
        when(repository.save(address1)).thenReturn(address1);
        Address address = serviceUnderTest.create(address1);

        assertEquals("Berlin", address.getCity());
        assertEquals("10245", address.getPostalCode());
    }

    @Test
    void listAllTest() {
        when(repository.save(address2)).thenReturn(address2);
        when(repository.save(address1)).thenReturn(address1);

        addresses.add(address1);
        addresses.add(address2);

        when(repository.findAll()).thenReturn(addresses);
        List<Address> addressList = serviceUnderTest.listAll();

        assertEquals(2, addressList.size());
    }

    @Test
    void getOneTest() {
        address2.setId(UUID.fromString("c90c0562-7722-4336-be08-3911c3c16398"));
        when(repository.save(address2)).thenReturn(address2);

        when(repository.findById(address2.getId())).thenReturn(java.util.Optional.ofNullable(address2));

        Address address = serviceUnderTest.getOne(address2.getId());

        assertEquals("c90c0562-7722-4336-be08-3911c3c16398", address.getId().toString());
    }

    @Test
    void updateTest() {
        address2.setId(UUID.fromString("c90c0562-7722-4336-be08-3911c3c16398"));
        when(repository.save(address2)).thenReturn(address2);

        address2.setCity("Yaounde");
        address2.setStreetAndNumber("Simalen street 1");

        when(repository.save(address2)).thenReturn(address2);
        when(repository.findById(address2.getId())).thenReturn(java.util.Optional.ofNullable(address2));

        Address address = serviceUnderTest.update(address2.getId(), address2);

        assertEquals("Yaounde", address.getCity());
        assertEquals("Simalen street 1", address.getStreetAndNumber());
    }
}