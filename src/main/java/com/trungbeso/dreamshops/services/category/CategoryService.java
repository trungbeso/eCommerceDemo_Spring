package com.trungbeso.dreamshops.services.category;

import com.trungbeso.dreamshops.exception.AlreadyExistsException;
import com.trungbeso.dreamshops.exception.ResultNotFoundException;
import com.trungbeso.dreamshops.models.Category;
import com.trungbeso.dreamshops.repositories.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService implements ICategoryService {

	CategoryRepository categoryRepository;

	@Override
	public Category getCategoryById(Long id) {
		return categoryRepository.findById(id)
			  .orElseThrow(() -> new ResultNotFoundException("Category not found!"));
	}

	@Override
	public Category getCategoryByName(String name) {
		return categoryRepository.findByName(name);
	}

	@Override
	public List<Category> getAllCategories() {
		return categoryRepository.findAll();
	}

	@Override
	public Category addCategory(Category category) {
		return Optional.of(category).filter(c -> !categoryRepository.existsByName(c.getName()))
			  .map(categoryRepository::save)
			  .orElseThrow(() -> new AlreadyExistsException(category.getName() + " already exists"));
	}

	@Override
	public Category updateCategory(Category category, Long id) {
		return Optional.ofNullable(getCategoryById(id))
			  .map(oldCategory -> {
				  oldCategory.setName(category.getName());
				  return categoryRepository.save(oldCategory);
			  }).orElseThrow(() -> new ResultNotFoundException("Category not found!"));
	}

	@Override
	public void deleteCategory(Long id) {
		categoryRepository.findById(id).
			  ifPresentOrElse(categoryRepository::delete,() -> {
				  throw new ResultNotFoundException("Category not found!");
			  });
	}
}