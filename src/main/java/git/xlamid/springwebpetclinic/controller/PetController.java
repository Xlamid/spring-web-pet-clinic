package git.xlamid.springwebpetclinic.controller;

import git.xlamid.springwebpetclinic.dto.pet.GetPetDto;
import git.xlamid.springwebpetclinic.dto.pet.PostPetDto;
import git.xlamid.springwebpetclinic.dto.pet.PutPetDto;
import git.xlamid.springwebpetclinic.mapper.PetMapper;
import git.xlamid.springwebpetclinic.model.Pet;
import git.xlamid.springwebpetclinic.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;
    private final PetMapper petMapper;

    @Autowired
    protected PetController(PetService petService, PetMapper petMapper) {
        this.petService = petService;
        this.petMapper = petMapper;
    }

    @PostMapping
    public ResponseEntity<GetPetDto> createPet(@Valid @RequestBody PostPetDto petDto) {
        Pet pet = petService.createPet(petMapper.postPetDtoToPet(petDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(petMapper.petToGetPetDto(pet));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetPetDto> updatePetById(@PathVariable Long id,
                                                   @Valid @RequestBody PutPetDto petDto) {
        Pet pet = petService.updatePetById(id, petMapper.putPetDtoToPet(petDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(petMapper.petToGetPetDto(pet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetById(@PathVariable Long id) {
        petService.deletePetById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPetDto> findPetById(@PathVariable Long id) {
        Pet pet = petService.findGetPetDtoById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(petMapper.petToGetPetDto(pet));
    }
}