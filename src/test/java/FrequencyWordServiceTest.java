import kp.upload.FrequencyWordService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockPart;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({MockitoExtension.class})
public class FrequencyWordServiceTest {
    @InjectMocks
    private FrequencyWordService frequencyWordService;
    @Test
    void shouldReturnListWithFrequencyEqualToOne() throws IOException {
        MockHttpServletRequest request = createHttpRequestMockForFile("oneWordFile.txt");
        Map<String, Integer> responseMap = frequencyWordService.countWordsFrequencyInFile(request);
        assertEquals(1, responseMap.size());
        assertEquals(1, responseMap.get("one"));
    }
    @Test
    void shouldReturnEmptyMapForEmptyFile() throws IOException {
        MockHttpServletRequest request = createHttpRequestMockForFile("emptyFile.txt");
        Map<String, Integer> responseMap = frequencyWordService.countWordsFrequencyInFile(request);
        assertEquals(0, responseMap.size());
    }

    @Test
    void shouldReturnEmptyMapForTrimmedFile() throws IOException {
        MockHttpServletRequest request = createHttpRequestMockForFile("fileToTrim.txt");
        Map<String, Integer> responseMap = frequencyWordService.countWordsFrequencyInFile(request);
        assertEquals(0, responseMap.size());
    }

    @Test
    void shouldNotSplitPolishWordFromFile() throws IOException {
        MockHttpServletRequest request = createHttpRequestMockForFile("polishWord.txt");
        Map<String, Integer> responseMap = frequencyWordService.countWordsFrequencyInFile(request);
        assertEquals(1, responseMap.size());
    }

    @Test
    void shouldCalculateTheSameWord() throws IOException {
        MockHttpServletRequest request = createHttpRequestMockForFile("multilineSameWord.txt");
        Map<String, Integer> responseMap = frequencyWordService.countWordsFrequencyInFile(request);
        assertEquals(1, responseMap.size());
    }

    @Test
    void shouldThrowException() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        assertThrows(NullPointerException.class, () ->  frequencyWordService.countWordsFrequencyInFile(request));
    }

    private MockHttpServletRequest createHttpRequestMockForFile(String file) throws IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setMethod("POST");
        request.setContentType("multipart/form-data");
        MockPart part = new MockPart("file", Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(file).readAllBytes()));
        request.addPart(part);
        return request;
    }
}
