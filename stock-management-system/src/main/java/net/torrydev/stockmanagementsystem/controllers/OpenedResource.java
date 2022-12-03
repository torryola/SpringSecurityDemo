package net.torrydev.stockmanagementsystem.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/open")
public class OpenedResource {

    @GetMapping(path = "/rc")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DEVELOPER','ROLE_USER')")
    public String openAccess(){
        return "Open Access Resource";
    }
}
