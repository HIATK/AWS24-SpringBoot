package org.zerock.b01.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChartData {
    private String label;
    private int value;

    public ChartData(String item,int cell) {
        this.label=item;
        this.value=cell;
    }

    // Getters and Setters
}