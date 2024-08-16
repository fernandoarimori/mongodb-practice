package com.mongo.mongo.exercise.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PayCond {
    MADE("Made"),
    OPEN("Open"),
    DONE("Done");

    private final String text;
}
