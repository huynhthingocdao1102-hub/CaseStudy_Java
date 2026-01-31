package service;

import exception.OutOfStockException;
import model.Brand;
import model.Shoe;
import utils.FileUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ShoeService {

    private List<Shoe> shoes;
    private final String FILE = "shoes.dat";
    private final String CSV_FILE = "shoes.csv";

    // ================= CONSTRUCTOR =================
    public ShoeService() {
        shoes = FileUtils.read(FILE);

        if (shoes == null) {
            shoes = new ArrayList<>();
            loadFromCSV();
        }

        sortById();
    }

    // ================= LOAD CSV =================
    private void loadFromCSV() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line;
            br.readLine(); // bỏ header

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                String id = parts[0].trim();
                String name = parts[1].trim();
                Brand brand = Brand.valueOf(parts[2].trim().toUpperCase());
                double price = Double.parseDouble(parts[3].trim());
                int stock = Integer.parseInt(parts[4].trim());
                int size = Integer.parseInt(parts[5].trim());
                String origin = parts[6].trim();

                shoes.add(new Shoe(id, name, brand, price, stock, size, origin));
            }

        } catch (Exception e) {
            System.out.println("❌ Lỗi đọc CSV, dùng dữ liệu mặc định!");

            shoes.add(new Shoe("S01", "Air Force 1", Brand.NIKE, 2500000, 15, 42, "Vietnam"));
            shoes.add(new Shoe("S02", "Air Jordan 1", Brand.NIKE, 4200000, 8, 43, "China"));
            shoes.add(new Shoe("S03", "Ultraboost", Brand.ADIDAS, 3200000, 10, 41, "Vietnam"));
            shoes.add(new Shoe("S04", "Stan Smith", Brand.ADIDAS, 2100000, 20, 40, "Indonesia"));
        }
    }

    // ================= HIỂN THỊ =================
    public void showAllById() {
        sortById();
        System.out.println("📋 DANH SÁCH GIÀY THEO ID");

        for (Shoe s : shoes) {
            System.out.println(s);
        }
    }

    public void showAllByPrice() {
        sortByPrice();
        System.out.println("📋 DANH SÁCH GIÀY THEO GIÁ");

        for (Shoe s : shoes) {
            System.out.println(s);
        }
    }

    // ================= TÌM KIẾM =================
    public Shoe findById(String id) {
        for (Shoe s : shoes) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    public void findByBrand(String brandInput) {
        boolean found = false;

        for (Shoe s : shoes) {
            if (s.getBrand().name().equalsIgnoreCase(brandInput)) {
                System.out.println(s);
                found = true;
            }
        }

        if (!found) {
            System.out.println("❌ Không tìm thấy giày theo hãng!");
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
            System.out.println("⚠️ Không có giày sắp hết hàng!");
        }
    }

    // ================= THÊM - SỬA - XÓA =================
    public boolean add(Shoe shoe) {
        if (findById(shoe.getId()) != null) {
            return false;
        }
        shoes.add(shoe);
        return true;
    }

    public boolean updateShoe(String id, String name, Brand brand,
                              double price, int stock, int size, String origin) {
        Shoe s = findById(id);
        if (s == null) return false;

        s.setName(name);
        s.setBrand(brand);
        s.setPrice(price);
        s.setStock(stock);
        s.setSize(size);
        s.setOrigin(origin);

        return true;
    }

    public boolean deleteShoe(String id) {
        Shoe s = findById(id);
        if (s == null) return false;

        shoes.remove(s);
        return true;
    }

    // ================= BÁN GIÀY =================
    public void sell(String id, int qty) throws OutOfStockException {
        Shoe s = findById(id);

        if (s == null)
            throw new OutOfStockException("❌ Không tìm thấy giày!");

        if (s.getStock() < qty)
            throw new OutOfStockException("❌ Không đủ hàng!");

        s.decreaseStock(qty);
    }

    // ================= SẮP XẾP (COMPARATOR CƠ BẢN) =================
    public void sortByPrice() {
        Collections.sort(shoes, new Comparator<Shoe>() {
            @Override
            public int compare(Shoe s1, Shoe s2) {
                return Double.compare(s1.getPrice(), s2.getPrice());
            }
        });
    }

    public void sortById() {
        Collections.sort(shoes, new Comparator<Shoe>() {
            @Override
            public int compare(Shoe s1, Shoe s2) {
                return s1.getId().compareTo(s2.getId());
            }
        });
    }

    // ================= LƯU FILE =================
    public void save() {
        FileUtils.write(FILE, shoes);
    }

    public List<Shoe> getShoes() {
        return shoes;
    }
}
