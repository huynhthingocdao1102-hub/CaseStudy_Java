package service;

import model.Invoice;
import utils.FileUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InvoiceService {

    private List<Invoice> invoices;
    private final String FILE = "invoices.dat";
    private final String CSV_FILE = "invoices.csv";

    public InvoiceService() {
        Object data = FileUtils.read(FILE);
        if (data != null && data instanceof List) {
            invoices = (List<Invoice>) data;
        } else {
            invoices = new ArrayList<>();
        }
    }

    //Thêm hóa đơn
    public void add(Invoice invoice) {
        invoices.add(invoice);
        System.out.println(invoice);
    }

    public void showAll() {
        if (invoices.isEmpty()) {
            System.out.println("📭 Chưa có hóa đơn nào!");
            return;
        }

        System.out.println("\n📋 DANH SÁCH HÓA ĐƠN");
        invoices.forEach(System.out::println);
    }

    public void save() {
        FileUtils.write(FILE, invoices);
    }

    //Xuất hóa đơn
    public void exportToCSV() {
        try (FileWriter fw = new FileWriter(CSV_FILE)) {
            fw.write("Tên giày,Số lượng,Tổng tiền,Ngày mua\n");

            NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));

            for (Invoice i : invoices) {
                fw.write(i.getShoeName() + "," +
                        i.getQuantity() + "," +
                        nf.format(i.getTotal()) + " ₫," +
                        i.getDate() + "\n");
            }

            System.out.println("✅ Xuất hóa đơn ra file invoices.csv thành công!");
        } catch (IOException e) {
            System.out.println("❌ Lỗi xuất file CSV!");
        }
    }
}
