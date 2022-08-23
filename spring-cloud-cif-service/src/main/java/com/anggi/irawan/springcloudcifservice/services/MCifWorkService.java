package com.anggi.irawan.springcloudcifservice.services;

import com.anggi.irawan.springcloudcifservice.dtos.request.MCifWorkRequest;
import com.anggi.irawan.springcloudcifservice.dtos.response.DefaultResponse;

public interface MCifWorkService {

    DefaultResponse addMCifWork(MCifWorkRequest request);

    DefaultResponse updateMCifWork(String id, MCifWorkRequest request);

    DefaultResponse allDataMCifWork();

    DefaultResponse findByIdMCifWork(String id);

    DefaultResponse deleteMCifWork(String id);
}
