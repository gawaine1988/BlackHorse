package com.example.black_horse.infrastructure.mapper;

import com.example.black_horse.infrastructure.repository.entity.OrderEntity;
import com.example.black_horse.model.Order;
import com.example.black_horse.webinterface.dto.PaymentConfirmation;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrderMapper {
    PaymentConfirmation model2Dto(Order order);

    Order dto2Model(PaymentConfirmation paymentConfirmation);

    Order entity2Model(OrderEntity entity);

    OrderEntity model2Entity(Order order);
}
