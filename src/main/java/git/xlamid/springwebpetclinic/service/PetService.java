package git.xlamid.springwebpetclinic.service;

import git.xlamid.springwebpetclinic.model.Pet;
import git.xlamid.springwebpetclinic.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PetService {

    private final UserService userService;

    private final Map<Long, Long> petUserMap;
    private Long counter;

    @Autowired
    protected PetService(UserService userService) {
        this.userService = userService;
        this.petUserMap = new HashMap<>();
        this.counter = 0L;
    }

    public Pet createPet(Pet pet) {
        pet.setId(++counter);
        Long userId = pet.getUserId();
        petUserMap.put(counter, userId);

        User user = userService.findUserById(userId);
        user.getPets().add(pet);
        userService.updateUserById(userId, user);

        return pet;
    }

    public Pet updatePetById(Long id, Pet newPet) {
        Pet oldPet = findPetById(id);
        Long userId = oldPet.getUserId();
        User user = userService.findUserById(userId);

        for (Pet pet : user.getPets()) {
            if (Objects.equals(pet.getId(), id)) {
                pet.setName(newPet.getName());
                newPet = pet;
            }
        }
        userService.updateUserById(userId, user);

        return newPet;
    }

    public void deletePetById(Long id) {
        User user = findUserByPetId(id);
        boolean isRemoved = user.getPets()
                .removeIf(remPet -> remPet.getId().equals(id));
        if (isRemoved) {
            userService.updateUserById(user.getId(), user);
            petUserMap.remove(id);
        }
    }

    public Pet findGetPetDtoById(Long id) {
        User user = findUserByPetId(id);
        return Optional.of(user.getPets().stream()
                        .filter(pet -> pet.getId().equals(id))
                        .findFirst())
                .get().orElseThrow(() -> new NoSuchElementException("Pet with id=" + id + " not found"));
    }

    public Pet findPetById(Long id) {
        User user = findUserByPetId(id);
        return Optional.of(user.getPets().stream()
                .filter(pet -> pet.getId().equals(id))
                .findFirst())
                .get().orElseThrow(() -> new NoSuchElementException("Pet with id=" + id + " not found"));
    }

    private User findUserByPetId(Long id) {
        Long userId = Optional.ofNullable(petUserMap.get(id))
                .orElseThrow(() -> new NoSuchElementException("Pet with id=" + id + " not found"));
        return userService.findUserById(userId);
    }
}