package pl.szaran.shop_spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szaran.shop_spring.exceptions.ExceptionCode;
import pl.szaran.shop_spring.exceptions.MyException;
import pl.szaran.shop_spring.model.Trade;
import pl.szaran.shop_spring.model.dto.TradeDTO;
import pl.szaran.shop_spring.repository.TradeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeService {

    private final TradeRepository tradeRepository;
    private final ErrorService errorService;
    private final String TABLE = "TRADE;";

    public void addTrade(TradeDTO tradeDTO) {

        Trade trade;
        if (tradeDTO.getId() != null) {
            trade = tradeRepository.findById(tradeDTO.getId()).orElse(null);
        } else {
            trade = tradeRepository.findByName(tradeDTO.getName()).orElse(null);

            if (trade == null) {
                trade = ModelMapper.fromTradeDTOToTrade(tradeDTO);
                tradeRepository.save(trade);
            } else {
                String errorMessage = "TRADE " + tradeDTO.getName() + " ALREADY ADDED";
                System.out.println(errorMessage);
                errorService.addError(TABLE + errorMessage);
            }
        }

        if (trade == null) {
            errorService.addError(TABLE + "PROBLEMS WITH TRADE");
            throw new MyException(ExceptionCode.SERVICE, "TRADE SERVICE, addTrade() - PROBLEMS WITH TRADE");
        }
    }

    public List<TradeDTO> getTrades() {
        return tradeRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromTradeToTradeDTO)
                .collect(Collectors.toList());
    }
}
