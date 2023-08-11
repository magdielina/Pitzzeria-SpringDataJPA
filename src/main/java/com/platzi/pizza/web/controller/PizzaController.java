package com.platzi.pizza.web.controller;

import com.platzi.pizza.persistence.entity.PizzaEntity;
import com.platzi.pizza.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public ResponseEntity<List<PizzaEntity>> getAll() {
        return ResponseEntity.ok(this.pizzaService.getAll());
    }

    @GetMapping("/{pizzaId}")
    public ResponseEntity<PizzaEntity> get(@PathVariable int pizzaId) {
        if (this.pizzaService.exists(pizzaId)) {
            return ResponseEntity.ok(this.pizzaService.get(pizzaId));
        }
        return ResponseEntity.noContent().build();
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

    @DeleteMapping("/{pizzaId}")
    public ResponseEntity<Void> delete(@PathVariable int pizzaId) {
        if (this.pizzaService.exists(pizzaId)) {
            this.pizzaService.delete(pizzaId);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.noContent().build();
    }

}
