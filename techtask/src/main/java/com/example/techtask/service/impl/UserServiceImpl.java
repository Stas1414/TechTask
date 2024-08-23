package com.example.techtask.service.impl;

import com.example.techtask.model.User;
import com.example.techtask.model.enumiration.OrderStatus;
import com.example.techtask.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findUser() {
        String jpql = "SELECT u FROM User u JOIN u.orders o " +
                "WHERE FUNCTION('YEAR', o.createdAt) = 2003 " +
                "GROUP BY u.id " +
                "ORDER BY SUM(o.price * o.quantity) DESC";

        return entityManager.createQuery(jpql, User.class)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Override
    public List<User> findUsers() {
        String jpql = "SELECT DISTINCT u FROM User u JOIN u.orders o " +
                "WHERE FUNCTION('YEAR', o.createdAt) = 2010 " +
                "AND o.orderStatus = :paidStatus";

        return entityManager.createQuery(jpql, User.class)
                .setParameter("paidStatus", OrderStatus.PAID)
                .getResultList();
    }
}
