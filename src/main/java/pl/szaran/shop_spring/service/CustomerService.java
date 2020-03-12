package pl.szaran.shop_spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szaran.shop_spring.exceptions.ExceptionCode;
import pl.szaran.shop_spring.exceptions.MyException;
import pl.szaran.shop_spring.model.Country;
import pl.szaran.shop_spring.model.Customer;
import pl.szaran.shop_spring.model.dto.CustomerDTO;
import pl.szaran.shop_spring.repository.CountryRepository;
import pl.szaran.shop_spring.repository.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CountryRepository countryRepository;
    private final ErrorService errorService;
    private String TABLE = "CUSTOMER;";

    public void addCustomer(CustomerDTO customerDTO) {

        Customer customer = null;

        if (customerDTO.getId() != null) {
            customer = customerRepository.findById(customerDTO.getId()).orElse(null);
        }

        if (customer == null) {
            Country country = null;

            if (customerDTO.getCountryDTO().getId() != null) {
                country = countryRepository.findById(customerDTO.getCountryDTO().getId()).orElse(null);
            }

            if (country == null && customerDTO.getCountryDTO().getName() != null) {
                country = countryRepository.findByName(customerDTO.getCountryDTO().getName()).orElse(null);
            }

            if (country == null) {
                country = countryRepository
                        .save(Country.builder().name(customerDTO.getCountryDTO().getName()).build());
            }

            if (country == null) {
                throw new MyException(ExceptionCode.SERVICE, "CUSTOMER SERVICE - addCustomer(), CANNOT ADD NEW COUNTRY");
            }

            customer = ModelMapper.fromCustomerDTOToCustomer(customerDTO);
            customer.setCountry(country);
            customerRepository.save(customer);

            if (customer == null) {
                errorService.addError(TABLE + "PROBLEMS WITH CUSTOMER");
                throw new MyException(ExceptionCode.SERVICE, "CUSTOMER SERVICE, addCustomer() - PROBLEMS WITH CUSTOMER");
            }

        }
    }

    public List<CustomerDTO> getCustomers() {
        return customerRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromCustomerToCustomerDTO)
                .collect(Collectors.toList());
    }
}
