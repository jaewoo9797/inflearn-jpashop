package jpabook.jpashop.item.db;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import jpabook.jpashop.item.entity.Book;
import jpabook.jpashop.item.entity.Item;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    @Transactional
    @Rollback(false)
    void testItem() {
        // given
        Item item = new Book();
        item.setName("bookA");
        // when
        Long saveId = itemRepository.save(item);
        Item foundItem = itemRepository.findOne(saveId);
        // then
        assertThat(foundItem.getName()).isEqualTo(item.getName());
        assertThat(foundItem.getId()).isEqualTo(item.getId());
        assertThat(foundItem).isEqualTo(item);
    }
}