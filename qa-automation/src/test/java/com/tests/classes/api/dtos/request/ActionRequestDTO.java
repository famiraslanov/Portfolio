package com.tests.classes.api.dtos.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ActionRequestDTO
{
    @Builder.Default private int page = 1;
    @Builder.Default private int itemsPerPage = 100;
}
