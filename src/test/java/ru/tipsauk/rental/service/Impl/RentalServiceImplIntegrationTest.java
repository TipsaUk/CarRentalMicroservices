package ru.tipsauk.rental.service.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.tipsauk.rental.dto.CarDto;
import ru.tipsauk.rental.dto.CarRentalDto;
import ru.tipsauk.rental.dto.ClientDto;
import ru.tipsauk.rental.dto.RentalFeeDto;
import ru.tipsauk.rental.entity.Car;
import ru.tipsauk.rental.entity.CarRental;
import ru.tipsauk.rental.entity.Client;
import ru.tipsauk.rental.entity.RentalStatus;
import ru.tipsauk.rental.exception.EntityOperationException;
import ru.tipsauk.rental.exception.EntityUpdateException;
import ru.tipsauk.rental.repository.RentalRepository;
import ru.tipsauk.rental.service.RentalService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class RentalServiceImplIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("rental")
            .withUsername("user")
            .withPassword("password");

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private RentalService rentalService;

    private CarRentalDto carRentalDto;

    private RentalFeeDto rentalFeeDto;


    @BeforeEach
    void setUp() {
        CarRental carRental1 = new CarRental(1, new Client(1L), new Car(1L), Date.from(LocalDate.of(2024, 4, 19)
                .atStartOfDay(ZoneId.systemDefault()).toInstant())
                , Date.from(LocalDate.of(2024, 4, 20)
                .atStartOfDay(ZoneId.systemDefault()).toInstant())
                , RentalStatus.UNPAID, false, null);
        CarRental carRental2 = new CarRental(2, new Client(2L), new Car(2L), Date.from(LocalDate.of(2024, 4, 19)
                .atStartOfDay(ZoneId.systemDefault()).toInstant())
                , Date.from(LocalDate.of(2024, 4, 20)
                .atStartOfDay(ZoneId.systemDefault()).toInstant())
                , RentalStatus.RENTED, false, null);
        carRentalDto = new CarRentalDto(2, new ClientDto(2L), new CarDto(2L), Date.from(LocalDate.of(2024, 4, 19)
                .atStartOfDay(ZoneId.systemDefault()).toInstant())
                , Date.from(LocalDate.of(2024, 4, 20)
                .atStartOfDay(ZoneId.systemDefault()).toInstant())
                , RentalStatus.UNPAID, false, null);

        rentalFeeDto = new RentalFeeDto();
        rentalFeeDto.setRentId(1L);
        rentalFeeDto.setPaid(true);
        rentalFeeDto.setBankPaymentId("bank_payment_id");

        rentalRepository.deleteAll();
        rentalRepository.save(carRental1);
        rentalRepository.save(carRental2);
    }

    @Test
    void findAll_ShouldReturnAllCarRentals() {
        List<CarRentalDto> carRentals = rentalService.findAll();
        assertThat(carRentals).hasSizeGreaterThan(0);
    }

    @Test
    void findById_ShouldReturnCarRental() {
        List<CarRentalDto> carRentals = rentalService.findAll();
        CarRentalDto carRentalDto = rentalService.findById(carRentals.get(0).getId());
        assertThat(carRentalDto).isNotNull();
        assertThat(carRentalDto.getId()).isEqualTo(carRentals.get(0).getId());
    }

    @Test
    void findById_ShouldThrowException_WhenNotFound() {
        long nonExistentId = 9999L;
        EntityOperationException thrown = assertThrows(EntityOperationException.class
                , () -> rentalService.findById(nonExistentId));
        assertThat(thrown).hasMessageContaining("Car rental not found by id " + nonExistentId);
    }

    @Test
    void create_ShouldSaveAndReturnCarRental() {
        CarRentalDto newRentalDto = new CarRentalDto(2, new ClientDto(1L), new CarDto(1L)
                , Date.from(LocalDate.of(2024, 4, 19)
                .atStartOfDay(ZoneId.systemDefault()).toInstant())
                , Date.from(LocalDate.of(2024, 4, 20)
                .atStartOfDay(ZoneId.systemDefault()).toInstant())
                , RentalStatus.UNPAID, false, null);

        CarRental createdRental = rentalService.create(newRentalDto);

        assertThat(createdRental).isNotNull();
        assertThat(createdRental.getId()).isNotNull();
        assertThat(createdRental.getStatus()).isEqualTo(RentalStatus.UNPAID);
    }

    @Test
    void update_ShouldModifyAndReturnCarRental() {
        carRentalDto.setStatus(RentalStatus.RENTED);

        CarRental updatedRental = rentalService.update(carRentalDto);

        assertThat(updatedRental).isNotNull();
        assertThat(updatedRental.getStatus()).isEqualTo(RentalStatus.RENTED);
    }

    @Test
    void update_ShouldThrowException_WhenNoIdProvided() {
        CarRentalDto updateDto = new CarRentalDto();
        updateDto.setStatus(RentalStatus.BOOKED);

        EntityUpdateException thrown = assertThrows(EntityUpdateException.class
                , () -> rentalService.update(updateDto));
        assertThat(thrown).hasMessageContaining("No id is specified for the car rental");
    }

    @Test
    void deleteById_ShouldRemoveCarRental() {
        List<CarRentalDto> carRentals = rentalService.findAll();
        long id = carRentals.get(0).getId();
        rentalService.deleteById(id);

        Optional<CarRental> deletedRental = rentalRepository.findById(id);
        assertThat(deletedRental).isEmpty();
    }

    @Test
    void deleteById_ShouldThrowException_WhenNotFound() {
        long nonExistentId = 9999L;
        EntityOperationException thrown = assertThrows(EntityOperationException.class
                , () -> rentalService.deleteById(nonExistentId));
        assertThat(thrown).hasMessageContaining("Car rental not found by id " + nonExistentId);
    }

    @Test
    void confirmRentalPayment_WhenPaymentIsSuccessful_ExpectStatusBooked() {
        List<CarRentalDto> carRentals = rentalService.findAll();
        long id = carRentals.get(0).getId();
        rentalFeeDto.setRentId(id);

        rentalService.confirmRentalPayment(rentalFeeDto);

        Optional<CarRental> updatedCarRental = rentalRepository.findById(id);
        assertThat(updatedCarRental.isPresent()).isTrue();
        assertThat(RentalStatus.BOOKED).isEqualTo(updatedCarRental.get().getStatus());
        assertThat(updatedCarRental.get().isPaid()).isTrue();
        assertThat(updatedCarRental.get().getBankPaymentId()).isEqualTo("bank_payment_id");
    }

    @Test
    void confirmRentalPayment_WhenPaymentIsNotSuccessful_ExpectStatusCancelled() {
        List<CarRentalDto> carRentals = rentalService.findAll();
        long id = carRentals.get(0).getId();
        rentalFeeDto.setRentId(id);
        rentalFeeDto.setPaid(false);

        rentalService.confirmRentalPayment(rentalFeeDto);

        Optional<CarRental> updatedCarRental = rentalRepository.findById(id);
        assertThat(updatedCarRental.isPresent()).isTrue();
        assertThat(RentalStatus.CANCELLED).isEqualTo(updatedCarRental.get().getStatus());
    }

    @Test
    void confirmRentalPayment_WhenRentalNotFound_ExpectException() {
        long nonExistentId = 999L;
        rentalFeeDto.setRentId(nonExistentId);

        EntityOperationException thrown = assertThrows(EntityOperationException.class, () -> {
            rentalService.confirmRentalPayment(rentalFeeDto);
        });

        assertThat(thrown).hasMessageContaining("Car rental not found by id " + nonExistentId);
    }

}
