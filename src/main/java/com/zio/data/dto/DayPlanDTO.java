package com.zio.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DayPlanDTO {
    GeneralDTO breakfast, lunch, dinner;
}
