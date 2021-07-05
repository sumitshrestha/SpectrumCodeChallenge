package com.spectrum.service;

import com.spectrum.pojo.ApiResponse;
import com.spectrum.pojo.License;
import com.spectrum.pojo.Organization;
import com.spectrum.pojo.Release;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrganizationService {
    String endpoint = "https://www.energy.gov/sites/prod/files/2020/12/f81/code-12-15-2020.json";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public List<Organization> getOrganization() {
        RestTemplate restTemplate = new RestTemplate();
        ApiResponse obj = restTemplate.getForObject(endpoint, ApiResponse.class);
        System.out.println(obj.toString());
        List<Release> releases = Optional.ofNullable(obj.getReleases()).orElse(new LinkedList<>());
        Map<String, Organization> map = new HashMap<>();
        Map<String, int[]> releaseDate = new HashMap<>();
        for (Release release : releases) {
            if (map.containsKey(release.organization)) {
                Organization organization = map.get(release.organization);
                organization.setTotal_labor_hours(organization.getTotal_labor_hours() + release.laborHours);
                organization.setAll_in_production(organization.isAll_in_production() && "Production".equals(release.status));
                organization.getLicenses().addAll(Optional.ofNullable(release.getPermissions().licenses).orElse(new LinkedList<>()).stream().map(License::getName).collect(Collectors.toSet()));
                try {
                    Date date = sdf.parse(release.getDate().getCreated());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(date.getTime());
                    int month = calendar.get(Calendar.MONTH);
                    releaseDate.get(release.getOrganization())[month]++;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                Organization organization = new Organization();
                organization.setOrganization(release.organization);
                organization.setTotal_labor_hours(release.getLaborHours());
                organization.setAll_in_production("Production".equals(release.status));
                organization.setLicenses(Optional.ofNullable(release.getPermissions().licenses).orElse(new LinkedList<>()).stream().map(License::getName).collect(Collectors.toSet()));
                try {
                    Date date = sdf.parse(release.getDate().getCreated());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(date.getTime());
                    int month = calendar.get(Calendar.MONTH);
                    int[] array = new int[12];
                    array[month] = 1;
                    releaseDate.put(release.getOrganization(), array);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                map.put(release.organization, organization);
            }

        }
        LinkedList<Organization> list = new LinkedList<>(map.values());
        for (Organization org : list) {
            if (releaseDate.containsKey(org.getOrganization())) {
                int[] month = releaseDate.get(org.getOrganization());
                Arrays.sort(month);
                List<String> most_active_months = new LinkedList<>();
                int max = -1;
                for (int i = month.length - 1; i >= 0; i--) {
                    if (max < 0)
                        max = month[i];
                    if (max > month[i]) {
                        break;
                    }
                    most_active_months.add(String.valueOf(i));
                }
                org.setMost_active_months(most_active_months);
            }
        }
        return list;
    }
}
