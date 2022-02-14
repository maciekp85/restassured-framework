package pl.javastart.restassured.main.test.data;

import pl.javastart.restassured.main.pojo.user.User;

public class UserTestDataGenerator extends TestDataGenerator {

    public User generateUser() {
        User user = new User();
        user.setId(faker().number().numberBetween(1, 9999));
        user.setUsername(faker().name().username());
        user.setFirstName(faker().name().firstName());
        user.setLastName(faker().name().lastName());
        user.setEmail(faker().internet().emailAddress());
        user.setPassword("P@ssw0rd");
        user.setPhone(faker().phoneNumber().phoneNumber());
        user.setUserStatus(1);
        return user;
    }
}
