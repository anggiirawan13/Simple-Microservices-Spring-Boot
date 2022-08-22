package com.anggi.irawan.springcloudcifservice.repositories;

import com.anggi.irawan.springcloudcifservice.models.MCifModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MCifRepository extends JpaRepository<MCifModel, String> {
}
