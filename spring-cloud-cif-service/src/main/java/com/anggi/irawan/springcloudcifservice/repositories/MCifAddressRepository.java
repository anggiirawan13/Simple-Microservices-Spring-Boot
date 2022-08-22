package com.anggi.irawan.springcloudcifservice.repositories;

import com.anggi.irawan.springcloudcifservice.models.MCifAddressModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MCifAddressRepository extends JpaRepository<MCifAddressModel, String> {
}
