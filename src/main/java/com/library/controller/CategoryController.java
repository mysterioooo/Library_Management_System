package com.library.controller;

import com.library.entity.Category;
import com.library.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository repository;



    @GetMapping
    public List<Category> getAll() {

        return repository.findAll();
    }

    @PostMapping
    public Category create(
            @RequestBody Category category) {

        if(repository.existsByName(
                category.getName())){

            throw new RuntimeException(
                    "Category already exists");
        }

        return repository.save(category);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id){

        repository.deleteById(id);
    }
    @PutMapping("/{id}")
    public Category update(
            @PathVariable Long id,
            @RequestBody Category request){

        Category category =
                repository.findById(id)
                        .orElseThrow();

        category.setName(
                request.getName());

        return repository.save(category);
    }
}
