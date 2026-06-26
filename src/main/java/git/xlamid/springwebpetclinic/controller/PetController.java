package git.xlamid.springwebpetclinic.controller;

import git.xlamid.springwebpetclinic.dto.pet.GetPetDto;
import git.xlamid.springwebpetclinic.dto.pet.CreatePetDto;
import git.xlamid.springwebpetclinic.dto.pet.UpdatePetDto;
import git.xlamid.springwebpetclinic.mapper.PetMapper;
import git.xlamid.springwebpetclinic.model.Pet;
import git.xlamid.springwebpetclinic.service.PetService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private static final Logger log = LoggerFactory.getLogger(PetController.class);

    private final PetService petService;
    private final PetMapper petMapper;

    @Autowired
    protected PetController(PetService petService, PetMapper petMapper) {
        this.petService = petService;
        this.petMapper = petMapper;
    }

    @PostMapping
    public ResponseEntity<GetPetDto> createPet(@Valid @RequestBody CreatePetDto petDto) {
        log.info("Create pet: {}", petDto);
        Pet pet = petService.createPet(petMapper.postPetDtoToPet(petDto));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(petMapper.petToGetPetDto(pet));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetPetDto> updatePetById(@PathVariable Long id,
                                                   @Valid @RequestBody UpdatePetDto petDto) {
        log.info("Update pet: {} with id={}", petDto, id);
        Pet pet = petService.updatePetById(id, petMapper.putPetDtoToPet(petDto));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(petMapper.petToGetPetDto(pet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePetById(@PathVariable Long id) {
        log.info("Delete pet with id={}", id);
        petService.deletePetById(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPetDto> findPetById(@PathVariable Long id) {
        log.info("Find pet with id={}", id);
        Pet pet = petService.findGetPetDtoById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(petMapper.petToGetPetDto(pet));
    }
}