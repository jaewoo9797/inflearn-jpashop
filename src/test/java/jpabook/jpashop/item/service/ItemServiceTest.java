package jpabook.jpashop.item.service;

import static org.assertj.core.api.Assertions.assertThat;

import jpabook.jpashop.item.db.ItemRepository;
import jpabook.jpashop.item.entity.Book;
import jpabook.jpashop.item.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Test
    void 상품등록() {
        // given
        Item item = new Book();
        item.setName("itemA");
        // when
        Long savedId = itemService.saveItem(item);
        Item foundItem = itemService.findOne(savedId);
        // then
        assertThat(foundItem.getName()).isEqualTo(item.getName());
    }
}