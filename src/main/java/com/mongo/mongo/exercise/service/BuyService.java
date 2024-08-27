package com.mongo.mongo.exercise.service;

import com.mongo.mongo.exercise.model.Buy;
import com.mongo.mongo.exercise.model.Item;
import com.mongo.mongo.exercise.model.dto.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuyService {
    public final BuyRepository buyRepository;
    public final ItemRepository itemRepository;

    public ResponseEntity postBuy(BuyPostDTO buyPostDTO, UriComponentsBuilder builder) {
        Long idBuy =idGeneratorToBuy(buyRepository);
        Buy toPost = new Buy(buyPostDTO);
        toPost.setId(idBuy);
        Buy savedOne = buyRepository.save(toPost);
        List<Item> itemToSave = new ArrayList<>();
        buyPostDTO.item().forEach(
                item->{
                    Long idItem = idGeneratorToItem(itemRepository);
                    Item itemPost = new Item(item);
                    itemPost.setId(idItem);
                    itemPost.setBuyId(savedOne.getId());
                    itemRepository.save(itemPost);
                    itemToSave.add(itemPost);
                }
        );
        savedOne.setItem(itemToSave);
        Buy response = buyRepository.save(savedOne);
        System.out.println(response.getId());
        URI uri = builder.path("/buy/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity.created(uri).body(new BuyResponseDTO(response));
    }

    public ResponseEntity<Page<BuyResponseDTO>> findAll(Pageable pageable) {
        var allfound = buyRepository.findAll(pageable).map(BuyResponseDTO::new);
        return ResponseEntity.ok(allfound);
    }

    public ResponseEntity findById(Long id) {
        Buy foundOne = buyRepository.findById(id).get();
        return ResponseEntity.ok().body(new BuyResponseDTO(foundOne));
    }

    public ResponseEntity updateBuy(Long id, BuyEditDTO dto) {
        Buy foundOne = buyRepository.findById(id).get();
        List<Item> foundItens = new ArrayList<>();
        List<Item> allItens = itemRepository.findAll();
        allItens.forEach(
                item->{
                    if(item.getBuyId()==foundOne.getId()){
                        foundItens.add(item);
                        System.out.println(item.getName());
                    }
                }
        );
        List<ItemUpdateDTO> listItemDTO = dto.item();
        List<Item> updatedOne = new ArrayList<>();
        foundItens.forEach(
                item -> {
                    Item foundItem = itemRepository.findById(item.getId()).get();
                    allItens.forEach(
                            x ->
                            {
                                if (x.getBuyId() == foundOne.getId()) {
                                    itemRepository.deleteById(x.getId());
                                }
                            }
                    );
                    listItemDTO.forEach(
                            dtoUpdate->{
                                foundItem.update(dtoUpdate);
                                foundItem.setBuyId(foundItem.getBuyId());
                                itemRepository.save(foundItem);
                            }
                    );
                }
        );
        listItemDTO.forEach(
                item->{
                    Item foundItemToList = itemRepository.findByBuyId(foundOne.getId());
                    updatedOne.add(foundItemToList);
                }
        );
        foundOne.update(dto, updatedOne);
        buyRepository.save(foundOne);
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
