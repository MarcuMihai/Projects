package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.entities.UserEntity;

public class UserBuilder {
    public UserBuilder() {
    }

    public static UserDTO toUserDTO(UserEntity user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getName(), user.getAddress(), user.getBirthDate(), user.getType(),user.getDevices());
    }

    public static UserEntity toEntity(UserDTO userDTO) {
        return new UserEntity(userDTO.getId(),
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getName(),
                userDTO.getAddress(),
                userDTO.getBirthDate(),
                userDTO.getType(),
                userDTO.getDevices());
    }
}
