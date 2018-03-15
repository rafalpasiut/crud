package com.crud.tasks.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class StaticWebPageController {

    @RequestMapping("{number}")
    public String index(Map<String, Object> model, @PathVariable Integer number) {
        model.put("variable", "My Thymeleaf variable");
        model.put("number", number);
        return "index";
    }
}
