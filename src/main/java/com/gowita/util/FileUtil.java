package com.gowita.util;

import com.gowita.exception.FileException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class FileUtil {

    @Value("${value.filePath}")
    private String rootPath;

    public String save(MultipartFile file, String filePath) {
        try {
            String fileFormat = Objects.requireNonNull(file.getOriginalFilename())
                    .substring(Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf('.'));

            String fileName = getFileName();
            StringBuilder filePathBuilder = new StringBuilder()
                    .append(rootPath)
                    .append(filePath)
                    .append(fileName)
                    .append(fileFormat);
            log.info("File save process is started. filePath. filePath - {}", filePathBuilder);

            Path path = Paths.get(filePathBuilder.toString());
            byte[] bytes = file.getBytes();
            Files.write(path, bytes);

            return fileName + fileFormat;
        } catch (Exception ex) {
            log.info("File save process is failed. filePath - {}, exception :", filePath);
            throw new FileException("File save process is failed. filePath - " + filePath);
        }
    }

    public void deleteFile(String path) {
        try {
            Files.delete(Path.of(rootPath + path));
            log.info("Delete file process is finished. path -{} ", rootPath + path);
        } catch (IOException e) {
            log.error("Delete file process is failed. path -{} ", rootPath + path);
            e.printStackTrace();
        }
    }

    private static String getFileName() {
        return String.format("%s_%s",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss")),
                getRandomName());
    }

    private static String getRandomName() {
        return UUID.randomUUID().toString().substring(0, 7).replace("-", "");
    }
}
