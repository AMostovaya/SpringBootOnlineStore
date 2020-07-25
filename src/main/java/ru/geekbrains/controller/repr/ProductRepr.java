package ru.geekbrains.controller.repr;


import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.model.Brand;
import ru.geekbrains.model.Category;
import ru.geekbrains.model.Picture;
import ru.geekbrains.model.Product;
import ru.geekbrains.repo.PictureRepository;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ProductRepr implements Serializable {

    private Long id;
    private String name;
    private Double price;
    private Category categoryName;
    private Brand brandName;
    private List<Long> pictureIds;

    public ProductRepr() {
    }

    public ProductRepr(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.price = product.getPrice();
        this.categoryName = product.getCategory();
        this.brandName = product.getBrand();
        this.pictureIds = product.getPictures().stream()
                .map(Picture::getId)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(Category categoryName) {
        this.categoryName = categoryName;
    }

    public Brand getBrandName() {
        return brandName;
    }

    public void setBrandName(Brand brandName) {
        this.brandName = brandName;
    }

    public List<Long> getPictureIds() {
        return pictureIds;
    }

    public void setPictureIds(List<Long> pictureIds) {
        this.pictureIds = pictureIds;
    }
}
