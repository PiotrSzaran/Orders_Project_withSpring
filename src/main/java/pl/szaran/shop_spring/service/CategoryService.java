package pl.szaran.shop_spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.szaran.shop_spring.exceptions.ExceptionCode;
import pl.szaran.shop_spring.exceptions.MyException;
import pl.szaran.shop_spring.model.Category;
import pl.szaran.shop_spring.model.dto.CategoryDTO;
import pl.szaran.shop_spring.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ErrorService errorService;
    private final String TABLE = "CATEGORY;";


    public void addCategory(CategoryDTO categoryDTO) {

        Category category;
        if (categoryDTO.getId() != null) {
            category = categoryRepository.findById(categoryDTO.getId()).orElse(null);
        } else {
            category = categoryRepository.findByName(categoryDTO.getName()).orElse(null);

            if (category == null) {
                category = ModelMapper.fromCategoryDTOToCategory(categoryDTO);
                categoryRepository.save(category);
            } else {
                String message = "CATEGORY " + categoryDTO.getName() + " ALREADY ADDED";
                errorService.addError(TABLE + message);
                System.out.println(message);
            }
        }

        if (category == null) {
            errorService.addError(TABLE + "PROBLEMS WITH CATEGORY");
            throw new MyException(ExceptionCode.SERVICE, "CATEGORY SERVICE, addCategory() - PROBLEMS WITH CATEGORY");
        }
    }

    public List<CategoryDTO> getCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(ModelMapper::fromCategoryToCategoryDTO)
                .collect(Collectors.toList());
    }
}
