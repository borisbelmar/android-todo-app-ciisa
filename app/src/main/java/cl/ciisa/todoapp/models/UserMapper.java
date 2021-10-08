package cl.ciisa.todoapp.models;

public class UserMapper {
    private IUser user;

    public UserMapper(IUser user) {
        this.user = user;
    }

    public UserEntity toEntity() {
        return new UserEntity(
                this.user.getId(),
                this.user.getFirstName(),
                this.user.getLastName(),
                this.user.getEmail(),
                this.user.getBirthday(),
                this.user.getPassword()
        );
    }

    public User toBase() {
        User userBase = new User(
                this.user.getFirstName(),
                this.user.getLastName(),
                this.user.getEmail(),
                this.user.getBirthday()
        );
        userBase.setPassword(this.user.getPassword());
        userBase.setId(this.user.getId());
        return userBase;
    }
}
