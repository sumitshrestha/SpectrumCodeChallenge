package com.spectrum.pojo;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class Release {
    public Contact contact;
    public Date date;
    public String description;
    public double laborHours;
    public String name;
    public String organization;
    public Permissions permissions;
    public String repositoryURL;
    public String status;
    public List<String> tags;
    public String vcs;
    public List languages;
}
