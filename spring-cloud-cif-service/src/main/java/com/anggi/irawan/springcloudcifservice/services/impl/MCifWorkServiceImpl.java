package com.anggi.irawan.springcloudcifservice.services.impl;

import com.anggi.irawan.springcloudcifservice.dtos.request.MCifWorkRequest;
import com.anggi.irawan.springcloudcifservice.dtos.response.DefaultResponse;
import com.anggi.irawan.springcloudcifservice.dtos.response.MCifResponse;
import com.anggi.irawan.springcloudcifservice.dtos.response.MCifWorkResponse;
import com.anggi.irawan.springcloudcifservice.exceptions.BussinesException;
import com.anggi.irawan.springcloudcifservice.models.MCifModel;
import com.anggi.irawan.springcloudcifservice.models.MCifWorkModel;
import com.anggi.irawan.springcloudcifservice.repositories.MCifRepository;
import com.anggi.irawan.springcloudcifservice.repositories.MCifWorkRepository;
import com.anggi.irawan.springcloudcifservice.services.MCifWorkService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@CacheConfig(cacheNames = "cifWorkCache")
public class MCifWorkServiceImpl implements MCifWorkService {

    private MCifWorkRepository mCifWorkRepository;
    private MCifRepository cifRepository;


    private MCifWorkModel transformDataFromModelToRequest(MCifWorkRequest request ,
                                                          MCifModel mCif){
        return MCifWorkModel.builder()
                .address(request.getAddress())
                .mcif(mCif)
                .penghasilan(request.getPenghasilan())
                .createdBy("ADMIN")
                .createdAt(LocalDate.now())
                .isDeleted(false)
                .build();

    }

    private MCifWorkResponse transformDataFromModelToResponse(MCifWorkModel mCifWork, MCifResponse mCifResponse){
        MCifWorkResponse workResponse = new MCifWorkResponse();
        workResponse.setId(mCifWork.getId());
        workResponse.setAddress(mCifWork.getAddress());
        workResponse.setPenghasilan(mCifWork.getPenghasilan());
        workResponse.setCifId(mCifResponse);

        return  workResponse;
    }

    @SneakyThrows
    @Transactional
    @CacheEvict (cacheNames = "cifWork", allEntries = true)
    @Override
    public DefaultResponse addMCifWork(MCifWorkRequest request) {

        MCifModel findByMCif = cifRepository.findById(request.getCifId())
                .orElseThrow(() -> new BussinesException("DATA NOT FOUND"));

        MCifWorkModel mCifWork  = transformDataFromModelToRequest(request, findByMCif);

        MCifWorkModel responseSaved = mCifWorkRepository.save(mCifWork);

        MCifResponse mCifResponse = new MCifResponse().transformCifResponseFromModel(findByMCif);

        MCifWorkResponse workResponse = transformDataFromModelToResponse(responseSaved, mCifResponse);

        return new DefaultResponse(true, "Update data success", workResponse, null);

    }

    @SneakyThrows
    @Transactional
    @CacheEvict(cacheNames = "cifWork", key = "#id", allEntries = true)
    @Override
    public DefaultResponse updateMCifWork(String id, MCifWorkRequest request) {
        var cariDataCif = cifRepository.findById(request.getCifId())
                .orElseThrow(() -> new BussinesException("DATA NOT FOUND"));

        MCifWorkModel findByMCifWork = mCifWorkRepository.findById(id)
                .orElseThrow(() -> new BussinesException("DATA NOT FOUND"));

        findByMCifWork.setAddress(request.getAddress());
        findByMCifWork.setMcif(cariDataCif);
        findByMCifWork.setPenghasilan(request.getPenghasilan());

        MCifWorkModel saveData = mCifWorkRepository.save(findByMCifWork);

        MCifResponse responseWork = new MCifResponse().transformCifResponseFromModel(cariDataCif);

        MCifWorkResponse workResponse =  transformDataFromModelToResponse(saveData, responseWork);

        return new DefaultResponse(true, "Update data success", workResponse, null);
    }


    @SneakyThrows
    @Cacheable (cacheNames = "cifWork")
    @Override
    public DefaultResponse allDataMCifWork()
    {
        List<MCifWorkModel> findMciWorks = mCifWorkRepository.findAll();

        List<MCifWorkResponse> listWork = new ArrayList<>();

        findMciWorks.forEach(data -> {
            MCifResponse cifResponse = new MCifResponse().transformCifResponseFromModel(data.getMcif());
            MCifWorkResponse cifWorkResponse = transformDataFromModelToResponse(data, cifResponse);

            listWork.add(cifWorkResponse);
        });

        return new DefaultResponse(true, "Get list cif data success", listWork, null);
    }

    @SneakyThrows
    @Caching(evict = { @CacheEvict (cacheNames = "cifWorks", key = "#id"), @CacheEvict(cacheNames = "cifWork", allEntries = true) })
    @Transactional
    @Override
    public DefaultResponse deleteMCifWork(String id) {
        Optional<MCifWorkModel> findWorks = mCifWorkRepository.findById(id);
        if(findWorks.isEmpty()){
            throw new Exception("ID NOT FOUND");
        }

        mCifWorkRepository.deleteById(id);

        return new DefaultResponse(true, "Delete cif data success", null, null);
    }

    @SneakyThrows
    @Cacheable(cacheNames = "cifWorks", key = "#id", unless = "#result == null")
    @Transactional
    @Override
    public DefaultResponse findByIdMCifWork(String id) {
        MCifWorkModel cariData = mCifWorkRepository.findById(id)
                .orElseThrow(() -> new BussinesException("DATA NOT FOUNDS"));

        MCifWorkResponse response = new MCifWorkResponse();
        response.setId(cariData.getId());
        response.setAddress(cariData.getAddress());
        response.setPenghasilan(cariData.getPenghasilan());

        return new DefaultResponse(true, "Get cif data by id success", response, null);
    }

}
