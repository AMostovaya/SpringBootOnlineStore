package ru.geekbrains.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.geekbrains.controller.repr.ProductRepr;
import ru.geekbrains.service.model.LineItem;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// тестирование корзины
public class CartServiceTest {

    private CartService cartService;

    @BeforeEach
    public void init() {
        cartService = new CartServiceImplement();
    }

    // добавление новой позиции
    @Test
    public void addOneProductTest() {
        ProductRepr productRepr = new ProductRepr();
        productRepr.setId(1l);
        productRepr.setName("Product 1");
        productRepr.setPrice(new BigDecimal(234));
        cartService.addProductQty(productRepr, "black", "metal", 1);

        List<LineItem> lineItemList = cartService.getLineItems();
        // проверка на null
        assertNotNull(lineItemList);
        // проверка добавленного количества
        assertEquals(1, lineItemList.size());
        LineItem lineItem = lineItemList.get(0);
        assertEquals("black", lineItem.getColor());
        assertEquals("metal", lineItem.getMaterial());
        assertEquals(1, lineItem.getQty());
        assertEquals(productRepr.getId(), lineItem.getProductId());
        assertNotNull(lineItem.getProductRepr());
        assertEquals(productRepr.getId(), lineItem.getProductRepr().getId());
        assertEquals(productRepr.getName(), lineItem.getProductRepr().getName());
    }

    // удаление целой позиции из корзины
    @Test
    public void deleteOneProductTest() {

        ProductRepr productRepr = new ProductRepr();
        productRepr.setId(1l);
        productRepr.setName("Product 1");
        productRepr.setPrice(new BigDecimal(234));
        cartService.addProductQty(productRepr, "black", "metal", 1);

        ProductRepr productRepr2 = new ProductRepr();
        productRepr2.setId(3l);
        productRepr2.setName("Product 3");
        productRepr2.setPrice(new BigDecimal(453));
        cartService.addProductQty(productRepr2, "white", "metal", 2);

        List<LineItem> lineItemList = cartService.getLineItems();
        // проверка на null
        assertNotNull(lineItemList);
        // проверка добавленного количества
        assertEquals(2, lineItemList.size());

        // удалим 1-й продукт
        LineItem lineItem = lineItemList.get(0);
        cartService.removeProduct(lineItem);
        lineItemList = cartService.getLineItems();
        assertEquals(1, lineItemList.size());

    }

    // удаление количества продукта из корзины
    @Test
    public void deleteQtyProduct() {
        ProductRepr productRepr = new ProductRepr();
        productRepr.setId(1l);
        productRepr.setName("Product 1");
        productRepr.setPrice(new BigDecimal(234));
        cartService.addProductQty(productRepr, "black", "metal", 2);

        ProductRepr productRepr2 = new ProductRepr();
        productRepr2.setId(3l);
        productRepr2.setName("Product 3");
        productRepr2.setPrice(new BigDecimal(453));
        cartService.addProductQty(productRepr2, "white", "metal", 4);

        List<LineItem> lineItemList = cartService.getLineItems();
        // проверка на null
        assertNotNull(lineItemList);
        // проверка добавленного количества
        assertEquals(2, lineItemList.size());
        LineItem lineItem = lineItemList.get(1);
        assertEquals(4, lineItem.getQty());
        // удаляем на одно количество
        cartService.removeProductQty(productRepr2, "white", "metal", 1);
        lineItemList = cartService.getLineItems();
        lineItem = lineItemList.get(1);
        assertEquals(3, lineItem.getQty());

    }



}
