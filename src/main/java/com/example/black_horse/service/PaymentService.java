package com.example.black_horse.service;

import com.example.black_horse.infrastructure.mapper.CompensateMapper;
import com.example.black_horse.infrastructure.mapper.OrderMapper;
import com.example.black_horse.infrastructure.repository.CompensateRepository;
import com.example.black_horse.infrastructure.repository.OrderRepository;
import com.example.black_horse.infrastructure.repository.entity.CompensateEntity;
import com.example.black_horse.infrastructure.repository.entity.OrderEntity;
import com.example.black_horse.infrastructure.stream.MqProducerClient;
import com.example.black_horse.infrastructure.stream.dto.CompensateMessage;
import com.example.black_horse.infrastructure.web.PayMentClient;
import com.example.black_horse.infrastructure.web.apimodel.PaymentRequest;
import com.example.black_horse.infrastructure.web.apimodel.PaymentResponse;
import com.example.black_horse.model.Compensate;
import com.example.black_horse.model.Order;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
public class PaymentService {
    private OrderRepository orderRepository;
    private CompensateRepository compensateRepository;
    private OrderMapper orderMapper;
    private CompensateMapper compensateMapper;
    private PayMentClient payMentClient;
    private MqProducerClient mqProducerClient;

    @Autowired
    public PaymentService(OrderRepository orderRepository, OrderMapper orderMapper, PayMentClient payMentClient, CompensateMapper compensateMapper, MqProducerClient mqProducerClient, CompensateRepository compensateRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.payMentClient = payMentClient;
        this.compensateMapper = compensateMapper;
        this.mqProducerClient = mqProducerClient;
        this.compensateRepository = compensateRepository;
    }

    public boolean confirmPayment(Order order) {
        try {
            Long orderNumber = orderRepository.countByTradeNo(order.getTradeNo());
            if (orderNumber == 0) {
                return false;
            }
            OrderEntity entity = orderMapper.model2Entity(order);
            OrderEntity savedEntity = orderRepository.save(entity);
            return Objects.nonNull(savedEntity);
        } catch (JpaSystemException e) {
            return false;
        }
    }

    public Order getPayment(String contractId) throws IOException {
        OrderEntity latestOrder = orderRepository.findLatestOrderByContractId(contractId);
        String paymentState = payMentClient.getPaymentState(latestOrder.getTradeNo());
        OrderEntity gatewayOrderEntity = OrderEntity.builder()
                .tradeState(paymentState)
                .tradeNo(latestOrder.getTradeNo())
                .contractId(contractId).build();
        OrderEntity savedEntity = orderRepository.save(gatewayOrderEntity);
        if (paymentState.equals("SUCCESS")) {
            Order order = orderMapper.entity2Model(savedEntity);
            return order;
        } else {
            PaymentRequest paymentRequest = PaymentRequest.builder().tradeNo(latestOrder.getTradeNo()).build();
            PaymentResponse response = payMentClient.createPayment(paymentRequest);
            OrderEntity newCreatedOrderEntity = OrderEntity.builder()
                    .tradeState(response.getTradeState())
                    .tradeNo(response.getTradeNo())
                    .contractId(contractId)
                    .codeUrl(response.getCodeUrl())
                    .build();
            OrderEntity savedNewCreatedOrderEntity = orderRepository.save(newCreatedOrderEntity);
            Order newCreatedOrder = orderMapper.entity2Model(savedNewCreatedOrderEntity);
            return newCreatedOrder;
        }
    }

    public boolean createCompensate(Compensate compensate) {
        if (compensateRepository.countByContractId(compensate.getContractId()) > 0) {
            return false;
        }
        CompensateEntity entity = compensateMapper.model2Entity(compensate);
        CompensateEntity savedEntity = compensateRepository.save(entity);
        Compensate savedCompensate = compensateMapper.entity2Model(savedEntity);
        CompensateMessage compensateMessage = compensateMapper.model2Message(savedCompensate);
        boolean sendResult = mqProducerClient.sendCompensate(compensateMessage);
        if (!sendResult) {
            CompensateEntity failedCompensate = CompensateEntity.builder()
                    .compensateState("FAILED")
                    .contractId(compensate.getContractId())
                    .amount(compensate.getAmount())
                    .build();
            compensateRepository.save(failedCompensate);
        }
        return sendResult;
    }
}
