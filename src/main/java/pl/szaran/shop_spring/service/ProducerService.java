package pl.szaran.shop_spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szaran.shop_spring.exceptions.ExceptionCode;
import pl.szaran.shop_spring.exceptions.MyException;
import pl.szaran.shop_spring.model.Country;
import pl.szaran.shop_spring.model.Producer;
import pl.szaran.shop_spring.model.Trade;
import pl.szaran.shop_spring.model.dto.ProducerDTO;
import pl.szaran.shop_spring.repository.CountryRepository;
import pl.szaran.shop_spring.repository.ProducerRepository;
import pl.szaran.shop_spring.repository.TradeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProducerService {
    private final ProducerRepository producerRepository;
    private final CountryRepository countryRepository;
    private final TradeRepository tradeRepository;
    private final ErrorService errorService;
    private final String TABLE = "PRODUCER;";

    public void addProducer(ProducerDTO producerDTO) {

        Producer producer = null;
        if (producerDTO.getId() != null) {
            producer = producerRepository.findById(producerDTO.getId()).orElse(null);
        }
        if (producer == null) {

            Country country = null;

            if (producerDTO.getCountryDTO().getId() != null) {
                country = countryRepository.findById(producerDTO.getCountryDTO().getId()).orElse(null);
            }
            if (country == null && producerDTO.getCountryDTO().getName() != null) {
                country = countryRepository.findByName(producerDTO.getCountryDTO().getName()).orElse(null);
            }
            if (country == null) {
                country = countryRepository
                        .save(Country.builder().name(producerDTO.getCountryDTO().getName()).build());
            }

            if (country == null) {
                throw new MyException(ExceptionCode.SERVICE, "PRODUCER SERVICE - addProducer(), CANNOT ADD NEW COUNTRY");
            }

            Trade trade = null;
            if (producerDTO.getTradeDTO().getId() != null) {
                trade = tradeRepository.findById(producerDTO.getTradeDTO().getId()).orElse(null);
            }
            if (trade == null && producerDTO.getTradeDTO().getName() != null) {
                trade = tradeRepository.findByName(producerDTO.getTradeDTO().getName()).orElse(null);
            }

            if (trade == null) {
                trade = tradeRepository
                        .save(Trade.builder().name(producerDTO.getTradeDTO().getName()).build());
            }

            if (trade == null) {
                throw new MyException(ExceptionCode.SERVICE, "PRODUCER SERVICE - addProducer(), CANNOT ADD NEW TRADE");
            }

            producer = ModelMapper.fromProducerDTOToProducer(producerDTO);
            producer.setTrade(trade);
            producer.setCountry(country);
            producerRepository.save(producer);

            if (producer == null) {
                errorService.addError(TABLE + "PROBLEMS WITH PRODUCER");
                throw new MyException(ExceptionCode.SERVICE, "PRODUCER SERVICE, addProducer() - PROBLEMS WITH PRODUCER");
            }
        }
    }

    public List<ProducerDTO> getProducers() {
        return producerRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromProducerToProducerDTO)
                .collect(Collectors.toList());
    }
}

