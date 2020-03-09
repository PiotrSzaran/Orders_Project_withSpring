package pl.szaran.shop_spring.service;

import pl.szaran.shop_spring.model.User;
import pl.szaran.shop_spring.model.dto.UserDTO;

import java.util.HashSet;

public class ModelMapper {

    //User
    static User fromUserDTOToUser(UserDTO userDTO) {
        return userDTO == null ? null : User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .enabled(userDTO.getEnabled())
                .role(userDTO.getRole())
                .build();
    }

    //UserDTO
    static UserDTO fromUserToUserDTO(User user) {
        return user == null ? null : UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .enabled(user.getEnabled())
                .role(user.getRole())
                .build();
    }
}
