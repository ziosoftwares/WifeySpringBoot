package com.zio.user.data;

import lombok.Data;

@Data
public class LoginDTO {
    public String email;
    public String password;
    public String userName;
    public String identifier;
}
