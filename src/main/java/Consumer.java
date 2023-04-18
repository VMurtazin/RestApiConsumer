import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class Consumer {

    public static void main(String[] args) {
        RestTemplate restTemplate=new RestTemplate();
        Map<String,String> jsonSend=new HashMap<>();
        HttpEntity<Map<String,String>>request=new HttpEntity<>(jsonSend);

        jsonSend.put("name","Kolya");
        jsonSend.put("job","master");
        String urlput="https://reqres.in/api/users";
        String respPut=restTemplate.postForObject(urlput,request,String.class);
        System.out.println(respPut);


//        String url="https://reqres.in/api/users/2";
//        String response=restTemplate.getForObject(url,String.class);
//        System.out.println(response);
    }
}
