package com.trungbeso.dreamshops.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String brand;

	private BigDecimal price;

	private int inventory; //quantity

	private String description;

	//cascade all mean when delete product, the relationship is gone too
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "category_id")
	private Category category;

	//1 product have a list of image
	// orphanRemoval=true mean once product have been deleted, image belong to this product will be deleted too
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	public List<Image> images;
}
