package git.xlamid.springwebpetclinic.controller;

import git.xlamid.springwebpetclinic.dto.pet.GetPetDto;
import git.xlamid.springwebpetclinic.dto.pet.PostPetDto;
import git.xlamid.springwebpetclinic.dto.pet.PutPetDto;
import git.xlamid.springwebpetclinic.helper.CreatePetHelper;
import git.xlamid.springwebpetclinic.model.Pet;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PetControllerTest {

    private final CreatePetHelper createPetHelper;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    protected PetControllerTest(CreatePetHelper createPetHelper,
                                MockMvc mockMvc) {
        this.createPetHelper = createPetHelper;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    void shouldSuccessCreatePet() throws Exception {
        PostPetDto postPetDto = createPetHelper.createCorrectPostPetDto();
        String json = objectMapper.writeValueAsString(postPetDto);

        String resJson = mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse()
                .getContentAsString();
        GetPetDto resDto = objectMapper.readValue(resJson, GetPetDto.class);

        assertEquals(postPetDto.getName(), resDto.getName());
        assertEquals(postPetDto.getUserId(), resDto.getUserId());
        assertNotNull(resDto.getId());
    }

    @Test
    void shouldNotCreatePetWhenPetIncorrect() throws Exception {
        PostPetDto postPetDto = createPetHelper.createIncorrectPostPetDto();
        String json = objectMapper.writeValueAsString(postPetDto);

        mockMvc.perform(post("/api/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldSuccessUpdatePet() throws Exception {
        Pet pet = createPetHelper.createCorrectPet();
        pet = new Pet(pet.getId(), pet.getName(), pet.getUserId());

        PutPetDto putPetDto = createPetHelper.createCorrectPutPetDto();
        String json = objectMapper.writeValueAsString(putPetDto);

        String resJson = mockMvc.perform(put("/api/pets/{id}", pet.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();
        GetPetDto resDto = objectMapper.readValue(resJson, GetPetDto.class);

        assertNotEquals(pet.getName(), resDto.getName());
        assertEquals(pet.getId(), resDto.getId());
        assertEquals(pet.getUserId(), resDto.getUserId());
    }

    @Test
    void shouldNotUpdatePetWhenPetIncorrect() throws Exception {
        Pet pet = createPetHelper.createCorrectPet();
        PutPetDto putPetDto = createPetHelper.createIncorrectPutPetDto();
        String json = objectMapper.writeValueAsString(putPetDto);

        mockMvc.perform(put("/api/pets/{id}", pet.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldSuccessDeletePet() throws Exception {
        Pet pet = createPetHelper.createCorrectPet();
        mockMvc.perform(delete("/api/pets/{id}", pet.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldNotDeletePetWhenPetNotFound() throws Exception {
        mockMvc.perform(delete("/api/pets/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldSuccessGetPet() throws Exception {
        Pet pet = createPetHelper.createCorrectPet();

        String resJson = mockMvc.perform(get("/api/pets/{id}", pet.getId()))
                .andExpect(status().isOk())
                .andReturn().getResponse()
                .getContentAsString();
        GetPetDto resDto = objectMapper.readValue(resJson, GetPetDto.class);

        Assertions.assertThat(pet)
                .usingRecursiveComparison()
                .isEqualTo(resDto);
    }

    @Test
    void shouldNotGetPetWhenPetNotFound() throws Exception {
        mockMvc.perform(get("/api/pets/{id}", 1))
                .andExpect(status().isNotFound());
    }
}