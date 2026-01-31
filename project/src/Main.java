import exception.OutOfStockException;
import model.Brand;
import model.Invoice;
import model.Shoe;
import service.InvoiceService;
import service.ShoeService;
import utils.InputUtils;

public class Main {

    public static void main(String[] args) {
        ShoeService shoeService = new ShoeService();
        InvoiceService invoiceService = new InvoiceService();

        while (true) {
            showMenu();

            int choice = InputUtils.readInt("👉 Nhập lựa chọn của bạn: ");

            switch (choice) {
                case 0:
                    shoeService.save();
                    invoiceService.save();
                    System.out.println("\n👋 Cảm ơn đã sử dụng PUSH SNEAKERS!");
                    return;

                case 1:
                    shoeService.showAllById(); // hiển thị theo ID
                    break;

                case 2:
                    sellShoe(shoeService, invoiceService);
                    break;

                case 3:
                    System.out.println("\n🔽 SẮP XẾP GIÀY THEO GIÁ");
                    shoeService.sortByPrice();     // sắp xếp theo giá
                    shoeService.showAllByPrice();  // hiển thị theo giá
                    break;

                case 4:
                    addNewShoe(shoeService);
                    break;

                case 5:
                    System.out.println("\n🔎 TÌM GIÀY THEO HÃNG");
                    String brand = InputUtils.readName("Nhập tên hãng: ");
                    shoeService.findByBrand(brand);
                    break;

                case 6:
                    System.out.println("\n⚠️ GIÀY SẮP HẾT HÀNG");
                    shoeService.lowStockShoes();
                    break;

                case 7:
                    updateShoe(shoeService);
                    break;

                case 8:
                    deleteShoe(shoeService);
                    break;

                case 9:
                    invoiceService.exportToCSV();
                    break;

                default:
                    System.out.println("❌ Lựa chọn không hợp lệ! Vui lòng chọn lại.");
            }

            System.out.println("\n⏎ Nhấn Enter để tiếp tục...");
            new java.util.Scanner(System.in).nextLine();

        }
    }

    //MENU
    private static void showMenu() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║             PUSH SNEAKERS            ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  1. Danh sách giày                   ║");
        System.out.println("║  2. Bán giày                         ║");
        System.out.println("║  3. Sắp xếp giá                      ║");
        System.out.println("║  4. Thêm giày                        ║");
        System.out.println("║  5. Tìm theo hãng                    ║");
        System.out.println("║  6. Sắp hết hàng                     ║");
        System.out.println("║  7. Sửa giày                         ║");
        System.out.println("║  8. Xóa giày                         ║");
        System.out.println("║  9. Xuất hóa đơn ra Excel            ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║  0. Thoát                            ║");
        System.out.println("╚══════════════════════════════════════╝");
    }

    //BÁN GIÀY
    private static void sellShoe(ShoeService shoeService,
                                 InvoiceService invoiceService) {
        shoeService.showAllById();

        String id = InputUtils.readShoeId("\n🔎 Nhập mã giày (Ví dụ: S01): ");
        int qty = InputUtils.readInt("📦 Số lượng: ");

        try {
            Shoe shoe = shoeService.findById(id);
            shoeService.sell(id, qty);

            Invoice invoice = new Invoice(
                    shoe.getName(),
                    qty,
                    qty * shoe.getPrice()
            );

            invoiceService.add(invoice);
            System.out.println("✅ Bán giày thành công!");

        } catch (OutOfStockException e) {
            System.out.println("❌ " + e.getMessage());
        }
    }

    //THÊM GIÀY
    private static void addNewShoe(ShoeService shoeService) {
        System.out.println("\n➕ THÊM GIÀY MỚI");

        String id = InputUtils.readShoeId("Mã giày (Sxx): ");
        String name = InputUtils.readName("Tên giày: ");

        String brandInput = InputUtils.readName("Hãng (NIKE / ADIDAS / PUMA): ");
        if (!brandInput.equals("NIKE") && !brandInput.equals("ADIDAS") &&
                !brandInput.equals("PUMA")) {
            System.out.println("❌ Hãng không hợp lệ! Vui lòng nhập lại.");
            return;
        }
        Brand brand = Brand.valueOf(brandInput.trim().toUpperCase());

        double price = InputUtils.readDouble("Giá: ");
        int stock = InputUtils.readInt("Tồn kho: ");

        shoeService.add(new Shoe(id, name, brand, price, stock));
        System.out.println("✅ Thêm giày thành công!");
    }

    //SỬA GIÀY
    private static void updateShoe(ShoeService shoeService) {
        System.out.println("\n✏️ SỬA GIÀY");

        shoeService.showAllById();

        String id = InputUtils.readShoeId("Nhập mã giày cần sửa: ");

        String name = InputUtils.readName("Tên mới: ");
        String brandInput = InputUtils.readName("Hãng mới (NIKE / ADIDAS / PUMA / CONVERSE / VANS / NEW_BALANCE): ");
        Brand brand = Brand.valueOf(brandInput.trim().toUpperCase());
        double price = InputUtils.readDouble("Giá mới: ");
        int stock = InputUtils.readInt("Tồn kho mới: ");

        boolean result = shoeService.updateShoe(id, name, brand, price, stock);

        if (result) {
            System.out.println("✅ Sửa giày thành công!");
        } else {
            System.out.println("❌ Không tìm thấy giày!");
        }
    }

    //XÓA GIÀY
    private static void deleteShoe(ShoeService shoeService) {
        System.out.println("\n🗑️ XÓA GIÀY");

        shoeService.showAllById();

        String id = InputUtils.readShoeId("Nhập mã giày cần xóa: ");

        boolean result = shoeService.deleteShoe(id);

        if (result) {
            System.out.println("✅ Xóa giày thành công!");
        } else {
            System.out.println("❌ Không tìm thấy giày!");
        }
    }
}
