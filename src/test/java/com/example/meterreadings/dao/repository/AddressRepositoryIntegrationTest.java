package com.example.meterreadings.dao.repository;

import com.example.meterreadings.dao.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AddressRepositoryIntegrationTest {

    private static final String CITY_SPLIT = "Split";

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void whenFindByCity_thenReturnAddress() {
        List<Address> found = addressRepository.findByCity(CITY_SPLIT);
        assertThat(found.stream().allMatch(address -> CITY_SPLIT.equals(address.getCity())), is(true));
    }
}