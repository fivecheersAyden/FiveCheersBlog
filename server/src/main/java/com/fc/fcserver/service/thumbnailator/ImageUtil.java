package com.fc.fcserver.service.thumbnailator;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

public class ImageUtil {
    public static MultipartFile compressImage(int maxSize, MultipartFile imageFile) throws IOException {
        // Check if image file needs compression
        if (imageFile.getSize() <= maxSize) {
            return imageFile; // If image file size is within limit, return original file
        }

        // Compress image
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(imageFile.getBytes());
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Thumbnails.of(inputStream)
                    .size(800, 600) // Resize image to fit within 800x600 dimensions
                    .outputQuality(0.8) // Set compression quality
                    .toOutputStream(outputStream);

            // Convert compressed image to MultipartFile
            byte[] compressedBytes = outputStream.toByteArray();
            return new MultipartFile() {
                @Override
                public String getName() {
                    return imageFile.getName();
                }

                @Override
                public String getOriginalFilename() {
                    return imageFile.getOriginalFilename();
                }

                @Override
                public String getContentType() {
                    return imageFile.getContentType();
                }

                @Override
                public boolean isEmpty() {
                    return compressedBytes.length == 0;
                }

                @Override
                public long getSize() {
                    return compressedBytes.length;
                }

                @Override
                public byte[] getBytes() throws IOException {
                    return compressedBytes;
                }

                @Override
                public InputStream getInputStream() throws IOException {
                    return new ByteArrayInputStream(compressedBytes);
                }

                @Override
                public void transferTo(File dest) throws IOException, IllegalStateException {
                    Files.write(dest.toPath(), compressedBytes);
                }
            };
        }
    }
}
