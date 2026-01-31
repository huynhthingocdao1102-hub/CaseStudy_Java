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

    // Constructor không tham số
    public Shoe() {
    }

    // Constructor
    public Shoe(String id, String name, Brand brand, double price, int stock) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
    }

    // GETTER
    public String getId() { return id; }
    public String getName() { return name; }
    public Brand getBrand() { return brand; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    // SETTER
    public void setName(String name) { this.name = name; }
    public void setBrand(Brand brand) { this.brand = brand; }
    public void setPrice(double price) { this.price = price; }
    public void setStock(int stock) { this.stock = stock; }

    // BUSINESS
    public void decreaseStock(int qty) {
        stock -= qty;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + brand +
                " | " + StringCommon.formatCurrency(price) + " | Stock: " + StringCommon.formatNumber(stock);
    }
}
