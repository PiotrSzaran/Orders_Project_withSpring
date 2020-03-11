package pl.szaran.shop_spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szaran.shop_spring.model.Error;
import pl.szaran.shop_spring.model.dto.ErrorDTO;
import pl.szaran.shop_spring.repository.ErrorRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ErrorService {

    private final ErrorRepository errorRepository;

    public void addError(String message) {

        Error error = ModelMapper.fromErrorDTOToError(ErrorDTO.builder().message(message).date(LocalDateTime.now()).build());
        errorRepository.save(error);
    }

    public List<ErrorDTO> getErrors() {
        return errorRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromErrorToErrorDTO)
                .collect(Collectors.toList());
    }
}
