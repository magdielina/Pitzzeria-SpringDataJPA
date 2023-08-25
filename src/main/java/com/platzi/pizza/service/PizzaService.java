package com.platzi.pizza.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.platzi.pizza.persistence.entity.PizzaEntity;
import com.platzi.pizza.persistence.repository.PizzaPagSortRepository;
import com.platzi.pizza.persistence.repository.PizzaRepository;
import com.platzi.pizza.service.dto.UpdatePizzaPriceDTO;
import com.platzi.pizza.service.exception.EmailApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PizzaService {

//    private final JdbcTemplate jdbcTemplate;
    private final PizzaRepository pizzaRepository;
    private final PizzaPagSortRepository pizzaPagSortRepository;

    @Autowired
    public PizzaService(PizzaRepository pizzaRepository, PizzaPagSortRepository pizzaPagSortRepository) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaPagSortRepository = pizzaPagSortRepository;
    }

//    public Page<PizzaEntity> getAll() {
//        return this.jdbcTemplate.query("SELECT * FROM pizza", new BeanPropertyRowMapper<>(PizzaEntity.class));
//        return this.pizzaRepository.findAll();
//    }

    public Page<PizzaEntity> getAll(int page, int elements) {
        Pageable pageRequest = PageRequest.of(page, elements);
        return this.pizzaPagSortRepository.findAll(pageRequest);
    }


//    public List<PizzaEntity> getAvailable(){
//        System.out.printf("Vegan Pizzas " + this.pizzaRepository.countByVeganTrue());
//        return this.pizzaRepository.findAllByAvailableTrueOrderByPrice();
//    }
    public Page<PizzaEntity> getAvailable(int page, int elements, String sortBy, String sortDirection){
        Pageable pageRequest = PageRequest.of(page, elements, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
        return this.pizzaPagSortRepository.findByAvailableTrue(pageRequest);
    }

    public PizzaEntity getByName(String name){
        return this.pizzaRepository.findFirstByAvailableTrueAndNameIgnoreCase(name).orElseThrow(() -> new RuntimeException("Pizza does not exist"));
    }

    public List<PizzaEntity> getCheapest(double price){
        return this.pizzaRepository.findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(price);
    }

    public List<PizzaEntity> getWith(String ingredient){
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionContainingIgnoreCase(ingredient);
    }

    public List<PizzaEntity> getWithout(String ingredient){
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(ingredient);
    }

    public PizzaEntity get(int pizzaId) {
        return this.pizzaRepository.findById(pizzaId).orElse(null);
    }

    public PizzaEntity save(PizzaEntity pizza) {
        return this.pizzaRepository.save(pizza);
    }

    public void delete(int pizzaId) {
        this.pizzaRepository.deleteById(pizzaId);
    }

    @Transactional(noRollbackFor = EmailApiException.class)
    public void updatePrice(UpdatePizzaPriceDTO dto){
        this.pizzaRepository.updatePrice(dto);
        this.sendEmail();
    }

    public void sendEmail() {
        throw new EmailApiException();
    }

    public boolean exists(int pizzaId) {
        return this.pizzaRepository.existsById(pizzaId);
    }
}
