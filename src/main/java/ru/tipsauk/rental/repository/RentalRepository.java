package ru.tipsauk.rental.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tipsauk.rental.entity.CarRental;

@Repository
public interface RentalRepository extends JpaRepository<CarRental, Long> {


}
