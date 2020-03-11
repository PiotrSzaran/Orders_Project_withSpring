package pl.szaran.shop_spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szaran.shop_spring.exceptions.ExceptionCode;
import pl.szaran.shop_spring.exceptions.MyException;
import pl.szaran.shop_spring.model.Country;
import pl.szaran.shop_spring.model.dto.CountryDTO;
import pl.szaran.shop_spring.repository.CountryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;
    private final ErrorService errorService;
    private final String TABLE = "COUNTRY;";

    public void addCountry(CountryDTO countryDTO) {

        Country country;
        if (countryDTO.getId() != null) {
            country = countryRepository.findById(countryDTO.getId()).orElse(null);
        } else {
            country = countryRepository.findByName(countryDTO.getName()).orElse(null);

            if (country == null) {
                country = ModelMapper.fromCountryDTOToCountry(countryDTO);
                countryRepository.save(country);
            } else {
                String message = "COUNTRY " + countryDTO.getName() + " ALREADY ADDED";
                errorService.addError(TABLE + message);
                System.out.println(message);
            }
        }

        if (country == null) {
            errorService.addError(TABLE + "PROBLEMS WITH COUNTRY");
            throw new MyException(ExceptionCode.SERVICE, "COUNTRY SERVICE, addCountry() - PROBLEMS WITH COUNTRY");
        }
    }

    public List<CountryDTO> getCountries() {
        return countryRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromCountryToCountryDTO)
                .collect(Collectors.toList());
    }
}
