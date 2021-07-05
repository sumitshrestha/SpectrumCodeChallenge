package com.spectrum.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class Organization {
    private String organization;
    private int release_count;
    private double total_labor_hours;
    private boolean all_in_production;
    private Set<String> licenses;
    private List<String> most_active_months;

}
