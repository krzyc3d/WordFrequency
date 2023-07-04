package kp.upload;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/frequency")
public class UploadController {
    private final FrequencyWordService frequencyWordService;

    public UploadController(FrequencyWordService frequencyWordService) {
        this.frequencyWordService = frequencyWordService;
    }

    @PostMapping
    public Map<String, Integer> wordFrequency(HttpServletRequest request) {
        return frequencyWordService.countWordsFrequencyInFile(request);
    }
}
