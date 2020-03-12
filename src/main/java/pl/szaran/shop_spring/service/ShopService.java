package pl.szaran.shop_spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szaran.shop_spring.exceptions.ExceptionCode;
import pl.szaran.shop_spring.exceptions.MyException;
import pl.szaran.shop_spring.model.Country;
import pl.szaran.shop_spring.model.Shop;
import pl.szaran.shop_spring.model.dto.ShopDTO;
import pl.szaran.shop_spring.repository.CountryRepository;
import pl.szaran.shop_spring.repository.ShopRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final CountryRepository countryRepository;
    private final ErrorService errorService;
    private final String TABLE = "SHOP;";

    public void addShop(ShopDTO shopDTO) {

        Shop shop = null;

        if (shopDTO.getId() != null) {
            shop = shopRepository.findById(shopDTO.getId()).orElse(null);
        }

        if (shop == null) {
            Country country = null;

            if (shopDTO.getCountryDTO().getId() != null) {
                country = countryRepository.findById(shopDTO.getCountryDTO().getId()).orElse(null);
            }

            if (country == null && shopDTO.getCountryDTO().getName() != null) {
                country = countryRepository.findByName(shopDTO.getCountryDTO().getName()).orElse(null);
            }

            if (country == null) {
                country = countryRepository
                        .save(Country.builder().name(shopDTO.getCountryDTO().getName()).build());
            }

            if (country == null) {
                throw new MyException(ExceptionCode.SERVICE, "SHOP SERVICE - addShop(), CANNOT ADD NEW COUNTRY");
            }

            shop = ModelMapper.fromShopDTOToShop(shopDTO);
            shop.setCountry(country);
            shopRepository.save(shop);

            if (shop == null) {
                errorService.addError(TABLE + "PROBLEMS WITH SHOP");
                throw new MyException(ExceptionCode.SERVICE, "SHOP SERVICE, addShop() - PROBLEMS WITH SHOP");
            }
        }
    }

    public List<ShopDTO> getShops() {
        return
                shopRepository.findAll()
                        .stream()
                        .map(ModelMapper::fromShopToShopDTO)
                        .collect(Collectors.toList());
    }
}
