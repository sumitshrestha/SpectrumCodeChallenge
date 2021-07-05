package com.spectrum.controller;

import com.spectrum.pojo.Organization;
import com.spectrum.service.OrganizationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@RestController
public class RestEndpointController {
    private static final Log logger = LogFactory.getLog(RestEndpointController.class);

    @Autowired
    private OrganizationService organizationService;

    @RequestMapping("/spectrum-code-challenge/getOrganizationAsJson")
    @ResponseBody
    public HashMap<String, List<Organization>> getOrganizationAsJson() {
        System.out.println("controller called");
        HashMap<String, List<Organization>> map = new HashMap<>();
        map.put("organizations", organizationService.getOrganization());
        return map;
    }

    @RequestMapping("/spectrum-code-challenge/getOrganization.csv")
    @ResponseBody
    public String getOrganizationAsCsv(HttpServletResponse response) {
        System.out.println("controller called");
        List<Organization> list = organizationService.getOrganization();
        StringBuilder buffer = new StringBuilder();
        buffer.append("organization,release_count,total_labor_hours,all_in_production,licenses,most_active_months");
        buffer.append("\n");
        for (Organization org : list) {
            buffer.append(org.getOrganization());
            buffer.append(",");
            buffer.append(org.getRelease_count());
            buffer.append(",");
            buffer.append(org.getTotal_labor_hours());
            buffer.append(",");
            buffer.append(org.isAll_in_production());
            buffer.append(",");
            buffer.append(String.join("|", org.getLicenses().toArray(new String[0])));
            buffer.append(",");
            buffer.append(String.join("|", org.getMost_active_months().toArray(new String[0])));
            buffer.append("\n");
        }
        response.setContentType("text/plain; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename=a.csv");
        return buffer.toString();
    }
}
