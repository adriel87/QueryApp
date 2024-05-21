package com.aao.queryapp.QueryApp.controller;

import com.aao.queryapp.QueryApp.Entities.ColumnName;
import com.aao.queryapp.QueryApp.Entities.Models;
import com.aao.queryapp.QueryApp.dto.request.AddColumnRequestDTO;
import com.aao.queryapp.QueryApp.dto.request.ModelRequestDTO;
import com.aao.queryapp.QueryApp.dto.request.ModelResponseDTO;
import com.aao.queryapp.QueryApp.dto.response.ModelsResponseDTO;
import com.aao.queryapp.QueryApp.repository.ColumnNameRepository;
import com.aao.queryapp.QueryApp.repository.ModelsRepository;
import com.aao.queryapp.QueryApp.services.query.JoinPhase;
import com.aao.queryapp.QueryApp.services.query.Query;
import com.aao.queryapp.QueryApp.services.query.SelectPhase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;


@RestController
@RequestMapping("models")
public class ModelsController {

    @Autowired
    ModelsRepository modelsRepository;

    @Autowired
    ColumnNameRepository columnNameRepository;

    @GetMapping("list")
    public ResponseEntity<Collection<ModelsResponseDTO>> getMethodName() {
        Iterable<Models> models = modelsRepository.findAll();
        ArrayList<ModelsResponseDTO> modelsList = new ArrayList<>();
        models.iterator().forEachRemaining(m -> {
            ModelsResponseDTO modelsResponseDTO = new ModelsResponseDTO();
            modelsResponseDTO.setName(m.getName());
            modelsList.add(modelsResponseDTO);
        });

        return new ResponseEntity<>(modelsList,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getModelById(@PathVariable(name = "id") int id) {
        Optional<Models> models = modelsRepository.findById(id);
        ModelResponseDTO modelResponseDTO = new ModelResponseDTO();
        modelResponseDTO.setChildrensID(new HashSet<>());

        if (models.isPresent()) {
            Models models2 = models.get();
            modelResponseDTO.setId(models2.getId().toString());
            modelResponseDTO.setAlias(models2.getAlias());

            if (!models2.getColumns().isEmpty()) {
                modelResponseDTO.setColumns(new HashSet<>());
                models2.getColumns().iterator().forEachRemaining(column -> modelResponseDTO.getColumns().add(column));
            }
            modelResponseDTO.setColumns(models2.getColumns());
            modelResponseDTO.setModelJoinColumn(models2.getModelJoinColum());
            if (models2.getModels() != null) {
                modelResponseDTO.setModelsID(models2.getModels().getId().toString());
            }
            modelResponseDTO.setName(models2.getName());
            models2.getChildren().iterator().forEachRemaining(child -> modelResponseDTO.getChildrensID().add(child.getId()));
            return new ResponseEntity<>(models2, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("query")
    public String getQueryString(
            @RequestParam("parentModelId") int parentModelId,
            @RequestParam("childModelId") int childModelId
    ) {

        Optional<Models> parentModel = modelsRepository.findById(parentModelId);
        Optional<Models> childModel = modelsRepository.findById(childModelId);
        Optional<ColumnName> optionalColumnName = columnNameRepository.findById(1);

        ArrayList<Models> childModels = new ArrayList<>();


        if (parentModel.isPresent()) {
            Models presentParent = parentModel.get();
            SelectPhase selectPhase;


            if (childModel.isPresent()) {
                childModels.add(childModel.get());
                HashSet<Models> presentChildrens = new HashSet<>();
                HashSet<ColumnName> columns = new HashSet<>();
                columns.add(optionalColumnName.get());
                presentChildrens.add(childModel.get());

                selectPhase = new SelectPhase(presentParent, presentChildrens, columns);
                JoinPhase joinPhase = new JoinPhase(presentChildrens);
                Query query = new Query(selectPhase, joinPhase);

                return query.createQuery();

            } else {
                selectPhase = new SelectPhase(presentParent);
                Query query = new Query(selectPhase);
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
                model.setModels(join.get());
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

    @PostMapping("/add/column")
    public ResponseEntity<?> addColumn(@RequestBody AddColumnRequestDTO addColumnRequestDTO) {

        ColumnName newColumnName = new ColumnName();


        Optional<Models> models = modelsRepository.findById(addColumnRequestDTO.getModelId());

        if (models.isPresent()) {

            newColumnName.setType(addColumnRequestDTO.getType());
            newColumnName.setName(addColumnRequestDTO.getName());
            newColumnName.setModels(models.get());
            ColumnName insertedColumnName = columnNameRepository.save(newColumnName);

            return new ResponseEntity<>(insertedColumnName, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

}
