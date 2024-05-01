package com.aao.queryapp.QueryApp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.aao.queryapp.QueryApp.Entities.Models;
import com.aao.queryapp.QueryApp.dto.request.ModelRequestDTO;
import com.aao.queryapp.QueryApp.repository.ModelsRepository;
import com.aao.queryapp.QueryApp.services.query.Query;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("models")
public class ModelsController {

    @Autowired
    ModelsRepository modelsRepository;


    @GetMapping("list")
    public Iterable<Models> getMethodName() {
        return modelsRepository.findAll();
    }

    @GetMapping("query")
    public String getQueryString(
        @RequestParam("parentModelId") int parentModelId, 
        @RequestParam("childModelId") int childModelId
    ) {

        Optional<Models> parentModel = modelsRepository.findById(parentModelId);
        Optional<Models> childModel = modelsRepository.findById(childModelId);

        ArrayList<Models> childModels = new ArrayList();
        
        if (parentModel.isPresent()) {
            Models present = parentModel.get();
            if (childModel.isPresent()) {
                childModels.add(childModel.get());
                
                Query query = new Query(present, childModels);
                return query.createQuery();
            }else{
                Query query = new Query(present);
                return query.createQuery();
            }
        }
        return "query.toString()";
    }

    @PostMapping("add")
    public ResponseEntity<Boolean> postMethodName(@RequestBody ModelRequestDTO modelRequestDTO) {
        Models model;
        if (modelRequestDTO.getOptionalModelId().isPresent()) {
            Integer id = modelRequestDTO.getOptionalModelId().get();
            Optional<Models> join = modelsRepository.findById(id);
            if (join.isPresent()) {
                model = new Models();
                model.setAlias(modelRequestDTO.getAlias());
                model.setModelJoinColum(modelRequestDTO.getModelNameJoinColum());
                model.setName(modelRequestDTO.getName());
                model.setTargetJoinColumn(modelRequestDTO.getTargetJoinColumn());
                model.setJoinModel(join.get());
                modelsRepository.save(model);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            model = new Models();
            model.setAlias(modelRequestDTO.getAlias());
            model.setModelJoinColum(modelRequestDTO.getModelNameJoinColum());
            model.setName(modelRequestDTO.getName());
            model.setTargetJoinColumn(modelRequestDTO.getTargetJoinColumn());
            modelsRepository.save(model);
        }

       
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
