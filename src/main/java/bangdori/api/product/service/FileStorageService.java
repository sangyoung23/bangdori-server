package bangdori.api.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageService {


    String userHome = System.getProperty("user.home");

    private final String uploadDir = userHome + "/Desktop/3tierDocker/backend/src/main/resources/static";

    public String saveFile(MultipartFile file) {

        try {

            Path directoryPath = Paths.get(uploadDir);
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath); // 경로가 없으면 생성
            }
            String fileNmForExt = file.getOriginalFilename();
            String ext = fileNmForExt.substring(fileNmForExt.lastIndexOf(".") + 1);
            String fileName = UUID.randomUUID().toString() + "."+ext;

            // 파일 저장
            Path targetLocation = directoryPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;

        } catch (IOException ex) {
            throw new RuntimeException("파일 저장 실패: " + file.getOriginalFilename(), ex);
        }
    }
}