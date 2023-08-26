package com.devsuperior.dscatalog.resources;

import com.devsuperior.dscatalog.entities.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryResource {

    @GetMapping
    //@ResponseStatus (code = HttpStatus.FOUND)
    public ResponseEntity<List<Category>> findAll() {
        List<Category> list = new ArrayList<>();

        list.add(new Category(1L, "books"));
        list.add(new Category(2L, "eletronics"));

        return ResponseEntity.ok(list);
    }
}








































