package com.example.tacocloud.repository.jdbc;

import com.example.tacocloud.model.Order;

public interface OrderRepository {

    Order save(Order order);

}
