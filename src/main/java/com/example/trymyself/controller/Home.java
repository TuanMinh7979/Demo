package com.example.trymyself.controller;

import com.example.trymyself.util.OsmToDB;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class Home {

    private final OsmToDB osmToDB;

    @RequestMapping("settup")
    @ResponseBody
    public String index() {
        osmToDB.setupData();
        return "sc";
    }
}

