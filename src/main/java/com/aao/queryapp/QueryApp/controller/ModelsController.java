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
                // String query = createQuery(present, childModels);
                return query.createQuery();
            }else{
                return selectPhase(present);
            }
        }
        return "query.toString()";
    }

    @PostMapping("add")
    public ResponseEntity<Boolean> postMethodName(@RequestBody ModelRequestDTO modelRequestDTO) {
        //TODO: process POST request
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

    private String selectPhase(Models model){

        StringBuilder select = new StringBuilder();
        String whiteSpace = " ";
        String carryReturn = "\n";
        String alias = model.getAlias();
        String baseModel = model.getName();
        select
        .append("SELECT * FROM")
        .append(whiteSpace)
        .append(baseModel);

        if (alias != null) {
            select
            .append(whiteSpace)
            .append(alias);
        }

        select.append(carryReturn);

        return select.toString();
    }

    private String joinPhase(Models parentModel, Models chilModel){
        StringBuilder join = new StringBuilder();
        String JOIN = "JOIN";
        String ON = "ON";
        String equalTo = "=";
        String point = ".";
        String whiteSpace = " ";

        String childModelName = chilModel.getName();
        String childModelAlias = chilModel.getAlias();
        String childModelColumnToJoin = chilModel.getModelJoinColum();

        String parentModelAlias = parentModel.getAlias();
        String parentColumnToJoin = parentModel.getTargetJoinColumn();

        join
            .append(JOIN)
            .append(whiteSpace)
            .append(childModelName)
            .append(whiteSpace)
            .append(childModelAlias)
            .append(whiteSpace)
            .append(ON)
            .append(whiteSpace)
            .append(childModelAlias)
            .append(point)
            .append(childModelColumnToJoin)
            .append(whiteSpace)
            .append(equalTo)
            .append(whiteSpace)
            .append(parentModelAlias)
            .append(point)
            .append(parentColumnToJoin);

        return join.toString();
    }

    private String createQuery(Models parentModel, ArrayList<Models> joinModels){
        StringBuilder query = new StringBuilder();
        String carryReturn = "\n";
        String closeQuery = ";";
        query
            .append(selectPhase(parentModel))
            .append(carryReturn);

        joinModels.forEach(joinModel -> {
            query.append(joinPhase(parentModel, joinModel));
        });
        
        query
            .append(closeQuery);
        
        return query.toString();
    }
    
    private String createQuery(Models parentModel){
        StringBuilder query = new StringBuilder();
        String closeQuery = ";";
        query
            .append(selectPhase(parentModel))
            .append(closeQuery);
        
        return query.toString();
    }
    
    
    
}
