package service;

import exception.OutOfStockException;
import model.Brand;
import model.Shoe;
import utils.FileUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class ShoeService {

    private List<Shoe> shoes;
    private final String FILE = "shoes.dat";
    private final String CSV_FILE = "shoes.csv";

    // Constructor
    public ShoeService() {
        shoes = FileUtils.read(FILE);

        if (shoes == null) {
            shoes = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
                String line;
                br.readLine(); // bỏ header
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 5) {
                        String id = parts[0].trim();
                        String name = parts[1].trim();
                        Brand brand = Brand.valueOf(parts[2].trim().toUpperCase());
                        double price = Double.parseDouble(parts[3].trim());
                        int stock = Integer.parseInt(parts[4].trim());

                        shoes.add(new Shoe(id, name, brand, price, stock));
                    }
                }
            } catch (Exception e) {
                System.out.println("❌ Lỗi đọc CSV, dùng dữ liệu mặc định!");

                shoes.add(new Shoe("S01", "Air Force 1", Brand.NIKE, 2500000, 15));
                shoes.add(new Shoe("S02", "Air Jordan 1", Brand.NIKE, 4200000, 8));
                shoes.add(new Shoe("S03", "Ultraboost", Brand.ADIDAS, 3200000, 10));
                shoes.add(new Shoe("S04", "Stan Smith", Brand.ADIDAS, 2100000, 20));
                shoes.add(new Shoe("S05", "RS-X", Brand.PUMA, 2300000, 14));
                shoes.add(new Shoe("S06", "Chuck 70", Brand.CONVERSE, 1800000, 30));
                shoes.add(new Shoe("S07", "Old Skool", Brand.VANS, 1700000, 22));
                shoes.add(new Shoe("S08", "NB 574", Brand.NEW_BALANCE, 2400000, 13));
            }
        }

        sortById();
    }

    //HIỂN THỊ

    public void showAllById() {
        sortById();
        System.out.println("📋 DANH SÁCH GIÀY THEO ID");
        shoes.forEach(System.out::println);
    }

    public void showAllByPrice() {
        System.out.println("📋 DANH SÁCH GIÀY THEO GIÁ");
        shoes.forEach(System.out::println);
    }

    //TÌM KIẾM

    public Shoe findById(String id) {
        for (Shoe s : shoes) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    public void findByBrand(String input) {
        input = input.trim().toUpperCase();
        boolean found = false;

        for (Shoe s : shoes) {
            if (s.getBrand().name().equals(input)) {
                System.out.println(s);
                found = true;
            }
        }

        if (!found) {
            System.out.println("❌ Không tìm thấy giày theo hãng này!");
        }
    }

    public void lowStockShoes() {
        boolean found = false;

        for (Shoe s : shoes) {
            if (s.getStock() <= 3) {
                System.out.println(s);
                found = true;
            }
        }

        if (!found) {
            System.out.println("⚠️ Không có giày nào sắp hết hàng!");
        }
    }

    //QUẢN LÝ

    // Thêm giày
    public boolean add(Shoe shoe) {
        if (findById(shoe.getId()) != null) {
            return false; // trùng ID
        }
        shoes.add(shoe);
        sortById();
        return true;
    }

    public void sell(String id, int qty) throws OutOfStockException {
        Shoe s = findById(id);

        if (s == null)
            throw new OutOfStockException("❌ Sai mã giày!");

        if (s.getStock() < qty)
            throw new OutOfStockException("❌ Không đủ hàng!");

        s.decreaseStock(qty);
    }

    //SẮP XẾP

    public void sortByPrice() {
        shoes.sort(Comparator.comparingDouble(Shoe::getPrice));
    }

    public void sortById() {
        shoes.sort(Comparator.comparingInt(s -> {
            try {
                return Integer.parseInt(s.getId().substring(1));
            } catch (Exception e) {
                return 0;
            }
        }));
    }

    //SỬA, XÓA

    public boolean updateShoe(String id, String newName, Brand newBrand, double newPrice, int newStock) {
        Shoe shoe = findById(id);
        if (shoe == null) return false;

        shoe.setName(newName);
        shoe.setBrand(newBrand);
        shoe.setPrice(newPrice);
        shoe.setStock(newStock);

        sortById();
        return true;
    }

    public boolean deleteShoe(String id) {
        Shoe shoe = findById(id);
        if (shoe == null) return false;

        shoes.remove(shoe);
        return true;
    }

    //LƯU FILE

    public void save() {
        FileUtils.write(FILE, shoes);
    }

    public List<Shoe> getShoes() {
        return shoes;
    }
}
