package git.xlamid.springwebpetclinic.controller;

import git.xlamid.springwebpetclinic.dto.user.GetUserDto;
import git.xlamid.springwebpetclinic.dto.user.PostUserDto;
import git.xlamid.springwebpetclinic.dto.user.PutUserDto;
import git.xlamid.springwebpetclinic.mapper.UserMapper;
import git.xlamid.springwebpetclinic.model.User;
import git.xlamid.springwebpetclinic.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@SuppressWarnings({"java:S5131", "squid:S5131"})
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<GetUserDto> createUser(@Valid @RequestBody PostUserDto userDto) {
        log.info("Create user: {}", userDto);
        User user = userService
                .createUser(userMapper.postUserDtoToUser(userDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userMapper.userToGetUserDto(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetUserDto> updateUserById(@PathVariable Long id,
                                                     @Valid @RequestBody PutUserDto userDto) {
        log.info("Update user: {} with id={}", userDto, id);
        User user = userService
                .updateUserById(id, userMapper.putUserDtoToUser(userDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userMapper.userToGetUserDto(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        log.info("Delete user with id={}", id);
        userService.deleteUserById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserDto> findUserById(@PathVariable Long id) {
        log.info("Find user with id={}", id);
        User user = userService.findUserById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userMapper.userToGetUserDto(user));
    }
}