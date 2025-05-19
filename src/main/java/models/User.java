package models;

import enumurate.UserRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    int id;
    String username;
    String hash_password;
    UserRole role;

    @Override
    public String toString() {
        return "user id: " + id + ", name: " + username;
    }
}
