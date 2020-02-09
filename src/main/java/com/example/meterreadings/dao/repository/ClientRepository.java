package com.example.meterreadings.dao.repository;

import com.example.meterreadings.dao.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Client findByName(String name);
}