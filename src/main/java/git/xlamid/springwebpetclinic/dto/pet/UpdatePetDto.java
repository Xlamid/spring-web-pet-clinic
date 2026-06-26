package git.xlamid.springwebpetclinic.dto.pet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdatePetDto {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    public UpdatePetDto() {
    }

    public UpdatePetDto(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PutPetDto{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}