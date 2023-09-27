package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.RepresentationModel;
import java.util.Objects;

public class UserLoginDTO extends RepresentationModel<UserLoginDTO>{
    private String username;
    private String password;

    public UserLoginDTO(String username, String password){
        this.username=username;
        this.password=password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLoginDTO user = (UserLoginDTO) o;
        return  Objects.equals(username, user.username) &&
                Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username,password);
    }
}
