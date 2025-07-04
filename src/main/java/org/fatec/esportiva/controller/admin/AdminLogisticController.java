package org.fatec.esportiva.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/logistic")
public class AdminLogisticController {

    @GetMapping
    public String index(Model model) {
        return "admin/logistic/index";
    }
}
