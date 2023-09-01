package org.example.user;

import lombok.extern.slf4j.Slf4j;
import org.example.exception.NoSuchUserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Collection;

@Service
@Transactional
@Slf4j
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository repository){
        userRepository = repository;
    }


    public Collection<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserMapper::pojoToDto).toList();
    }

    public UserDto getUser(long id) {
        checkUserById(id);
        return UserMapper.pojoToDto(userRepository.findById(id).get());
    }

    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.dtoToPojo(userDto);
        user.setRegistrationDate(Instant.now());
        user.setState(UserState.ACTIVE);
        User savedUser = userRepository.save(user);
        log.info("Создан пользователь " + savedUser);
        return UserMapper.pojoToDto(savedUser);
    }

    public UserDto editUser(UserDto userDto, long id) {
        checkUserById(id);
        User user = UserMapper.dtoToPojo(userDto);
        User oldUser = userRepository.findById(id).get();
        user.setId(id);
        user.setState(oldUser.getState());
        user.setRegistrationDate(oldUser.getRegistrationDate());
        User savedUser = userRepository.save(user);
        log.info("Изменен пользователь " + savedUser);
        return UserMapper.pojoToDto(savedUser);
    }

    public void deleteUser(long id) {
        checkUserById(id);
        userRepository.deleteById(id);
        log.info("Удален пользователь " + id);
    }

    public void checkUserById(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new NoSuchUserException("Не найден пользователь " + userId);
        }
    }
}
