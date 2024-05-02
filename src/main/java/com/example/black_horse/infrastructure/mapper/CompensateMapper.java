package com.example.black_horse.infrastructure.mapper;

import com.example.black_horse.infrastructure.repository.entity.CompensateEntity;
import com.example.black_horse.infrastructure.stream.dto.CompensateMessage;
import com.example.black_horse.model.Compensate;
import com.example.black_horse.webinterface.dto.CompensateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CompensateMapper {
    CompensateRequest model2Dto(Compensate compensate);

    Compensate dto2Model(CompensateRequest compensateRequest);

    CompensateEntity model2Entity(Compensate compensate);
    Compensate entity2Model(CompensateEntity entity);
    CompensateMessage model2Message(Compensate compensate);
}
