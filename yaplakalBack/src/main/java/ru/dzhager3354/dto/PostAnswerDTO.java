package ru.dzhager3354.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostAnswerDTO {
    private String postId;
    private String answerId;
    private String content;
}
