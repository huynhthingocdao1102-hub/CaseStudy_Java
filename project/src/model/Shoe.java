package model;


import utils.StringCommon;

import java.io.Serializable;
import java.text.DecimalFormat;

public class Shoe implements Serializable {

    private String id;
    private String name;
    private Brand brand;
    private double price;
    private int stock;
    private int size;        // size giày
    private String origin;   // xuất xứ

    // Constructor không tham số
    public Shoe() {
    }

    // Constructor đầy đủ
    public Shoe(String id, String name, Brand brand, double price, int stock, int size, String origin) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
        this.size = size;
        this.origin = origin;
    }

    // GETTER
    public String getId() { return id; }
    public String getName() { return name; }
    public Brand getBrand() { return brand; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    public int getSize() { return size; }
    public String getOrigin() { return origin; }

    // SETTER
    public void setName(String name) { this.name = name; }
    public void setBrand(Brand brand) { this.brand = brand; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }
    public void setSize(int size) { this.size = size; }
    public void setOrigin(String origin) { this.origin = origin; }

    // BUSINESS
    public void decreaseStock(int qty) {
        stock -= qty;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + brand +
                " | Size: " + size +
                " | Origin: " + origin +
                " | " + StringCommon.formatCurrency(price) +
                " | Stock: " + StringCommon.formatNumber(stock);
    }
}
