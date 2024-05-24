package com.project.icecream.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class MessageRequest {
    private int userId;
    private String userName;
    private String email;
    private String subject;
    private String message;
}
