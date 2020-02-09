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

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void whenFindByName_thenReturnClient() {
        // given
        String clientName = "Intertrade";

        // when
        Client found = clientRepository.findByName(clientName);

        // then
        assertThat(found.getName(), is(clientName));
    }

    @Test
    public void whenFindByName_thenReturnClientAddressCity() {
        // given
        String clientName = "Intertrade";

        String city = "Veliko Kolo";

        // when
        Client found = clientRepository.findByName(clientName);

        // then
        assertThat(found.getAddress().getCity(), is(city));
    }

    @Test
    public void whenFindByName_thenReturnClientMeterSerialNumber() {
        // given
        String clientName = "Intertrade";

        String serialNumber = "274";

        // when
        Client found = clientRepository.findByName(clientName);

        // then
        assertThat(found.getMeter().getSerialNumber(), is(serialNumber));
    }
}