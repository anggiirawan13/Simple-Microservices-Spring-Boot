package com.anggi.irawan.springcloudcifservice.controllers;

import com.anggi.irawan.springcloudcifservice.components.endpoints.MCifFamilyEndpoint;
import com.anggi.irawan.springcloudcifservice.dtos.request.MCifFamilyRequest;
import com.anggi.irawan.springcloudcifservice.dtos.response.DefaultResponse;
import com.anggi.irawan.springcloudcifservice.services.MCifFamilyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = MCifFamilyEndpoint.PATH)
@AllArgsConstructor
public class MCifFamilyController {

    private MCifFamilyService mCifFamilyService;

    @PostMapping
    public DefaultResponse addMCif(@RequestBody MCifFamilyRequest request) {
        return mCifFamilyService.addMCifFamily(request);
    }

    @PutMapping(path = MCifFamilyEndpoint.PATH_URL)
    public DefaultResponse updateMCif(@PathVariable(value = MCifFamilyEndpoint.VARIABLE_PATH_PARAM_ID) String id, @RequestBody MCifFamilyRequest request) {
        return mCifFamilyService.updateMCifFamily(id, request);
    }

    @GetMapping(value = MCifFamilyEndpoint.PATH_URL)
    public DefaultResponse listMCif() {
        return mCifFamilyService.allDataMCifFamily();
    }

    @DeleteMapping(value = MCifFamilyEndpoint.PATH_URL)
    public DefaultResponse deleteMCif(@PathVariable(value = MCifFamilyEndpoint.VARIABLE_PATH_PARAM_ID) String id) {
        return mCifFamilyService.deleteMCifFamily(id);
    }
}
