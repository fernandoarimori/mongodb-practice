package com.mongo.mongo.exercise.controller;

import com.mongo.mongo.exercise.model.dto.BuyEditDTO;
import com.mongo.mongo.exercise.model.dto.BuyPostDTO;
import com.mongo.mongo.exercise.model.dto.BuyResponseDTO;
import com.mongo.mongo.exercise.repository.BuyRepository;
import com.mongo.mongo.exercise.repository.ItemRepository;
import com.mongo.mongo.exercise.service.BuyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/buy")
@RequiredArgsConstructor
public class BuyController {
    private final BuyService buyService;
    private final BuyRepository buyRepository;
    private final ItemRepository itemRepository;

    @PostMapping
    public ResponseEntity postBuyController(@RequestBody BuyPostDTO buyPostDTO, UriComponentsBuilder builder) {
        return buyService.postBuy(buyPostDTO, builder);
    }

    @GetMapping
    private ResponseEntity<Page<BuyResponseDTO>> findAllController(@PageableDefault(size = 20) Pageable pageable) {
        return buyService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity findByIDController(@PathVariable Long id) {
        return buyService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody BuyEditDTO buyPostDTO) {
        return buyService.updateBuy(id, buyPostDTO);
    }

    @DeleteMapping("/explode")
    public ResponseEntity deleteDatabase() {
        buyRepository.deleteAll();
        itemRepository.deleteAll();
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body("""
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⡄⠀⡼⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⣸⣿⣿⣶⡾⠟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⣰⣿⣿⣿⣅⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⣠⣴⣿⡿⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⣀⣴⣾⣿⡿⠋⠀⣠⣶⣶⠿⠿⠿⠿⠷⢶⣶⣤⣄⠀⠀⠀⠀⠀⠀⠀⠀
                ⢰⣿⣿⣿⣟⠀⠀⢸⣿⣿⣥⣤⣤⣄⣀⣀⣠⣬⣭⣿⠁⠀⠀⠀⠀⠀⠀⠀
                ⠀⠉⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣤⣤⣄⠀⠀⠀⠀
                ⠀⠀⠀⠀⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣦⣄⠀
                ⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡏⠈⠙⠿⣿⣿
                ⠀⠀⠀⠀⠀⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠃⠀⠀⠀⣀⣹
                ⠀⠀⠀⠀⠀⠘⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠏⣀⣤⣶⡿⠿⠟
                ⠀⠀⠀⠀⠀⠀⠈⠻⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⠋⠰⣿⠇⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⣀⣤⣤⡍⠉⠉⠙⠛⠛⠋⣩⣥⣤⣀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠻⢿⡋⠉⠀⠀⠀⠀⠀⠀⠀⠀⠉⣻⡿⠿⠂⠀⠀⠀⠀⠀
                """);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteEntityDatabase(@PathVariable Long id) {
        return buyService.delete(id);
    }
}

