package com.example.techtask.service.impl;

import com.example.techtask.model.Order;
import com.example.techtask.model.enumiration.UserStatus;
import com.example.techtask.service.OrderService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.techtask.model.enumiration.UserStatus.ACTIVE;


@Service
public class OrderServiceImpl implements OrderService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order findOrder() {
        String query = "SELECT o FROM Order o WHERE o.quantity > 1 ORDER BY o.createdAt DESC";
        return entityManager.createQuery(query, Order.class)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Override
    public List<Order> findOrders() {
        String jpql = "SELECT o FROM Order o WHERE o.userId IN (SELECT u.id FROM User u WHERE u.userStatus =: ACTIVE) ORDER BY o.createdAt asc ";
        return entityManager.createQuery(jpql, Order.class)
                .setParameter("activeStatus", ACTIVE)
                .getResultList();
    }
}
