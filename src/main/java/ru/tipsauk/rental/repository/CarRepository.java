package ru.tipsauk.rental.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.tipsauk.rental.entity.Car;


@Repository
public interface CarRepository extends CrudRepository<Car, Long> {

}
