package com.moon.moonfamily.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardWriteDto {
    private String boardTitle;
    private String boardContent;
    private String boardImage;
    private String boardWriterId;
}
