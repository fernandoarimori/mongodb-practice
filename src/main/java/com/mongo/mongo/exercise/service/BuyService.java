package com.mongo.mongo.exercise.service;

import com.mongo.mongo.exercise.model.Buy;
import com.mongo.mongo.exercise.model.Item;
import com.mongo.mongo.exercise.model.dto.BuyPostDTO;
import com.mongo.mongo.exercise.model.dto.BuyResponseDTO;
import com.mongo.mongo.exercise.repository.BuyRepository;
import com.mongo.mongo.exercise.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class BuyService {
    public final BuyRepository buyRepository;
    public final ItemRepository itemRepository;

    public ResponseEntity postBuy(BuyPostDTO buyPostDTO, UriComponentsBuilder builder) {
        Item itemToPost = new Item(buyPostDTO.item(), idGeneratorToBuy(buyRepository));
        itemToPost.setId(idGeneratorToItem(itemRepository));
        Item savedItem = itemRepository.save(itemToPost);
        Buy toPost = new Buy(buyPostDTO, savedItem);
        toPost.setId(idGeneratorToBuy(buyRepository));
        Buy savedOne = buyRepository.save(toPost);
        savedItem.setBuyId(savedOne.getId());
        itemRepository.save(savedItem);
        URI uri = builder.path("/buy/{id}").buildAndExpand(savedOne.getId()).toUri();
        return ResponseEntity.created(uri).body(new BuyResponseDTO(savedOne));
    }

    public ResponseEntity<Page<BuyResponseDTO>> findAll(Pageable pageable) {
        var allfound = buyRepository.findAll(pageable).map(BuyResponseDTO::new);
        return ResponseEntity.ok(allfound);
    }

    public ResponseEntity findById(Long id) {
        Buy foundOne = buyRepository.findById(id).get();
        return ResponseEntity.ok().body(new BuyResponseDTO(foundOne));
    }

    public ResponseEntity updateBuy(Long id, BuyPostDTO dto) {
        System.out.println(dto.toString());
        Buy foundOne = buyRepository.findById(id).get();
        Item foundOneItem = itemRepository.findById(foundOne.getItem().getId()).get();
        System.out.println(foundOneItem.getPriceItem());
        foundOne.update(dto, foundOneItem);
        foundOneItem.update(dto.item());
        buyRepository.save(foundOne);
        itemRepository.save(foundOne.getItem());
        return ResponseEntity.ok().body(new BuyResponseDTO(foundOne));
    }

    public ResponseEntity delete(Long id) {
        Buy toDelete = buyRepository.findById(id).get();
        itemRepository.delete(itemRepository.findByBuyId(toDelete.getId()));
        buyRepository.delete(toDelete);
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
                ⠀⠀⠀⠀⠀⠀⠻⢿⡋⠉⠀⠀⠀⠀⠀⠀⠀⠀⠉⣻⡿⠿⠂ BOOM!!⠀⠀⠀⠀⠀
                """);
    }


    //id sequence generator (gambiarra)
    public static Long idGeneratorToBuy(BuyRepository buyRepository) {
        return Long.valueOf(buyRepository.count() + 1);
    }

    public static Long idGeneratorToItem(ItemRepository itemRepository) {
        return Long.valueOf(itemRepository.count() + 1);
    }

}
