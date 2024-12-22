package com.trungbeso.dreamshops.services.product;

import com.trungbeso.dreamshops.exception.ProductNotFoundException;
import com.trungbeso.dreamshops.models.Category;
import com.trungbeso.dreamshops.models.Product;
import com.trungbeso.dreamshops.repositories.CategoryRepository;
import com.trungbeso.dreamshops.repositories.ProductRepository;
import com.trungbeso.dreamshops.request.AddProductRequest;
import com.trungbeso.dreamshops.request.ProductUpdateRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService implements IProductService {

	ProductRepository productRepository;
	CategoryRepository categoryRepository;

	@Override
	public Product addProduct(AddProductRequest request) {
		//check if the category is found in the Database
		// If yes, set it as the new Product category
		// If no, save it as a new category then set as the new Product category

		Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
			  .orElseGet(() -> {
				  Category newCategory = new Category(request.getCategory().getName());
				  return categoryRepository.save(newCategory);
			  });
		request.setCategory(category);
		return productRepository.save(createProduct(request, category));
	}

	private Product createProduct(AddProductRequest request, Category category) {
		return  new Product(
			  request.getName(),
			  request.getBrand(),
			  request.getPrice(),
			  request.getInventory(),
			  request.getDescription(),
			 category
		);

	}

	@Override
	public Product getProductById(Long id) {
		return productRepository.findById(id).
			  orElseThrow(() -> new ProductNotFoundException("Product Not Found Exception!"));
	}

	@Override
	public void deleteProductById(Long id) {
		productRepository.findById(id).ifPresentOrElse(productRepository::delete, () -> {
			throw new ProductNotFoundException("Product Not Found Exception!");
		});
	}

	@Override
	public Product updateProduct(ProductUpdateRequest request, Long productId) {
		return productRepository.findById(productId)
			  .map(existingProduct -> updateExistingProduct(existingProduct, request))
			  .map(productRepository::save)
			  .orElseThrow(() -> new ProductNotFoundException("Product Not Found Exception!"));
	}

	private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request) {
		existingProduct.setName(request.getName());
		existingProduct.setBrand(request.getBrand());
		existingProduct.setPrice(request.getPrice());
		existingProduct.setInventory(request.getInventory());
		existingProduct.setDescription(request.getDescription());

		Category category = categoryRepository.findByName(request.getCategory().getName());
		existingProduct.setCategory(category);
		return existingProduct;
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> getProductsByCategory(String category) {
		return productRepository.findByCategoryName(category);
	}

	@Override
	public List<Product> getProductByBrand(String brand) {
		return productRepository.findByBrand(brand);
	}

	@Override
	public List<Product> getProductByCategoryAndBrand(String category, String brand) {
		return productRepository.findByCategoryNameAndBrand(category, brand);
	}

	@Override
	public List<Product> getProductByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public List<Product> getProductByBrandAndName(String brand, String name) {
		return productRepository.findByBrandAndBrand(brand, name);
	}

	@Override
	public Long countProductsByBrandAndName(String brand, String name) {
		return productRepository.countByBrandAndName(brand, name);
	}
}
