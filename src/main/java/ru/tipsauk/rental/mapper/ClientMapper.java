package ru.tipsauk.rental.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.tipsauk.rental.dto.ClientDto;
import ru.tipsauk.rental.dto.DocumentDto;
import ru.tipsauk.rental.entity.Client;
import ru.tipsauk.rental.entity.Document;

@Mapper(componentModel = "spring", uses = DocumentMapper.class)
public interface ClientMapper {

    ClientDto clientToClientDto(Client client);

    Client clientDtoToClient(ClientDto clientDto);

}
