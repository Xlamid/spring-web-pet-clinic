package git.xlamid.springwebpetclinic.service;

import git.xlamid.springwebpetclinic.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    private final HashMap<Long, User> userMap;
    private Long counter;

    protected UserService() {
        this.userMap = new HashMap<>();
        this.counter = 0L;
    }

    public User createUser(User user) {
        user.setId(++counter);
        userMap.put(counter, user);
        return user;
    }

    public User updateUserById(Long id, User newUser) {
        User oldUser = findUserById(id);
        newUser.setId(oldUser.getId());
        newUser.setPets(oldUser.getPets());
        userMap.put(id, newUser);
        return newUser;
    }

    public void deleteUserById(Long id) {
        User user = findUserById(id);
        if (!user.getPets().isEmpty()) {
            throw new IllegalStateException("User with id=" + id + " has pets");
        }
        userMap.remove(id);
    }

    public User findUserById(Long id) {
        return Optional.ofNullable(userMap.get(id))
                .orElseThrow(() -> new NoSuchElementException("User with id=" + id + " not found"));
    }
}