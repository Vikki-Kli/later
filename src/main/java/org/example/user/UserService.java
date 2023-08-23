package org.example.user;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository repository){
        userRepository = repository;
    }


    public Collection<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserMapper::pojoToDto).toList();
    }

    public UserDto getUser(long id) {
        return UserMapper.pojoToDto(userRepository.getById(id));
    }

    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.dtoToPojo(userDto);
        user.setRegistrationDate(Instant.now());
        user.setState(UserState.ACTIVE);
        return UserMapper.pojoToDto(userRepository.save(user));
    }

    public UserDto editUser(UserDto userDto, long id) {
        User user = UserMapper.dtoToPojo(userDto);
        User oldUser = userRepository.getById(id);
        user.setId(id);
        user.setState(oldUser.getState());
        user.setRegistrationDate(oldUser.getRegistrationDate());
        return UserMapper.pojoToDto(userRepository.save(user));
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
