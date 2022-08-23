package com.anggi.irawan.springcloudcifservice.controllers;

import com.anggi.irawan.springcloudcifservice.components.endpoints.MCifWorkEndpoint;
import com.anggi.irawan.springcloudcifservice.dtos.request.MCifWorkRequest;
import com.anggi.irawan.springcloudcifservice.dtos.response.DefaultResponse;
import com.anggi.irawan.springcloudcifservice.services.MCifWorkService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = MCifWorkEndpoint.PATH)
@AllArgsConstructor
public class MCifWorkController {

    private MCifWorkService mCifWorkService;

    @PostMapping
    public DefaultResponse addMCif(@RequestBody MCifWorkRequest request) {
        return mCifWorkService.addMCifWork(request);
    }

    @PutMapping(path = MCifWorkEndpoint.PATH_URL)
    public DefaultResponse updateMCif(@PathVariable(value = MCifWorkEndpoint.VARIABLE_PATH_PARAM_ID) String id, @RequestBody MCifWorkRequest request) {
        return mCifWorkService.updateMCifWork(id, request);
    }

    @GetMapping(value = MCifWorkEndpoint.PATH_URL)
    public DefaultResponse listMCif() {
        return mCifWorkService.allDataMCifWork();
    }

    @DeleteMapping(value = MCifWorkEndpoint.PATH_URL)
    public DefaultResponse deleteMCif(@PathVariable(value = MCifWorkEndpoint.VARIABLE_PATH_PARAM_ID) String id) {
        return mCifWorkService.deleteMCifWork(id);
    }
}
