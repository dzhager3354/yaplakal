package ru.dzhager3354.objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostAnswer {
    private String postId;
    private String id;
    private String author;
    private String answerId;
    private String content;
}
