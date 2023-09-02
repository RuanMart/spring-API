package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.DTO.CategoryDTO;
import com.devsuperior.dscatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<CategoryDTO>> pagedFindAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "moment") String orderBy,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction
    ) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        Page<CategoryDTO> categories = categoryService.pagedFindAll(pageRequest);
        return ResponseEntity.ok(categories);
    }

    @GetMapping(value = "/all")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<CategoryDTO>>  findAll() {
        List<CategoryDTO>  categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CategoryDTO> findById(
            @PathVariable Long id) {
        CategoryDTO categoryDTO = categoryService.findById(id);
        return ResponseEntity.ok(categoryDTO);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO categoryDTO){
        categoryDTO = categoryService.create(categoryDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}").buildAndExpand(categoryDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(categoryDTO);
    }

    @PutMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CategoryDTO> update(
            @RequestBody CategoryDTO categoryDTO,
            @PathVariable Long id) {
        categoryDTO = categoryService.update(id, categoryDTO);
        return ResponseEntity.ok(categoryDTO);
    }

    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<CategoryDTO> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
