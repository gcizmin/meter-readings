package com.example.meterreadings.dao.repository;

import com.example.meterreadings.dao.entity.Client;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ClientRepositoryIntegrationTest {

    private static final String CLIENT_NAME_INTERTRADE = "Intertrade";
    private static final String CITY_VELIKO_KOLO = "Veliko Kolo";
    private static final String SERIAL_NUMBER_274 = "274";

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void whenFindByName_thenReturnClient() {
        Client found = clientRepository.findByName(CLIENT_NAME_INTERTRADE);
        assertThat(found.getName(), is(CLIENT_NAME_INTERTRADE));
    }

    @Test
    public void whenFindByName_thenReturnClientAddressCity() {
        Client found = clientRepository.findByName(CLIENT_NAME_INTERTRADE);
        assertThat(found.getAddress().getCity(), is(CITY_VELIKO_KOLO));
    }

    @Test
    public void whenFindByName_thenReturnClientMeterSerialNumber() {
        Client found = clientRepository.findByName(CLIENT_NAME_INTERTRADE);
        assertThat(found.getMeter().getSerialNumber(), is(SERIAL_NUMBER_274));
    }
}