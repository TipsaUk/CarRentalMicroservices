package ru.tipsauk.rental.service.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tipsauk.rental.dto.CarDto;
import ru.tipsauk.rental.dto.CarRentalDto;
import ru.tipsauk.rental.dto.ClientDto;
import ru.tipsauk.rental.dto.RentalFeeDto;
import ru.tipsauk.rental.entity.Car;
import ru.tipsauk.rental.entity.CarRental;
import ru.tipsauk.rental.entity.Client;
import ru.tipsauk.rental.entity.RentalStatus;
import ru.tipsauk.rental.exception.EntityOperationException;
import ru.tipsauk.rental.mapper.CarRentalMapper;
import ru.tipsauk.rental.repository.RentalRepository;
import ru.tipsauk.rental.service.kafka.Impl.KafkaProducerServiceImpl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentalServiceImplTest {

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private KafkaProducerServiceImpl kafkaProducerServiceImpl;

    @Mock
    private CarRentalMapper carRentalMapper;

    @InjectMocks
    private RentalServiceImpl rentalService;

    private CarRental carRental1;

    private CarRental carRental2;

    private CarRentalDto carRentalDto1;

    private CarRentalDto carRentalDto2;

    private RentalFeeDto rentalFeeDto;

    @BeforeEach
    void setUp() {
        carRental1 = new CarRental(1, new Client(), new Car(), new Date(), new Date()
                , RentalStatus.BOOKED, false, "1111");
        carRental2 = new CarRental(2, new Client(), new Car(), new Date(), new Date()
                , RentalStatus.RENTED, false, "2222");
        carRentalDto1 = new CarRentalDto(1, new ClientDto(), new CarDto(), new Date(), new Date()
                , RentalStatus.BOOKED, false, "1111");
        carRentalDto2 = new CarRentalDto(2, new ClientDto(), new CarDto(), new Date(), new Date()
                , RentalStatus.RENTED, false, "2222");
        rentalFeeDto = new RentalFeeDto();
        rentalFeeDto.setRentId(1L);
        rentalFeeDto.setPaid(true);
        rentalFeeDto.setBankPaymentId("bank_payment_id");
    }

    @Test
    void findAll_WhenRetrievingAllCarRentals_ExpectCorrectListOfCarRentalDTOs() {
        List<CarRental> cars = Arrays.asList(carRental1, carRental2);
        when(rentalRepository.findAll()).thenReturn(cars);
        when(carRentalMapper.carRentalToCarRentalDto(carRental1)).thenReturn(carRentalDto1);
        when(carRentalMapper.carRentalToCarRentalDto(carRental2)).thenReturn(carRentalDto2);

        List<CarRentalDto> result = rentalService.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0)).isEqualTo(carRentalDto1);
        assertThat(result.get(1)).isEqualTo(carRentalDto2);
        verify(rentalRepository, times(1)).findAll();
    }

    @Test
    void findById_WhenRetrievingCarRentalById_ExpectCorrectCarRentalDTO() {
        when(rentalRepository.findById(1L)).thenReturn(Optional.ofNullable(carRental1));
        when(carRentalMapper.carRentalToCarRentalDto(carRental1)).thenReturn(carRentalDto1);

        CarRentalDto result = rentalService.findById(1L);

        assertThat(result).isNotNull().isEqualTo(carRentalDto1);
        verify(rentalRepository, times(1)).findById(1L);
    }

    @Test
    void create_WhenCreatingCarRental_ExpectSuccessfulCreationAndReturnCarRental() {
        when(rentalRepository.save(carRental1)).thenReturn(carRental1);
        when(carRentalMapper.carRentalDtoToCarRental(carRentalDto1)).thenReturn(carRental1);

        CarRental result = rentalService.create(carRentalDto1);

        assertThat(result).isNotNull().isEqualTo(carRental1);
        verify(rentalRepository, times(1)).save(carRental1);
    }

    @Test
    void update_WhenUpdatingCarRental_ExpectSuccessfulUpdateAndReturnCarRental() {
        when(rentalRepository.save(carRental1)).thenReturn(carRental1);
        when(carRentalMapper.carRentalDtoToCarRental(carRentalDto1)).thenReturn(carRental1);

        CarRental result = rentalService.update(carRentalDto1);

        assertThat(result).isNotNull().isEqualTo(carRental1);
        verify(rentalRepository, times(1)).save(carRental1);
    }

    @Test
    void deleteById_WhenDeletingCarRentalById_ExpectSuccessfulDeletion() {
        when(rentalRepository.findById(1L)).thenReturn(Optional.ofNullable(carRental1));
        rentalService.deleteById(1L);
        verify(rentalRepository, times(1)).deleteById(1L);
    }

    @Test
    public void confirmRentalPayment_WhenPaymentIsSuccessful_ExpectStatusBooked() {
        when(rentalRepository.findById(1L)).thenReturn(Optional.of(carRental1));

        rentalService.confirmRentalPayment(rentalFeeDto);

        verify(rentalRepository, times(1)).findById(1L);
        verify(rentalRepository, times(1)).save(any(CarRental.class));
        assertThat(carRental1.getStatus()).isEqualTo(RentalStatus.BOOKED);
        assertThat(carRental1.isPaid()).isTrue();
        assertThat(carRental1.getBankPaymentId()).isEqualTo("bank_payment_id");
    }

    @Test
    public void confirmRentalPayment_WhenPaymentIsNotSuccessful_ExpectStatusCancelled() {
        rentalFeeDto.setPaid(false);
        when(rentalRepository.findById(1L)).thenReturn(Optional.of(carRental1));

        rentalService.confirmRentalPayment(rentalFeeDto);
        verify(rentalRepository, times(1)).findById(1L);
        verify(rentalRepository, times(1)).save(any(CarRental.class));
        assertThat(carRental1.getStatus()).isEqualTo(RentalStatus.CANCELLED);
    }

    @Test
    public void confirmRentalPayment_WhenRentalNotFound_ExpectException() {
        when(rentalRepository.findById(1L)).thenReturn(Optional.empty());

        try {
            rentalService.confirmRentalPayment(rentalFeeDto);
        } catch (EntityOperationException e) {
            assertThat(e.getMessage()).isEqualTo("Car rental not found by id 1");
        }

        verify(rentalRepository, times(1)).findById(1L);
        verify(rentalRepository, times(0)).save(any(CarRental.class));
    }
}