package com.server.controller.implementation;

import com.server.controller.interfaces.ThirdPartyInterface;
import com.server.dto.SignUpThirdPartyRequest;
import com.server.dto.ThirdPartySummary;
import com.server.service.ThirdPartyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/thirdParty")
public class ThirdPartyController implements ThirdPartyInterface {
    @Autowired
    ThirdPartyService thirdPartyService;

    @ApiOperation(value = "See information of a third party user by ID")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ThirdPartySummary getThirdPartyUserById(@PathVariable Long id){
        return thirdPartyService.findThirdPartyUserById(id);
    }

    @ApiOperation(value = "Check all the third party users in the system")
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/thirdPartyUsers")
    @ResponseStatus(HttpStatus.OK)
    public List<ThirdPartySummary> getThirdPartyUsers(){
        return thirdPartyService.findAllThirdPartyUsers();
    }

    @ApiOperation(value = "Insert a new third party user[email and a password that will hashed to the key]. Method returns the ID generated for the user.")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createThirdPartyUser(@RequestBody SignUpThirdPartyRequest thirdPartyDTO){
        return thirdPartyService.registerThirdPartyUser(thirdPartyDTO);
    }
}
