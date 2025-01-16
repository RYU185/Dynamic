package com.dw.dynamic.controller;

import com.dw.dynamic.model.FormationData;
import com.dw.dynamic.service.FormationDataService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/formationdata")
public class FormationDataController {
    @Autowired
    FormationDataService formationDataService;

    @GetMapping("/all")
    public ResponseEntity<List<FormationData>> getAllFormationData(){
        return new ResponseEntity<>(
                formationDataService.getAllFormationData(),
                HttpStatus.OK
        );
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<FormationData> getFormationDataById(@PathVariable Long id){
        return new ResponseEntity<>(
                formationDataService.getFormationDataById(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<FormationData>>getFormationDataByTitle(@PathVariable String title){
        return new ResponseEntity<>(
                formationDataService.getFormationDataByTitle(title),
                HttpStatus.OK
        );
    }
    @PostMapping("/save")
    public ResponseEntity<FormationData> saveFormationData(@RequestBody FormationData formationData, HttpServletRequest request){
        return new ResponseEntity<>(
                formationDataService.saveFormationData(formationData,request),
                HttpStatus.OK
        );
    }
    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteFormationData(@PathVariable Long id,  HttpServletRequest request){
        return new ResponseEntity<>(
                formationDataService.deleteFormationData(id, request),
                HttpStatus.OK
        );
    }
}
