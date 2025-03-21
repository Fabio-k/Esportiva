package org.fatec.esportiva.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/logistic")
public class AdminLogisticController {

    @GetMapping({ "/index", "/in_processing" })
    public String inProcessing(Model model) {
        return "admin/logistic/in_processing";
    }

    @GetMapping("/in_transit")
    public String inTransit(Model model) {
        return "admin/logistic/in_transit";
    }

    @GetMapping("/returning")
    public String inReturning(Model model) {
        return "admin/logistic/returning";
    }

    @GetMapping("/returned")
    public String inReturned(Model model) {
        return "admin/logistic/returned";
    }

}
