package com.scsse.workflow.controller;

import com.scsse.workflow.entity.Charts;
import com.scsse.workflow.service.ChartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ChartsController {
    @Autowired
    private ChartsService chartsService;

    @GetMapping("findAll")
    public List<Charts> findAll(){
        return chartsService.findAll();
    }
}
