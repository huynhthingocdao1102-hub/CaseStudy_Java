package utils;

import java.io.*;

public class FileUtils {

    public static <T> void write(String path, T data) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(path))) {
            oos.writeObject(data);
        } catch (IOException e) {
            System.out.println("❌ Lỗi ghi file");
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T read(String path) {
        File file = new File(path);
        if (!file.exists()) return null;

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(path))) {
            return (T) ois.readObject();
        } catch (Exception e) {
            return null;
        }
    }
}
