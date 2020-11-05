package com.tech.paper.controller;
import com.tech.paper.service.ResetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResetController {
    @Autowired
    private ResetService resetService;

    @RequestMapping("/reset")
    public String reset() {
        resetService.reset();
        return "success";
    }

}
