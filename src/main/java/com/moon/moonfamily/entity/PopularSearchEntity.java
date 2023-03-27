package com.moon.moonfamily.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="PopularSearch")
@Table(name="PopularSearch")
public class PopularSearchEntity {
    @Id
    private String popularTerm;
    private int popularSearchCount;
}
