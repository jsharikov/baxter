package by.psu.baxter.web;

import by.psu.baxter.entity.User;
import by.psu.baxter.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Artiom on 19.12.2015.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(method = RequestMethod.GET)
    public List<User> list() {
        return userService.getAll();
    }

   /* @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    public User read(@PathVariable Long id) {
        return userService.get(id);
    }


    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody User user) {
        userService.save(user);
    }

    *//*@RequestMapping(value = "/{userId:\\d+}", method = RequestMethod.POST, produces = "application/json")
    public update(@PathVariable Long userId, @RequestBody User user) {
        userService.save(user);
    }*//*

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }*/
}
