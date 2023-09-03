package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.DTO.ProductDTO;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exception.DataBaseException;
import com.devsuperior.dscatalog.services.exception.RecordAlreadyExistsException;
import com.devsuperior.dscatalog.services.exception.RecordNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductDTO> findAll() {
        List<Product> categories = productRepository.findAll();
        return categories.stream().map(product -> new ProductDTO(product)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> pagedFindAll(PageRequest pageRequest) {
        Page<Product> categories = productRepository.findAll(pageRequest);

        return categories.map(product -> new ProductDTO(product));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> categoryObj = productRepository.findById(id);
        Product product = categoryObj.orElseThrow
                (() -> new RecordNotFoundException("Produto não encontrado " + id));
        return new ProductDTO(product, product.getCategories());
    }

    @Transactional
    public ProductDTO create(ProductDTO productDTO) {
        List<Product> foundCategory = productRepository.findByName(productDTO.getName());
        if (foundCategory.isEmpty()) {
            Product entity = new Product();
            entity.setName(productDTO.getName());
            entity = productRepository.save(entity);
            return new ProductDTO(entity);
        } else {
            throw new RecordAlreadyExistsException("Esse produto já existe já existe");
        }
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO){
        try {
             Product entity = productRepository.getReferenceById(id);
             entity.setName(productDTO.getName());
             entity.setDescription(productDTO.getDescription());
             entity.setPrice(productDTO.getPrice());
             entity.setImgUrl(productDTO.getImgUrl());
             entity.setDate(productDTO.getDate());
             entity = productRepository.save(entity);
             return new ProductDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new RecordNotFoundException("Produto não encontrado " + id);
        }
    }

    public void delete(Long id){
        try {
            if (productRepository.existsById(id)) {
                productRepository.deleteById(id);
            } else {
                throw new RecordNotFoundException("Produto não encontrado " + id);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Violação de integridade de dados");
       }
    }
}