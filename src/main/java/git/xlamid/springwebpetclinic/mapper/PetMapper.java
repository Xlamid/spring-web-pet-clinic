package git.xlamid.springwebpetclinic.mapper;

import git.xlamid.springwebpetclinic.dto.pet.GetPetDto;
import git.xlamid.springwebpetclinic.dto.pet.CreatePetDto;
import git.xlamid.springwebpetclinic.dto.pet.UpdatePetDto;
import git.xlamid.springwebpetclinic.model.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {

    public GetPetDto petToGetPetDto(Pet model) {
        return new GetPetDto(
                model.getId(),
                model.getName(),
                model.getUserId()
        );
    }

    public Pet postPetDtoToPet(CreatePetDto dto) {
        Pet model = new Pet();
        model.setName(dto.getName());
        model.setUserId(dto.getUserId());
        return model;
    }

    public Pet putPetDtoToPet(UpdatePetDto dto) {
        Pet model = new Pet();
        model.setName(dto.getName());
        return model;
    }
}