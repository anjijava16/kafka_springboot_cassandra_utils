package com.mmtech.mmpocspringcass.web.controller;

import com.mmtech.mmpocspringcass.data.entity.ExampleTableDTO;
import com.mmtech.mmpocspringcass.service.ExampleTableService;

import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@Api(value = "API to perform CRUD operation in a Student database maintained in apache cassandra",
description = "This API provides the capability to perform CRUD operation in a Student " +
        "database maintained in apache cassandra", produces = "application/json")
public class ExampleTableController {

    static final String TEXT_FIELD_1_ENDPOINT = "/api/text_field_1/";

    private ExampleTableService ExampleTableService;

    @Autowired
    public ExampleTableController(ExampleTableService ExampleTableService) {
        this.ExampleTableService = ExampleTableService;
    }

    @GetMapping(value = TEXT_FIELD_1_ENDPOINT + "{textField1}")
    public ResponseEntity<Iterable<ExampleTableDTO>> returnExampleTablesForTextField1(@PathVariable String textField1) {
        Collection<ExampleTableDTO> ExampleTableDTOs = this.ExampleTableService.findByTextField1(textField1);
        return new ResponseEntity<>(ExampleTableDTOs, HttpStatus.OK);
    }

}
