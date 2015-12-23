package by.psu.baxter.web;

import by.psu.baxter.entity.User;
import by.psu.baxter.entity.UserFilter;
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
    public List<User> list(
            @RequestParam(value = "orderBy", defaultValue = "id", required = false) String orderBy,
            @RequestParam(value = "reverse", defaultValue = "false", required = false) boolean reverse,
            @RequestParam(value = "limit", required = true) int limit,
            @RequestParam(value = "offset", required = true) int offset
    ) {
        UserFilter filter = new UserFilter();
        filter.setOrderBy(orderBy);
        filter.setReverse(reverse);
        filter.setLimit(limit);
        filter.setOffset(offset);
        return userService.getAll(filter);
    }

    @RequestMapping(value = "/count", method = RequestMethod.GET)
    public int list(
            @RequestParam(value = "limit", required = true) int limit,
            @RequestParam(value = "offset", required = true) int offset
    ) {
        UserFilter filter = new UserFilter();
        filter.setOrderBy("login");
        filter.setReverse(false);
        filter.setLimit(limit);
        filter.setOffset(offset);
        return userService.getAll(filter).size();
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    public User read(@PathVariable Long id) {
        return userService.get(id);
    }


    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody User user) {
        userService.save(user);
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.POST)
    public void update(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        userService.save(user);
    }

    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}
