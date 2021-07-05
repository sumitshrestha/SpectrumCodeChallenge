package com.spectrum.pojo;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class ApiResponse {
    public String agency;
    public MeasurementType measurementType;
    public List<Release> releases;
    public String version;
}
