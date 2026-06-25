package git.xlamid.springwebpetclinic.controller;

import git.xlamid.springwebpetclinic.dto.pet.PostPetDto;
import git.xlamid.springwebpetclinic.dto.user.GetUserDto;
import git.xlamid.springwebpetclinic.dto.user.PostUserDto;
import git.xlamid.springwebpetclinic.dto.user.PutUserDto;
import git.xlamid.springwebpetclinic.helper.CreatePetHelper;
import git.xlamid.springwebpetclinic.helper.CreateUserHelper;
import git.xlamid.springwebpetclinic.mapper.PetMapper;
import git.xlamid.springwebpetclinic.mapper.UserMapper;
import git.xlamid.springwebpetclinic.model.User;
import git.xlamid.springwebpetclinic.service.PetService;
import git.xlamid.springwebpetclinic.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    private final UserService userService;
    private final PetService petService;
    private final CreateUserHelper createUserHelper;
    private final CreatePetHelper createPetHelper;
    private final MockMvc mockMvc;
    private final UserMapper userMapper;
    private final PetMapper petMapper;
    private final ObjectMapper objectMapper;

    @Autowired
    protected UserControllerTest(UserService userService,
                       PetService petService,
                       CreateUserHelper createUserHelper,
                       CreatePetHelper createPetHelper,
                       MockMvc mockMvc,
                       UserMapper userMapper,
                       PetMapper petMapper) {
        this.userService = userService;
        this.petService = petService;
        this.createUserHelper = createUserHelper;
        this.createPetHelper = createPetHelper;
        this.mockMvc = mockMvc;
        this.userMapper = userMapper;
        this.petMapper = petMapper;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void shouldSuccessCreateUser() throws Exception {
        PostUserDto postDto = createUserHelper.createCorrectPostUserDto();
        String json = objectMapper.writeValueAsString(postDto);

        String resJson = mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse()
                .getContentAsString();
        GetUserDto resDto = objectMapper.readValue(resJson, GetUserDto.class);

        assertEquals(postDto.getName(), resDto.getName());
        assertEquals(postDto.getEmail(), resDto.getEmail());
        assertEquals(postDto.getAge(), resDto.getAge());
        assertNotNull(resDto.getId());
    }

    @Test
    void shouldNotCreateUserWhenUserIncorrect() throws Exception {
        PostUserDto postDto = createUserHelper.createIncorrectPostUserDto();
        String json = objectMapper.writeValueAsString(postDto);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldSuccessUpdateUser() throws Exception {
        PostUserDto postDto = createUserHelper.createCorrectPostUserDto();
        User user = userService.createUser(userMapper.postUserDtoToUser(postDto));
        PutUserDto putDto = createUserHelper.createCorrectPutUserDto();
        String json = objectMapper.writeValueAsString(putDto);

        String resJson = mockMvc.perform(put("/api/users/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();
        GetUserDto resDto = objectMapper.readValue(resJson, GetUserDto.class);

        assertNotEquals(user.getName(), resDto.getName());
        assertNotEquals(user.getEmail(), resDto.getEmail());
        assertNotEquals(user.getAge(), resDto.getAge());
        assertEquals(user.getId(), resDto.getId());
    }

    @Test
    void shouldNotUpdateUserWhenUserIncorrect() throws Exception {
        PostUserDto postDto = createUserHelper.createCorrectPostUserDto();
        User user = userService.createUser(userMapper.postUserDtoToUser(postDto));
        PutUserDto putDto = createUserHelper.createIncorrectPutUserDto();
        String json = objectMapper.writeValueAsString(putDto);

        mockMvc.perform(put("/api/users/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldSuccessDeleteUser() throws Exception {
        PostUserDto postDto = createUserHelper.createCorrectPostUserDto();
        User user = userService.createUser(userMapper.postUserDtoToUser(postDto));

        mockMvc.perform(delete("/api/users/{id}", user.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotDeleteUserWhenHasPets() throws Exception {
        PostUserDto postDto = createUserHelper.createCorrectPostUserDto();
        User user = userService.createUser(userMapper.postUserDtoToUser(postDto));
        PostPetDto postPetDto = createPetHelper.createCorrectPostPetDto(user.getId());
        petService.createPet(petMapper.postPetDtoToPet(postPetDto));

        mockMvc.perform(delete("/api/users/{id}", user.getId()))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldSuccessGetUser() throws Exception {
        PostUserDto postDto = createUserHelper.createCorrectPostUserDto();
        User user = userService.createUser(userMapper.postUserDtoToUser(postDto));

        String resJson = mockMvc.perform(get("/api/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();
        GetUserDto resDto = objectMapper.readValue(resJson, GetUserDto.class);

        Assertions.assertThat(user)
                .usingRecursiveComparison()
                .isEqualTo(resDto);
    }

    @Test
    void shouldNotGetUserWhenUserNotFound() throws Exception {
        mockMvc.perform(get("/api/users/{id}", 1))
                .andExpect(status().isNotFound())
                .andReturn().getResponse()
                .getContentAsString();
    }
}