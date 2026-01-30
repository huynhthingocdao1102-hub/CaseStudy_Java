package model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Invoice implements Serializable {

    private String shoeName;
    private int quantity;
    private double total;
    private LocalDateTime date;

    public Invoice(String shoeName, int quantity, double total) {
        this.shoeName = shoeName;
        this.quantity = quantity;
        this.total = total;
        this.date = LocalDateTime.now();
    }

    // GETTER
    public String getShoeName() {
        return shoeName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal() {
        return total;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public String toString() {
        // Format tiền VND
        DecimalFormat df = new DecimalFormat("#,###");
        String totalVND = df.format(total) + " ₫";

        // Format ngày giờ
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String time = date.format(formatter);

        return "🧾 HÓA ĐƠN | Giày: " + shoeName +
                " | SL: " + quantity +
                " | Tổng: " + totalVND +
                " | Ngày: " + time;
    }
}
