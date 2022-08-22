package com.anggi.irawan.springcloudcifservice.repositories;

import com.anggi.irawan.springcloudcifservice.models.MCifWorkModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MCifWorkRepository extends JpaRepository<MCifWorkModel, String> {
}
