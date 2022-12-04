package net.torrydev.stockmanagementsystem.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/")
public class DeveloperController {

    @GetMapping(path = "secure/dev")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DEVELOPER')")
    public String developerEndpoint(){
        return "Admin and Developer only Resource";
    }

    @PostMapping(path = "secure/dev") // Admin Only
    @PreAuthorize("hasAuthority('admin:write')")
//    @PreAuthorize("hasRole('ROLE_ADMIN'))")
    public String createNewResource(@RequestBody String newRes){
        return String.format("New %s created", newRes);
    }
}
