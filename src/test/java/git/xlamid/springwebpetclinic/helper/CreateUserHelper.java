package git.xlamid.springwebpetclinic.helper;

import git.xlamid.springwebpetclinic.dto.user.PostUserDto;
import git.xlamid.springwebpetclinic.dto.user.PutUserDto;
import org.springframework.stereotype.Component;

@Component
public class CreateUserHelper {

    public PostUserDto createCorrectPostUserDto() {
        return new PostUserDto(
                "name1",
                "name1@mail.com",
                20
        );
    }

    public PostUserDto createIncorrectPostUserDto() {
        return new PostUserDto(
                "n",
                "name1mail.com",
                20
        );
    }

    public PutUserDto createCorrectPutUserDto() {
        return new PutUserDto(
                "name2",
                "name2@mail.com",
                30
        );
    }

    public PutUserDto createIncorrectPutUserDto() {
        return new PutUserDto(
                "n",
                "name1mail.com",
                20
        );
    }
}