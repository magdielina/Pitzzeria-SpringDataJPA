package com.platzi.pizza.web.controller;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import com.platzi.pizza.service.PizzaService;
import com.platzi.pizza.service.dto.UpdatePizzaPriceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {

    private final PizzaService pizzaService;

    @Autowired
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

//    @GetMapping
//    public ResponseEntity<List<PizzaEntity>> getAll() {
//        return ResponseEntity.ok(this.pizzaService.getAll());
//    }
    @GetMapping
    public ResponseEntity<Page<PizzaEntity>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int elements) {
        return ResponseEntity.ok(this.pizzaService.getAll(page, elements));
    }

    @GetMapping("/{pizzaId}")
    public ResponseEntity<PizzaEntity> get(@PathVariable int pizzaId) {
        if (this.pizzaService.exists(pizzaId)) {
            return ResponseEntity.ok(this.pizzaService.get(pizzaId));
        }
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/available")
//    public ResponseEntity<List<PizzaEntity>> getAvailable() {
//        return ResponseEntity.ok(this.pizzaService.getAvailable());
//    }
    @GetMapping("/available")
    public ResponseEntity<Page<PizzaEntity>> getAvailable(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "8") int elements,
                                                          @RequestParam(defaultValue = "price") String sortBy,
                                                          @RequestParam(defaultValue = "ASC") String sortDirection) {
        return ResponseEntity.ok(this.pizzaService.getAvailable(page, elements, sortBy, sortDirection));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PizzaEntity> getByName(@PathVariable String name) {
        return ResponseEntity.ok(this.pizzaService.getByName(name));
    }
    @GetMapping("/with/{ingredient}")
    public ResponseEntity<List<PizzaEntity>> getWith(@PathVariable String ingredient) {
        return ResponseEntity.ok(this.pizzaService.getWith(ingredient));
    }
    @GetMapping("/without/{ingredient}")
    public ResponseEntity<List<PizzaEntity>> getWithout(@PathVariable String ingredient) {
        return ResponseEntity.ok(this.pizzaService.getWithout(ingredient));
    }
    @GetMapping("/cheapest/{price}")
    public ResponseEntity<List<PizzaEntity>> getCheapestPizzas(@PathVariable double price) {
        return ResponseEntity.ok(this.pizzaService.getCheapest(price));
    }


    @PostMapping
    public ResponseEntity<PizzaEntity> add(@RequestBody PizzaEntity pizza) {
        if (pizza.getPizzaId() == null || !this.pizzaService.exists(pizza.getPizzaId())) {
            return ResponseEntity.ok(this.pizzaService.save(pizza));
        }
        return ResponseEntity.badRequest().build();
    }
    @PutMapping
    public ResponseEntity<PizzaEntity> update(@RequestBody PizzaEntity pizza) {
        if (pizza.getPizzaId() != null && this.pizzaService.exists(pizza.getPizzaId())) {
            return ResponseEntity.ok(this.pizzaService.save(pizza));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/price")
    public ResponseEntity<Void> updatePrice(@RequestBody UpdatePizzaPriceDTO dto) {
        if (this.pizzaService.exists(dto.getPizzaId())) {
            this.pizzaService.updatePrice(dto);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{pizzaId}")
    public ResponseEntity<Void> delete(@PathVariable int pizzaId) {
        if (this.pizzaService.exists(pizzaId)) {
            this.pizzaService.delete(pizzaId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }

}
