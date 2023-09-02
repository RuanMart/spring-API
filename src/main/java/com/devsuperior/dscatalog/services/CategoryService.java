package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.DTO.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exception.DataBaseException;
import com.devsuperior.dscatalog.services.exception.ResourceAlreadyExists;
import com.devsuperior.dscatalog.services.exception.ResourceNotFoundException;
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
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<CategoryDTO> pagedFindAll(PageRequest pageRequest) {
        Page<Category> categories = categoryRepository.findAll(pageRequest);

        return categories.map(x -> new CategoryDTO(x));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> categoryObj = categoryRepository.findById(id);
        Category category = categoryObj.orElseThrow
                (() -> new ResourceNotFoundException("Categoria não encontrada " + id));
        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO create(CategoryDTO categoryDTO) {
        List<Category> foundCategory = categoryRepository.findByName(categoryDTO.getName());
        if (foundCategory.isEmpty()) {
            Category entity = new Category();
            entity.setName(categoryDTO.getName());
            entity = categoryRepository.save(entity);
            return new CategoryDTO(entity);
        } else {
            throw new ResourceAlreadyExists("Essa categoria já existe");
        }
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO categoryDTO){
        try {
             Category entity = categoryRepository.getReferenceById(id);
             entity.setName(categoryDTO.getName());
             entity = categoryRepository.save(entity);
             return new CategoryDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Categoria não encontrada " + id);
        }
    }

    public void delete(Long id){
        try {
            if (categoryRepository.existsById(id)) {
                categoryRepository.deleteById(id);
            } else {
                throw new ResourceNotFoundException("Categoria não encontrada " + id);
            }
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Violação de integridade de dados");
       }
    }
}