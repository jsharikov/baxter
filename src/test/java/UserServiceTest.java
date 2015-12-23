import by.psu.baxter.entity.User;
import by.psu.baxter.entity.UserFilter;
import by.psu.baxter.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Artiom on 20.12.2015.
 */
@ContextConfiguration(locations = {
        "classpath:spring/spring-core-config.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class UserServiceTest {

        @Autowired
        private UserService userService;

        @Test
        public void testCreate() {
                User petya = createUserType1("Петя");
                userService.save(petya);
                Assert.assertEquals(petya, userService.get(petya.getId()));
        }

        @Test
        public void testUpdate() {
                User petya = createUserType1("Петя");
                userService.save(petya);

                //Действие
                User petyaUpdate = createUserType2("ипетя");
                userService.save(petyaUpdate);

                //Проверка
                Assert.assertTrue(petyaUpdate.equals(userService.get(petya.getId())));
        }

        @Test
        public void testGetAll() {
                User petya = createUserType1("Петя");
                userService.save(petya);
                User vasya = createUserType2("Вася");
                userService.save(vasya);

                Set<User> expectedSetOfUsers = new HashSet<>(2);
                expectedSetOfUsers.add(petya);
                expectedSetOfUsers.add(vasya);

                //Действие
                UserFilter filter = new UserFilter();
                filter.setLimit(20);
                filter.setOffset(0);
                filter.setOrderBy("login");
                filter.setReverse(true);
                List<User> users = userService.getAll(filter);

                //Проверка
                Assert.assertTrue(users.containsAll(expectedSetOfUsers));
        }


        @Test
        public void testDelete() {
                User petya = createUserType1("Петя");
                userService.save(petya);

                //Действие
                userService.delete(petya.getId());

                //Проверка
                Assert.assertNull(userService.get(petya.getId()));
        }

        private User createUserType1(String userName) {
                User user = new User();
                user.setLogin(userName);
                user.setPassword("testPsw1");
                return user;
        }

        private User createUserType2(String userName) {
                User user = new User();
                user.setLogin(userName);
                user.setPassword("testPsw2");
                return user;
        }
}
