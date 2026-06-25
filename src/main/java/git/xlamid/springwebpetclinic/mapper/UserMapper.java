package git.xlamid.springwebpetclinic.mapper;

import git.xlamid.springwebpetclinic.dto.user.GetUserDto;
import git.xlamid.springwebpetclinic.dto.user.PostUserDto;
import git.xlamid.springwebpetclinic.dto.user.PutUserDto;
import git.xlamid.springwebpetclinic.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

@Component
public class UserMapper {

    public GetUserDto userToGetUserDto(User model) {
        return new GetUserDto(
                model.getId(),
                HtmlUtils.htmlEscape(model.getName()),
                HtmlUtils.htmlEscape(model.getEmail()),
                model.getAge(),
                model.getPets()
        );
    }

    public User postUserDtoToUser(PostUserDto dto) {
        User model = new User();
        model.setName(dto.getName());
        model.setEmail(dto.getEmail());
        model.setAge(dto.getAge());
        return model;
    }

    public User putUserDtoToUser(PutUserDto dto) {
        User model = new User();
        model.setName(dto.getName());
        model.setEmail(dto.getEmail());
        model.setAge(dto.getAge());
        return model;
    }
}