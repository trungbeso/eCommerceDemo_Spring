package com.trungbeso.dreamshops.dtos;

import com.trungbeso.dreamshops.models.Category;
import com.trungbeso.dreamshops.models.Image;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {
	private Long id;

	private String name;

	private String brand;

	private BigDecimal price;

	private int inventory; //quantity

	private String description;

	private Category category;

	private List<ImageDto> images;
}
