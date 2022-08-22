package com.anggi.irawan.springcloudcifservice.services;

import com.anggi.irawan.springcloudcifservice.dtos.request.MCifFamilyRequest;
import com.anggi.irawan.springcloudcifservice.dtos.response.DefaultResponse;

public interface MCifFamilyService {

    DefaultResponse addMCifFamily(MCifFamilyRequest request);

    DefaultResponse updateMCifFamily(String id, MCifFamilyRequest request);

    DefaultResponse allDataMCifFamily();

    DefaultResponse findByIdMCifFamily(String id);

    void deleteMCifFamily(String id);
}
