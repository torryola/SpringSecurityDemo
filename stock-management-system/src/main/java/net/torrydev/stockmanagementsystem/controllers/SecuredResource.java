package net.torrydev.stockmanagementsystem.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/secured")
public class SecuredResource {

    @GetMapping(path = "/auth")
    public String authorisedOnly(){
        return "Authorised User Only";
    }
}
