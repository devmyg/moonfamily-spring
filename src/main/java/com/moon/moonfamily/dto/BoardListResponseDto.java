package com.moon.moonfamily.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardListResponseDto {
    private List<BoardListDto> boardList;
    private int totalPages;
}
