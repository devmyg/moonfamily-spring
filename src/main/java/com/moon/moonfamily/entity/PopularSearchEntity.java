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
@Entity(name="Popularsearch")
@Table(name="Popularsearch")
public class PopularSearchEntity {
    @Id
    private String popularTerm;
    private int popularSearchCount;

    public PopularSearchEntity(String value) {
        this.popularTerm = value;
        this.popularSearchCount = 1;
    }
}
