
//Для работы предоставляется API по URL - http://94.198.50.185:7081/api/users
//
//        Ваша задача: Последовательно выполнить следующие операции и получить код для проверки на платформе:
//        Получить список всех пользователей
//        Когда вы получите ответ на свой первый запрос, вы должны сохранить свой session id, который получен через cookie. Вы получите его в заголовке ответа set-cookie. Поскольку все действия происходят в рамках одной сессии, все дальнейшие запросы должны использовать полученный session id ( необходимо использовать заголовок в последующих запросах )
//        Сохранить пользователя с id = 3, name = James, lastName = Brown, age = на ваш выбор. В случае успеха вы получите первую часть кода.
//        Изменить пользователя с id = 3. Необходимо поменять name на Thomas, а lastName на Shelby. В случае успеха вы получите еще одну часть кода.
//        Удалить пользователя с id = 3. В случае успеха вы получите последнюю часть кода.
//        В результате выполненных операций вы должны получить итоговый код, сконкатенировав все его части. Количество символов в коде = 18.
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

        System.out.println(response.getBody());
        String sessionID=responseHeader.substring(0,43);

        System.out.println(sessionID);

        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Cookie",sessionID);

        User addedUser=new User(3L,"James","Brown", (byte) 33);
        User PatchUser=new User(3L,"Thomas","Shelby", (byte) 35);


        HttpEntity<User> entity2 = new HttpEntity<>(addedUser, headers);
        String body2 = restTemplate.exchange(url,HttpMethod.POST,entity2, String.class).getBody();

        HttpEntity<User> entity3 = new HttpEntity<>(PatchUser, headers);
        String body3=restTemplate.exchange(url, HttpMethod.PUT, entity3, String.class).getBody();

        HttpEntity<User> entity4 = new HttpEntity<>(null, headers);
        String body4=restTemplate.exchange(url+"/3",HttpMethod.DELETE,entity4,String.class).getBody();

        System.out.println(body2+body3+body4);

    }
}
