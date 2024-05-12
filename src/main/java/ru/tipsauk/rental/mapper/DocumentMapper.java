package ru.tipsauk.rental.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.tipsauk.rental.dto.DocumentDto;
import ru.tipsauk.rental.entity.Document;

@Mapper(componentModel = "spring")
public interface DocumentMapper {


    DocumentDto documentToDocumentDto(Document client);
    Document DocumentDtoToDocument(DocumentDto clientDto);

}
