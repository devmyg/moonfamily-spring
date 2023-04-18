package com.moon.moonfamily.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="Liky")
@Table(name="Liky")
public class LikyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int likeId;
    private int boardNumber;
    private String userId;
}
