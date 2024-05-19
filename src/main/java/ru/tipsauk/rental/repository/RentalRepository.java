package ru.tipsauk.rental.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tipsauk.rental.entity.CarRental;
import java.util.List;
import java.util.Optional;

@Repository
public interface RentalRepository extends JpaRepository<CarRental, Long> {


}
