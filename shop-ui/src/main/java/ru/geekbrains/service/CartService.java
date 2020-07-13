package ru.geekbrains.service;

import ru.geekbrains.controller.repr.ProductRepr;
import ru.geekbrains.service.model.LineItem;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Stream;

public interface CartService extends Serializable {

    void addProductQty(ProductRepr productRepr, String color, String material, int qty);
    void removeProductQty(ProductRepr productRepr, String color, String material, int qty);
    void removeProduct(LineItem lineItem);
    List<LineItem> getLineItems();
    Stream<Double> getSubTotal();
    void updateCart(LineItem lineItem);

}
