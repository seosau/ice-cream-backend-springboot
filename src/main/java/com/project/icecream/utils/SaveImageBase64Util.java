package com.project.icecream.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class SaveImageBase64Util {
    private static String getExtensionFromBase64(String base64Image) {
        String extension = "png"; // Phần mở rộng mặc định
        String[] parts = base64Image.split(";");
        if (parts.length > 0) { // Kiểm tra xem mảng parts có ít nhất một phần tử không
            String mimeType = parts[0].split(":")[1]; // Lấy dữ liệu MIME
            extension = mimeType.split("/")[1]; // Tách phần mở rộng từ dữ liệu MIME
        }
        return extension;
    }
    public static String saveBase64ImageToFile(String base64Image, String name) throws IOException {

        String extension = getExtensionFromBase64(base64Image);

        // Tách phần header nếu có (data:image/png;base64,)
        if (base64Image.contains(",")) {
            base64Image = base64Image.split(",")[1];
        }

        // Chuyển đổi base64 thành mảng byte
        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);

        String filename = name + "." + extension;
        // Đường dẫn lưu file
        String filePath = "src/main/resources/static/images/" + filename;

        // Lưu mảng byte vào file
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(decodedBytes);
        }

        return "images/" + filename;
    }
}
