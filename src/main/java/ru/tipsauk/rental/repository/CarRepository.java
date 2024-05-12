package ru.tipsauk.rental.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tipsauk.rental.entity.Car;
import ru.tipsauk.rental.entity.Client;

import java.util.List;

@Repository
public interface CarRepository extends CrudRepository<Car, Long> {

    List<Car> findAll();

    Car findById(long id);

    Car save(Car car);

    void deleteById(long id);
}
