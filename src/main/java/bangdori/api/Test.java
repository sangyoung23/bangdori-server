package bangdori.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Test {
    @GetMapping("/test")
    public ResponseEntity<String> getYourData() {
        return ResponseEntity.ok("Some data");
    }

}

