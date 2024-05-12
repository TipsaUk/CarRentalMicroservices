package ru.tipsauk.rental.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tipsauk.rental.entity.CarRental;
import ru.tipsauk.rental.entity.Client;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    List<Client> findAll();

    Client findById(long id);

    Client save(Client client);

    void deleteById(long id);

}
