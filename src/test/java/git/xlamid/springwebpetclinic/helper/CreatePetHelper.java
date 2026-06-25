package git.xlamid.springwebpetclinic.helper;

import git.xlamid.springwebpetclinic.dto.pet.PostPetDto;
import git.xlamid.springwebpetclinic.dto.pet.PutPetDto;
import git.xlamid.springwebpetclinic.dto.user.PostUserDto;
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

    public PostPetDto createCorrectPostPetDto(Long userId) {
        return new PostPetDto(
                "name1",
                userId
        );
    }

    public PostPetDto createCorrectPostPetDto() {
        PostUserDto postUserDto = createUserHelper.createCorrectPostUserDto();
        User user = userService.createUser(userMapper.postUserDtoToUser(postUserDto));
        return new PostPetDto(
                "name1",
                user.getId()
        );
    }

    public PostPetDto createIncorrectPostPetDto() {
        PostUserDto postUserDto = createUserHelper.createCorrectPostUserDto();
        userService.createUser(userMapper.postUserDtoToUser(postUserDto));
        return new PostPetDto(
                "n",
                20L
        );
    }

    public Pet createCorrectPet() {
        PostPetDto postPetDto = createCorrectPostPetDto();
        return petService.createPet(petMapper.postPetDtoToPet(postPetDto));

    }

    public PutPetDto createCorrectPutPetDto() {
        return new PutPetDto(
                "name2"
        );
    }

    public PutPetDto createIncorrectPutPetDto() {
        return new PutPetDto(
                "n"
        );
    }
}