package com.moon.moonfamily.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PopularSearchDto {
    private String popularTerm;
    private int popularSearchCount;
}
