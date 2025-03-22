package org.fatec.esportiva.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/analysis")
public class AdminAnalisys {

    @GetMapping
    public String inProcessing(Model model) {
        return "admin/analysis";
    }
}
