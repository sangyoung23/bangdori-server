package bangdori.api.comm;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/ContentItem")
public class ImageServlet extends HttpServlet {

    @Value("${path.image}") String path;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String imagePath = request.getParameter("image"); // image 파라미터 값 가져오기
        String filePath = path +"/"+ imagePath; // 실제 파일 경로로 조정
        File imageFile = new File(filePath);
        if (imageFile.exists()) {
            // 이미지 MIME 타입 설정
            String mimeType = getServletContext().getMimeType(filePath);
            if (mimeType == null) {
                mimeType = "application/octet-stream"; // 기본 MIME 타입
            }
            response.setContentType(mimeType);

            // 파일을 읽어 응답으로 전송
            try (FileInputStream fis = new FileInputStream(imageFile);
                 OutputStream os = response.getOutputStream()) {
                byte[] buffer = new byte[16384];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Image not found");
        }
    }
}
