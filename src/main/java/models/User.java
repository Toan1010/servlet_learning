package models;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    int id;
    String username;
    String password;

    @Override
    public String toString() {
        return "user id: " + id + ", name: " + username;
    }
}
