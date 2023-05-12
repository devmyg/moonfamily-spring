package com.moon.moonfamily.service;

import com.moon.moonfamily.dto.ResponseDto;
import com.moon.moonfamily.entity.PopularSearchEntity;
import com.moon.moonfamily.repository.PopularSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PopularSearchService {

    @Autowired
    PopularSearchRepository popularSearchRepository;

    public ResponseDto<List<PopularSearchEntity>> getPopularSearch() {
        List<PopularSearchEntity> popularSearchEntityList = new ArrayList<>();
        try {
            popularSearchEntityList = popularSearchRepository.findTop3ByOrderByPopularSearchCountDesc();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.setFailed("데이터베이스 에러");
        }
        return ResponseDto.setSuccess("성공", popularSearchEntityList);
    }
}
