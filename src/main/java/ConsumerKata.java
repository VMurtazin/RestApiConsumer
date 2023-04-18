
//Для работы предоставляется API по URL - http://94.198.50.185:7081/api/users
//
//        Ваша задача: Последовательно выполнить следующие операции и получить код для проверки на платформе:
//
//        Получить список всех пользователей
//        Когда вы получите ответ на свой первый запрос, вы должны сохранить свой session id, который получен через cookie. Вы получите его в заголовке ответа set-cookie. Поскольку все действия происходят в рамках одной сессии, все дальнейшие запросы должны использовать полученный session id ( необходимо использовать заголовок в последующих запросах )
//        Сохранить пользователя с id = 3, name = James, lastName = Brown, age = на ваш выбор. В случае успеха вы получите первую часть кода.
//        Изменить пользователя с id = 3. Необходимо поменять name на Thomas, а lastName на Shelby. В случае успеха вы получите еще одну часть кода.
//        Удалить пользователя с id = 3. В случае успеха вы получите последнюю часть кода.
//        В результате выполненных операций вы должны получить итоговый код, сконкатенировав все его части. Количество символов в коде = 18.
//
//
//        Необходимые данные:
//
//        Модель пользователя:
//
//public class User {
//    private Long id;
//    private String name;
//    private String lastName;
//    private Byte age;
//       ...
//}
//
//
//    Список URL для операций и типы запросов:
//
//        Получение всех пользователей - …/api/users ( GET )
//        Добавление пользователя - …/api/users ( POST )
//        Изменение пользователя - …/api/users ( PUT )
//        Удаление пользователя - …/api/users /{id} ( DELETE )
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class ConsumerKata {

    public static void main(String[] args) {
        RestTemplate restTemplate=new RestTemplate();
        String url="http://94.198.50.185:7081/api/users";

        ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);

        String responseHeader = response.getHeaders().getFirst("set-cookie");
        String body = response.getBody();
        String sessionID=responseHeader.substring(0,43);

        System.out.println(sessionID);
//        System.out.println(responseHeader);
        System.out.println(body);

        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Cookie",sessionID);


        Map<String, String> jsonData = new HashMap<>();
        jsonData.put("id", "3");
        jsonData.put("name", "James");
        jsonData.put("lastName", "Brown");
        jsonData.put("age", "33");


        HttpEntity<Map<String, String>> entity2 = new HttpEntity<>(jsonData, headers);
        ResponseEntity<String> response2 = restTemplate.postForEntity(url,entity2, String.class);
        String body2 = response2.getBody();
        System.out.println(body2);



        User PatchUser=new User(3L,"Thomas","Shelby", (byte) 35);


        HttpEntity<User> entity3 = new HttpEntity<>(PatchUser, headers);

        System.out.println(restTemplate.exchange(url, HttpMethod.PUT, entity3, String.class));

        HttpEntity<User> entity4 = new HttpEntity<>(null, headers);

        System.out.println(restTemplate.exchange(url+"/3",HttpMethod.DELETE,entity4,String.class));












//        HttpHeaders headers=new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);

//        String result=restTemplate.getForObject(url,String.class);
//        System.out.println(result);
    }
}
