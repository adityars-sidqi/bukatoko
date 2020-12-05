package com.aditya.bukatoko.productservice.service;

import java.util.Optional;
import java.util.stream.Collectors;

import com.aditya.bukatoko.productservice.pojo.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.aditya.bukatoko.productservice.dao.ProductRepository;
import com.aditya.bukatoko.productservice.dto.ProductDTO;
import com.aditya.bukatoko.productservice.entity.Product;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private ModelMapper modelMapper;

	public Product save(Product product) {
		return productRepository.save(product);
	}

	public Optional<ProductDTO> findById(Long id) {
		return Optional.of(convertToDto(productRepository.findById(id).get()));
	}

	public Page<ProductDTO> findAll(Pageable pageable) {
		Page<Product> products = productRepository.findAll(pageable);
		return new PageImpl<>(products.stream().map(this::convertToDto).collect(Collectors.toList()),
				pageable, products.getTotalElements());
	}

	private ProductDTO convertToDto(Product product) {
		ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
		Category category = restTemplate.getForObject("http://category-service/categories/" + product.getCategoryId(), Category.class);
		productDTO.setCategory(category);
		return productDTO;
	}

}
