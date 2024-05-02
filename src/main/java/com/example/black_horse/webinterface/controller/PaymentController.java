package com.example.black_horse.webinterface.controller;

import com.example.black_horse.infrastructure.mapper.CompensateMapper;
import com.example.black_horse.infrastructure.mapper.OrderMapper;
import com.example.black_horse.model.Compensate;
import com.example.black_horse.model.Order;
import com.example.black_horse.service.PaymentService;
import com.example.black_horse.webinterface.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class PaymentController {
    private PaymentService paymentService;
    private OrderMapper orderMapper;
    private CompensateMapper compensateMapper;

    @Autowired
    public PaymentController(PaymentService paymentService, OrderMapper orderMapper, CompensateMapper compensateMapper) {
        this.orderMapper = orderMapper;
        this.paymentService = paymentService;
        this.compensateMapper = compensateMapper;
    }

    @GetMapping("/order-food-contracts/{id}/payment")
    public Response<Order> getPaymentConfirmation(@PathVariable String id) throws IOException {
        Order order = paymentService.getPayment(id);
        return Response.success(order, ResultStatus.FIND_PAYMENT_SUCCESS);
    }

    @PostMapping(value = "/order-food-contracts/{id}/payment/confirmation", consumes = "application/json", produces = "application/json")
    public Response paymentConfirmation(@RequestBody PaymentConfirmation paymentConfirmation) {
        Order order = orderMapper.dto2Model(paymentConfirmation);
        boolean isCreated = paymentService.confirmPayment(order);
        if (isCreated) {
            return Response.success(ResultStatus.PAYMENT_CONFIRMATION_SUCCESS);
        } else {
            return Response.failure(ResultStatus.PAYMENT_CONFIRMATION_FAIL);
        }
    }

    @PostMapping(value = "/order-food-contracts/{id}/compensate", consumes = "application/json", produces = "application/json")
    public Response createCompensate(@RequestBody CompensateRequest compensateRequest, @PathVariable String id) throws JsonProcessingException {
        Compensate compensate = compensateMapper.dto2Model(compensateRequest);
        compensate.setContractId(id);
        compensate.setCompensateState("CREATED");
        boolean sendResult = paymentService.createCompensate(compensate);
        if (sendResult) {
            return Response.success(ResultStatus.COMPENSATE_CREATE_SUCCESS);
        } else {
            return Response.failure(ResultStatus.COMPENSATE_CREATE_FAIL);
        }
    }
}