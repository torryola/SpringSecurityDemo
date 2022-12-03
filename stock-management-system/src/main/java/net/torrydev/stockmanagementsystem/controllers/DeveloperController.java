package net.torrydev.stockmanagementsystem.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/")
public class DeveloperController {

    @GetMapping(path = "secure/dev")
    public String developerEndpoint(){
        return "Admin and Developer only Resource";
    }

    @PostMapping(path = "secure/dev") // Admin Only
    public String createNewResource(@RequestBody String newRes){
        return String.format("New %s created", newRes);
    }
}
