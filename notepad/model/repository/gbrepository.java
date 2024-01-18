package OOPhw5.notepad.model.repository;

import OOPhw5.notepad.model.user;

import java.util.List;
import java.util.Optional;

public interface gbrepository {
    List<user> findAll();
    user create(user user);
    Optional<user> findById(Long id);
    Optional<user> update(Long userId, user update);
    void delete(Long userId);
}