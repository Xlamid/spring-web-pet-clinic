package git.xlamid.springwebpetclinic.helper;

import git.xlamid.springwebpetclinic.dto.pet.CreatePetDto;
import git.xlamid.springwebpetclinic.dto.pet.UpdatePetDto;
import git.xlamid.springwebpetclinic.dto.user.CreateUserDto;
import git.xlamid.springwebpetclinic.mapper.PetMapper;
import git.xlamid.springwebpetclinic.mapper.UserMapper;
import git.xlamid.springwebpetclinic.model.Pet;
import git.xlamid.springwebpetclinic.model.User;
import git.xlamid.springwebpetclinic.service.PetService;
import git.xlamid.springwebpetclinic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreatePetHelper {

    private final CreateUserHelper createUserHelper;
    private final UserService userService;
    private final UserMapper userMapper;
    private final PetService petService;
    private final PetMapper petMapper;

    @Autowired
    public CreatePetHelper(CreateUserHelper createUserHelper, UserService userService, UserMapper userMapper, PetService petService, PetMapper petMapper) {
        this.createUserHelper = createUserHelper;
        this.userService = userService;
        this.userMapper = userMapper;
        this.petService = petService;
        this.petMapper = petMapper;
    }

    public CreatePetDto createCorrectPostPetDto(Long userId) {
        return new CreatePetDto(
                "name1",
                userId
        );
    }

    public CreatePetDto createCorrectPostPetDto() {
        CreateUserDto createUserDto = createUserHelper.createCorrectPostUserDto();
        User user = userService.createUser(userMapper.postUserDtoToUser(createUserDto));
        return new CreatePetDto(
                "name1",
                user.getId()
        );
    }

    public CreatePetDto createIncorrectPostPetDto() {
        CreateUserDto createUserDto = createUserHelper.createCorrectPostUserDto();
        userService.createUser(userMapper.postUserDtoToUser(createUserDto));
        return new CreatePetDto(
                "n",
                20L
        );
    }

    public Pet createCorrectPet() {
        CreatePetDto createPetDto = createCorrectPostPetDto();
        return petService.createPet(petMapper.postPetDtoToPet(createPetDto));

    }

    public UpdatePetDto createCorrectPutPetDto() {
        return new UpdatePetDto(
                "name2"
        );
    }

    public UpdatePetDto createIncorrectPutPetDto() {
        return new UpdatePetDto(
                "n"
        );
    }
}