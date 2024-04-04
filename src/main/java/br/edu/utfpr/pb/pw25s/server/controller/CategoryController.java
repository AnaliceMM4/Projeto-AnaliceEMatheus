package br.edu.utfpr.pb.pw25s.server.controller;

import br.edu.utfpr.pb.pw25s.server.model.Category;
import br.edu.utfpr.pb.pw25s.server.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> create(
            @RequestBody @Valid Category category) {
        // category.setName("TESTE");
        categoryService.save(category);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(location).body(category);
    }

    //http://localhost:8025/categories/1
    //http://localhost:8025/categories?id=1

//    @GetMapping("{id}")
//    public ResponseEntity<?> findOne(@PathVariable Long id) {
////        return ResponseEntity.ok(categoryService.findOne(id));
//
//        Category category = categoryService.findOne(id);
//        if (category != null) {
//            return ResponseEntity.ok(category);
//        } else {
//            return (ResponseEntity<?>) ResponseEntity.noContent();
//        }
//    }
// http://localhost:8025/categories

    @GetMapping
    public ResponseEntity<List<Category>> findAll(){
        return ResponseEntity.ok(categoryService.findAll());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        categoryService.delete(id);
    }
}
