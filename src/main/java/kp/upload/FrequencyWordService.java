package kp.upload;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FrequencyWordService {

    public Map<String, Integer> countWordsFrequencyInFile(HttpServletRequest request) {
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        val startTime = LocalDateTime.now();
        log.info("Processing has start{}", startTime);
        try (InputStream wordFile = getFileInputStream(request)) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(wordFile));

            bufferedReader.lines()
                    .parallel()
                    .map(fileLine -> fileLine.replaceAll("\\s+", " ").replaceAll("[^\\p{IsLatin}]", "").trim().split(" "))
                    .forEach(strings -> Arrays.asList(strings).forEach(s -> {
                        if (!Objects.equals(s, "")) {
                            map.merge(s, 1, Integer::sum);
                        }
                    }));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("Processing time: {}", Duration.between(startTime, LocalDateTime.now()).toMillis());
        return map.entrySet().stream().sorted((e1, e2) ->
                        e2.getValue().compareTo(e1.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    private InputStream getFileInputStream(HttpServletRequest request) {
        try {
            return request.getPart("file").getInputStream();
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
