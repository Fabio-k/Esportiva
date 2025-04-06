package org.fatec.esportiva.controller;

import java.util.List;

import org.fatec.esportiva.request.LogDto;
import org.fatec.esportiva.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/logs")
public class AdminLogController {
    private final LogService logService;

    @GetMapping
    public String getClients(Model model){

        List<LogDto> logs = logService.getLogs();
        model.addAttribute("logs", logs);
        return "admin/logs";
    }
}
