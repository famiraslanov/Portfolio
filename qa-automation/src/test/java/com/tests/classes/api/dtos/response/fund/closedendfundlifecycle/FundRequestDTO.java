package com.tests.classes.api.dtos.response.fund.closedendfundlifecycle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FundRequestDTO
{
    @JsonProperty("closedEndFund")
    private String closedEndFund;

    @JsonProperty("lifecycle")
    private String lifecycle;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("committedCapital")
    private double committedCapital;

    @JsonProperty("date")
    private String date;

    @JsonProperty("estimate")
    private boolean estimate;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("deletedAt")
    private String deletedAt;

    public static FundRequestDTO copy(FundResponseDTO.Fund fund)
    {
        return new FundRequestDTO(
                fund.getClosedEndFund(),
                fund.getLifecycle(),
                fund.getCurrency(),
                fund.getCommittedCapital(),
                fund.getDate(),
                fund.isEstimate(),
                fund.getComment(),
                fund.getDeletedAt()
        );
    }
}
