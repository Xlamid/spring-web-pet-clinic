package git.xlamid.springwebpetclinic.dto.user;

import git.xlamid.springwebpetclinic.model.Pet;

import java.util.ArrayList;
import java.util.List;

public class GetUserDto {

    private Long id;
    private String name;
    private String email;
    private Integer age;
    private List<Pet> pets;

    public GetUserDto() {
        this.pets = new ArrayList<>();
    }

    public GetUserDto(Long id, String name, String email, Integer age, List<Pet> pets) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.pets = pets;
    }

    @Override
    public String toString() {
        return "GetUserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", pets=" + pets +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}