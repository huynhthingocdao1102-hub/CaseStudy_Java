package utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class StringCommon {

    public static String formatNumber(int quantity) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        return nf.format(quantity);
    }

    public static String formatCurrency(double price) {
        DecimalFormat df = new DecimalFormat("#,###");
        return df.format(price) + " ₫";
    }
}
