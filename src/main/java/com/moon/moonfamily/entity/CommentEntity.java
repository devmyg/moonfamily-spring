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
@Entity(name="Comment")
@Table(name="Comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;
    private int boardNumber;
    private String userId;
    private String commentWriteDate;
    private String commentContent;
}
