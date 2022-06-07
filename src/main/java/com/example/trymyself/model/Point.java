package com.example.trymyself.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor

@AllArgsConstructor
public class Point {

    private Long id;
    private String lat;
    private String lon;

//    private List<Edge> edges = new ArrayList<>();





}
