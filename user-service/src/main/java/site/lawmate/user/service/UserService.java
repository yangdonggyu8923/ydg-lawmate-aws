package site.lawmate.user.service;

import site.lawmate.user.component.Messenger;
import site.lawmate.user.domain.model.User;
import site.lawmate.user.domain.dto.UserDto;

public interface UserService extends CommandService<UserDto>, QueryService<UserDto> {

    default User dtoToEntity(UserDto dto) {
        return User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .phone(dto.getPhone())
                .age(dto.getAge())
                .gender(dto.getGender())
                .point(dto.getPoint())
                .build();
    }

    default UserDto entityToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .age(user.getAge())
                .gender(user.getGender())
                .point(user.getPoint())
                .build();
    }

    Messenger login(UserDto param);

    Boolean logout(String accessToken);

    Messenger update(UserDto user);

    User autoRegister();

    Boolean existsByEmail(String email);

}
