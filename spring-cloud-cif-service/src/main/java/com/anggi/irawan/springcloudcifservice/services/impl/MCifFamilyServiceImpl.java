package com.anggi.irawan.springcloudcifservice.services.impl;

import com.anggi.irawan.springcloudcifservice.dtos.request.MCifFamilyRequest;
import com.anggi.irawan.springcloudcifservice.dtos.response.DefaultResponse;
import com.anggi.irawan.springcloudcifservice.dtos.response.MCifFamilyResponse;
import com.anggi.irawan.springcloudcifservice.dtos.response.MCifResponse;
import com.anggi.irawan.springcloudcifservice.exceptions.BussinesException;
import com.anggi.irawan.springcloudcifservice.models.MCifFamilyModel;
import com.anggi.irawan.springcloudcifservice.models.MCifModel;
import com.anggi.irawan.springcloudcifservice.repositories.MCifFamilyRepository;
import com.anggi.irawan.springcloudcifservice.repositories.MCifRepository;
import com.anggi.irawan.springcloudcifservice.services.MCifFamilyService;
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
@CacheConfig(cacheNames = "cifFamilyCache")
public class MCifFamilyServiceImpl implements MCifFamilyService {

    private MCifFamilyRepository familyRepository;
    private MCifRepository mCifRepository;


    private MCifFamilyModel transformMCifFamilyToRequest(MCifFamilyRequest request,
                                                    MCifModel mCif){

        return MCifFamilyModel.builder()
                .type(request.getType())
                .mcif(mCif)
                .createdBy("ADMIN")
                .createdAt(LocalDate.now())
                .isDeleted(false)
                .build();
    }

    private MCifFamilyResponse transformMCifFamilyResponseToModel(MCifFamilyModel mCifFamily, MCifResponse mCifResponse) {
        MCifFamilyResponse familyResponse = new MCifFamilyResponse();
        familyResponse.setId(mCifFamily.getId());
        familyResponse.setType(mCifFamily.getType());
        familyResponse.setCifId(mCifResponse);

        return  familyResponse;
    }

    @SneakyThrows
    @Transactional
    @CacheEvict (cacheNames = "cifFamily", allEntries = true)
    @Override
    public DefaultResponse addMCifFamily(MCifFamilyRequest request) {
        Optional<MCifModel> findIdMci = mCifRepository.findById(request.getCifId());
        if(findIdMci.isEmpty()){
            throw new Exception("tidak ada data");
        }
        MCifModel dataDitemukan = findIdMci.get();

        MCifFamilyModel cifFamilyResponse = transformMCifFamilyToRequest(request, dataDitemukan);

        MCifFamilyModel responseSave = familyRepository.save(cifFamilyResponse);

        MCifResponse mCifResponse = new MCifResponse().transformCifResponseFromModel(dataDitemukan);

        MCifFamilyResponse familyResponse = transformMCifFamilyResponseToModel(responseSave, mCifResponse);

        return new DefaultResponse(true, "Add data success", familyResponse, null);

    }

    @SneakyThrows
    @Transactional
    @CacheEvict(cacheNames = "cifFamily", key = "#id", allEntries = true)
    @Override
    public DefaultResponse updateMCifFamily(String id, MCifFamilyRequest request) {

        Optional<MCifModel> findIdMci = mCifRepository.findById(request.getCifId());
        if(findIdMci.isEmpty()){
            throw new Exception("id tidak ditemukan");
        }

        MCifModel mCiFounds = findIdMci.get();

        Optional<MCifFamilyModel> findFamily = familyRepository.findById(id);
        if(findFamily.isEmpty()){
            throw  new Exception("tidak ditemukan");
        }

        findFamily.get().setType(request.getType());
        findFamily.get().setMcif(mCiFounds);

        MCifFamilyModel savedData = familyRepository.save(findFamily.get());

        MCifResponse responseFamily = new MCifResponse().transformCifResponseFromModel(mCiFounds);

        MCifFamilyResponse familyResponse = transformMCifFamilyResponseToModel(savedData, responseFamily);

        return new DefaultResponse(true, "Update data success", familyResponse, null);
    }

    @SneakyThrows
    @Cacheable(cacheNames = "cifFamily")
    @Override
    public DefaultResponse allDataMCifFamily() {

        List<MCifFamilyModel> findFamily = familyRepository.findAll();

        List<MCifFamilyResponse> listAddress = new ArrayList<>();

        findFamily.forEach(data -> {
            MCifResponse cifResponse = new MCifResponse().transformCifResponseFromModel(data.getMcif());
            MCifFamilyResponse cifFamilyResponse = transformMCifFamilyResponseToModel(data, cifResponse);

            listAddress.add(cifFamilyResponse);
        });

        return new DefaultResponse(true, "Get list cif data success", listAddress, null);
    }

    @SneakyThrows
    @Caching(evict = {@CacheEvict (cacheNames = "cifFamilys", key = "#id"), @CacheEvict(cacheNames = "cifFamily", allEntries = true)})
    @Transactional
    @Override
    public DefaultResponse deleteMCifFamily(String id) {
        Optional<MCifFamilyModel> findFamily = familyRepository.findById(id);
        if(findFamily.isEmpty()){
            throw new Exception("ID NOT FOUNDS");
        }

        familyRepository.deleteById(id);

        return new DefaultResponse(true, "Delete cif data success", null, null);
    }

    @SneakyThrows
    @Cacheable (cacheNames = "cifFamilys", key = "#id", unless = "#result == null")
    @Transactional
    @Override
    public DefaultResponse findByIdMCifFamily(String id) {
        MCifFamilyModel cariData = familyRepository.findById(id).orElseThrow(() -> new BussinesException("DATA NOT FOUNDS"));

        MCifFamilyResponse response = new MCifFamilyResponse();
        response.setId(cariData.getId());
        response.setType(cariData.getType());

        return new DefaultResponse(true, "Get cif data by id success", response, null);
    }
}
