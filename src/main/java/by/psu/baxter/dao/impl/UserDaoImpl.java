package by.psu.baxter.dao.impl;

import by.psu.baxter.dao.UserDao;
import by.psu.baxter.entity.User;
import by.psu.baxter.entity.UserFilter;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Artiom on 19.12.2015.
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<User> find(UserFilter filter) {
        return sessionFactory.getCurrentSession().createCriteria(User.class)
                .setFirstResult(filter.getOffset())
                .setMaxResults(filter.getLimit())
                .addOrder(filter.isReverse() ? Order.desc(filter.getOrderBy()) : Order.asc(filter.getOrderBy())).list();
    }

    @Override
    public void create(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public User read(Long id) {
        return (User) sessionFactory.getCurrentSession().get(User.class, id);
    }

    @Override
    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    @Override
    public void delete(Long id) {
        User user = read(id);
        if (user != null) {
            sessionFactory.getCurrentSession().delete(user);
        }
    }
}
