package by.psu.baxter.dao;

import by.psu.baxter.entity.User;
import by.psu.baxter.entity.UserFilter;

import java.util.List;

/**
 * Created by Artiom on 19.12.2015.
 */
public interface UserDao {

    List<User> find(UserFilter filter);

    void create(User user);

    User read(Long id);

    void update(User user);

    void delete(Long id);
}
