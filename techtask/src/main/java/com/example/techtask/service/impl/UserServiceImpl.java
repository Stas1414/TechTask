package com.example.techtask.service.impl;

import com.example.techtask.model.User;
import com.example.techtask.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findUser() {
        String sql = "SELECT u.* FROM users u " +
                "JOIN (SELECT user_id, SUM(price * quantity) AS total " +
                "      FROM orders " +
                "      WHERE order_status = 'DELIVERED' AND EXTRACT(YEAR FROM created_at) = 2003 " +
                "      GROUP BY user_id " +
                "      ORDER BY total DESC " +
                "      LIMIT 1) o ON u.id = o.user_id";

        Query query = entityManager.createNativeQuery(sql, User.class);
        List<User> result = query.getResultList();

        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    @Override
    public List<User> findUsers() {
        String sql = "SELECT DISTINCT u.* FROM users u " +
                "JOIN orders o ON u.id = o.user_id " +
                "WHERE o.order_status = 'PAID' AND EXTRACT(YEAR FROM o.created_at) = 2010";

        Query query = entityManager.createNativeQuery(sql, User.class);
        return query.getResultList();
    }
}
