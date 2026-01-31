package utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Author: WanBi
 * Email : anhnbt.it@gmail.com
 */
public class StringCommon {
    public static String formatNumber(int quantity) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        String formattedQuantity = nf.format(quantity);
        return formattedQuantity;
    }

    public static String formatCurrency(double price) {
        DecimalFormat df = new DecimalFormat("#,###");
        String priceVND = df.format(price) + " ₫";
        return priceVND;
    }
}
