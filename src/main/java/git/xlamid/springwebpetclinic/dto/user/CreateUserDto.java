package git.xlamid.springwebpetclinic.dto.user;

import jakarta.validation.constraints.*;

public class CreateUserDto {

    @NotBlank
    @Size(min = 1, max = 100)
    private String name;
    @Email
    private String email;
    @NotNull
    private Integer age;

    public CreateUserDto() {
    }

    public CreateUserDto(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    @Override
    public String toString() {
        return "PostUserDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}