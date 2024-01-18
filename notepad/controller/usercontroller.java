package OOPhw5.notepad.controller;

import notepad.model.User;
import notepad.model.repository.GBRepository;

import java.util.List;
import java.util.Objects;

public class usercontroller {
    private final gbrepository repository;

    public usercontroller(gbrepository repository) {
        this.repository = repository;
    }

    public void saveUser(user user) {
        repository.create(user);
    }

    public user readUser(Long userId) throws Exception {
        List<user> users = repository.findAll();
        for (user user : users) {
            if (Objects.equals(user.getId(), userId)) {
                return user;
            }
        }

        throw new RuntimeException("User not found");
    }

    public void updateUser(String userId, user update) {
        update.setId(Long.parseLong(userId));
        repository.update(Long.parseLong(userId), update);

    }
    public List <user> readAll (){
        return repository.findAll();
    }

    public void deleteUser(Long userId) {
        repository.delete(userId);
    }

}