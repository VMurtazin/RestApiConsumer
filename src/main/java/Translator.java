import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Translator {

    public static void main(String[] args) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("Введите на русском");
        Scanner scanner = new Scanner(System.in);
        String rusString = scanner.nextLine();
        String url = "https://translate.api.cloud.yandex.net/translate/v2/translate";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String token=
                "t1.9euelZqMjpPIzZiTiZbNnJaSxo2dl-3rnpWazZOaxo6Jjs3Kzo_JjJmdi5zl9PdZJxxe-e8sEwKE3fT3GVYZXvnvLBMChA.34-3DgBCJCjTLM5H31KvDCy05fKyzm6cL_Ie-myyoamyuNoyVM8Ki6lpFbrwtbj3pMRPjxWFk_Fyjnefk9ylDg";
        headers.add("Authorization", "Bearer " + token);

        Map<String, String> jsonData = new HashMap<>();
        jsonData.put("folderId", "b1gr2v42o09shk09rt8m");
        jsonData.put("targetLanguageCode", "en");
        jsonData.put("texts", "[" + rusString + "]");
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(jsonData, headers);
        YandexResponse response = restTemplate.postForObject(url, entity, YandexResponse.class);

        assert response != null;

        System.out.println("Перевод:" + response.getTranslations().get(0).getText().replaceAll("\\[","")
                .replaceAll("\\]",""));
    }
}
