package com.example.authenticationservice.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Complaint {
    Long id;
    UserInformation userInformation=new UserInformation();
}
