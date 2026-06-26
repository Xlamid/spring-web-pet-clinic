package git.xlamid.springwebpetclinic.helper;

import git.xlamid.springwebpetclinic.dto.user.CreateUserDto;
import git.xlamid.springwebpetclinic.dto.user.UpdateUserDto;
import org.springframework.stereotype.Component;

@Component
public class CreateUserHelper {

    public CreateUserDto createCorrectPostUserDto() {
        return new CreateUserDto(
                "name1",
                "name1@mail.com",
                20
        );
    }

    public CreateUserDto createIncorrectPostUserDto() {
        return new CreateUserDto(
                "n",
                "name1mail.com",
                20
        );
    }

    public UpdateUserDto createCorrectPutUserDto() {
        return new UpdateUserDto(
                "name2",
                "name2@mail.com",
                30
        );
    }

    public UpdateUserDto createIncorrectPutUserDto() {
        return new UpdateUserDto(
                "n",
                "name1mail.com",
                20
        );
    }
}