package ru.dzhager3354.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewAnswer {
    private User user;
    private PostAnswerDTO dto;
}
