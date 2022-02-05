package org.by.bharadwaj.fundoo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {
    private String email;
    private String password;
}
