package com.scsse.workflow.service;

import com.scsse.workflow.entity.Charts;
import com.scsse.workflow.repository.ChartsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ChartsService {

    @Autowired
    private ChartsRepository chartsRepository;

    public List<Charts> findAll(){
        return chartsRepository.findAll();
    }
}
