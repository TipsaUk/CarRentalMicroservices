package ru.tipsauk.rental.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tipsauk.rental.entity.Client;


@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

}
