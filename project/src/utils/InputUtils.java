package utils;

import java.util.Scanner;

public class InputUtils {
    private static Scanner sc = new Scanner(System.in);

    // Đọc số nguyên, chống crash
    public static int readInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Vui lòng nhập số!");
            }
        }
    }

    // Đọc mã giày
    public static String readShoeId(String message) {
        while (true) {
            System.out.print(message);
            String id = sc.nextLine().trim().toUpperCase();

            if (id.matches("S\\d{2}")) {
                return id;
            }

            System.out.println("❌ Mã giày không hợp lệ (VD: S01)");
        }
    }

    public static double readDouble(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("❌ Vui lòng nhập số hợp lệ!");
            }
        }
    }

    // Đọc tên sản phẩm
    public static String readName(String msg) {
        while (true) {
            System.out.print(msg);
            String name = sc.nextLine().trim();
            if (!name.isEmpty()) return name;
            System.out.println("❌Tên không được để trống!");
        }
    }
}
