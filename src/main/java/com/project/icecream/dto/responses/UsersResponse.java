package com.project.icecream.dto.responses;

import com.project.icecream.models.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class UsersResponse {
    private Users users;
    private int orderQuantity;
}
