package by.psu.baxter.service;

import by.psu.baxter.entity.User;

import java.util.List;

/**
 * Created by Artiom on 19.12.2015.
 */
public interface UserService {

    List<User> getAll();

    void save(User user);

    User get(Long id);

    void delete(Long id);
}
