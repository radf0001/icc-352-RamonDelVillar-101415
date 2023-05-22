package practicaAula.user;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableList;

public class UserDao {

    private final List<User> users = ImmutableList.of(
        //        Username    Salt for hash                    Hashed password (the password is "password" for all users)
        new User("ramon", "1234"),
        new User("carlos", "1234"),
        new User("mario", "1234")
    );

    public User getUserByUsername(String username) {
        return users.stream().filter(b -> b.username.equalsIgnoreCase(username)).findFirst().orElse(null);
    }

    public Iterable<String> getAllUserNames() {
        return users.stream().map(user -> user.username).collect(Collectors.toList());
    }

}
