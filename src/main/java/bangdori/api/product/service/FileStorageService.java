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


    @Value("${path.image}") String path;
    String userHome = System.getProperty("user.home");

    private String getFilePath (){
        String rltPath ="";

        String profile = System.getProperty("spring.profiles.active");
        if ("prod".equals(profile)) {
            rltPath = path + "/운영경로정해지면";  // 프로덕션 환경에서 이미지 경로
        } else {

            rltPath = userHome +  path;  // 로컬 환경에서 이미지 경로
        }
        return rltPath;
    }

    public String saveFile(MultipartFile file) {

        try {

            String path = getFilePath();
            Path directoryPath = Paths.get(path);
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
