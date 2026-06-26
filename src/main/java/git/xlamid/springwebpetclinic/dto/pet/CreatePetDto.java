package git.xlamid.springwebpetclinic.dto.pet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreatePetDto {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;
    @NotNull
    private Long userId;

    public CreatePetDto() {
    }

    public CreatePetDto(String name, Long userId) {
        this.name = name;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "PostPetDto{" +
                "name='" + name + '\'' +
                ", userId=" + userId +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}