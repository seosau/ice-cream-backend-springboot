package com.project.icecream.utils;

import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.regex.Pattern;
@Component
public class GenerateSlugUtil {
    public static String convertToSlug(String text) {
        // Chuyển đổi các ký tự tiếng Việt sang Latin
        String slug = Normalizer.normalize(text.toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "") // Loại bỏ các dấu thanh từ các ký tự
                .replace("đ", "d") // Thay thế ký tự 'đ' với 'd'
                .replaceAll("[^a-z0-9\\s-]", "") // Loại bỏ các ký tự không phải chữ cái, số, dấu cách, hoặc dấu gạch ngang
                .replaceAll("\\s+", "-") // Thay thế dấu cách bằng dấu gạch ngang
                .replaceAll("-+", "-") // Xóa các dấu gạch ngang liên tiếp
                .trim(); // Xóa khoảng trắng ở đầu và cuối chuỗi
        return slug;
    }
}
