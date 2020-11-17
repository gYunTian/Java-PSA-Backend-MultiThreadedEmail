package com.vsta.dto;

import java.util.Map;

public interface IUserManagementDTO {
    String getEmail();
    String getPassword();
    Map<String,String> getAll();
}
