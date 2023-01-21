package com.turkcell.rentACar.entities.converters;

import com.turkcell.rentACar.entities.dtos.get.GetRentalDto;
import com.turkcell.rentACar.entities.dtos.list.CustomerListDto;
import com.turkcell.rentACar.entities.sourceEntities.Rental;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hzyazilimci
 */

@Component
@RequiredArgsConstructor
public class RentalConverter {

    private final AdditionConverter additionConverter;

    public GetRentalDto convertRentalToDto(Rental rental){
        return GetRentalDto.builder()
                .rentId(rental.getRentId())
                .rentDate(rental.getRentDate())
                .rentReturnDate(rental.getRentReturnDate())
                .brandName(rental.getCar().getBrand().getBrandName())
                .description(rental.getCar().getDescription())
                .rentKilometer(rental.getRentKilometer())
                .rentReturnKilometer(rental.getRentReturnKilometer())
                .fromCityName(rental.getFromCity().getCityName())
                .toCityName(rental.getToCity().getCityName())
                .additionList(this.additionConverter.convertAdditionToDto(rental.getAdditionList()))
                .customerListDto(CustomerListDto.builder()
                        .userId(rental.getCustomer().getUserId())
                        .email(rental.getCustomer().getEmail())
                        .registerDate(rental.getCustomer().getRegisterDate())
                        .build())
                .build();
    }

    public List<GetRentalDto> convertRentalToDto(List<Rental> rentals){
        return rentals.stream().map(this::convertRentalToDto).collect(Collectors.toList());
    }
}
