package com.example.black_horse.infrastructure.repository;

import com.example.black_horse.infrastructure.repository.entity.OrderEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, String> {

    Long countByTradeNo(String tradeNo);

    @Query(value="select * from test_order where contract_id = ?1 order by created_time desc limit 1" ,nativeQuery=true)
    public OrderEntity findLatestOrderByContractId(String name);
}
