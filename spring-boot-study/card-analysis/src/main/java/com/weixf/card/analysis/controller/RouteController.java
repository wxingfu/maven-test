package com.weixf.card.analysis.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RouteController {
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

}
