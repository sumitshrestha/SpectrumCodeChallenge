package com.spectrum.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class License {
    @JsonProperty("URL")
    public String uRL;
    public String name;
}
