package ru.tipsauk.rental.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tipsauk.rental.entity.Document;

@Repository
public interface DocumentRepository extends CrudRepository<Document, Long> {

}
