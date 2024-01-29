package com.wilsonpedro.parking.dtos.records;

import com.wilsonpedro.parking.enums.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {

}
