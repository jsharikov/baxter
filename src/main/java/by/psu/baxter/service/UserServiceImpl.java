package by.psu.baxter.service;

import by.psu.baxter.dao.UserDao;
import by.psu.baxter.entity.User;
import by.psu.baxter.entity.UserFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Artiom on 19.12.2015.
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> getAll(UserFilter filter) {
        return userDao.find(filter);
    }

    @Override
    public void save(User user) {
        if (user.getId() == null) {
            userDao.create(user);
        } else {
            userDao.update(user);
        }
    }

    @Override
    public User get(Long id) {
        return userDao.read(id);
    }

    @Override
    public void delete(Long id) {
        userDao.delete(id);
    }
}
