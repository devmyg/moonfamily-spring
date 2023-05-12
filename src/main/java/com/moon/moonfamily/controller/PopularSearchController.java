package com.moon.moonfamily.controller;

import com.moon.moonfamily.dto.ResponseDto;
import com.moon.moonfamily.entity.PopularSearchEntity;
import com.moon.moonfamily.service.PopularSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PopularSearchController {

    @Autowired
    PopularSearchService popularSearchService;

    @GetMapping("/popularSearch")
    public ResponseDto<List<PopularSearchEntity>> getPopularSearch() {
        return popularSearchService.getPopularSearch();
    }
}
