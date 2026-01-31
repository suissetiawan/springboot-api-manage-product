package com.dibimbing.api_manage_product.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class welkamController {
    @GetMapping({ "/", "/welcome" })
    public String welkam() {
        return "Welkam to API Manage Product";
    }
}
