package com.example.auth.config.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthInfoInstance {

    private  Long userId;
    private  Long parentId;
    private  Long childId;
}
