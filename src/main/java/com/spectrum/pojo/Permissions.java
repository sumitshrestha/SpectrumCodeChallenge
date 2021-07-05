package com.spectrum.pojo;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class Permissions {
    public Object exemptionText;
    public List<License> licenses;
    public String usageType;
}
