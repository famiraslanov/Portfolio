package com.tests.classes.api.dtos.response.action;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tests.classes.api.validation.Rule;
import com.tests.classes.api.validation.Rules;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionHydraMemberDTO
{
    @Rules(rule = Rule.notEmpty)
    public int id;

    @Rules(rule = Rule.notEmpty)
    public String[] investorsUrls;

    @Rules(rule = Rule.notEmpty)
    public String[] investmentsUrls;

    @Rules(rule = Rule.notEmpty)
    public String[] fundsUrls;

    @Rules(rule = Rule.notEmpty)
    public String[] LPOutreachesUrls;

    @Rules(rule = Rule.notEmpty)
    public String[] assetMandatesUrls;

    @Rules(rule = Rule.notEmpty)
    public String[] managementFirmsUrls;

    @Rules(rule = Rule.notEmpty)
    public String[] personRolesUrls;

    @Rules(rule = Rule.notEmpty)
    public Integer[] investors;

    @Rules(rule = Rule.notEmpty)
    public Integer[] investments;

    @Rules(rule = Rule.notEmpty)
    public Integer[] funds;

    @Rules(rule = Rule.notEmpty)
    public Integer[] LPOutreaches;

    @Rules(rule = Rule.notEmpty)
    public Integer[] assetMandates;

    @Rules(rule = Rule.notEmpty)
    public Integer[] managementFirms;

    @Rules(rule = Rule.notEmpty)
    public Integer[] serviceProviders;

    @Rules(rule = Rule.notEmpty)
    public Integer[] personRoles;

    @Rules(rule = Rule.notEmpty)
    public String description;

    @Rules(rule = Rule.notEmpty)
    public Date date;

    @Rules(rule = Rule.notEmpty)
    public String source;

    @Rules(rule = Rule.notEmpty)
    public String additionalDocumentation;

    @Rules(rule = Rule.notEmpty)
    public String[] tags;

    @Rules(rule = Rule.notEmpty)
    public ZonedDateTime deletedAt;

    @Rules(rule = Rule.notEmpty)
    public ZonedDateTime createdAt;

    @Rules(rule = Rule.notEmpty)
    public ZonedDateTime updatedAt;
}
