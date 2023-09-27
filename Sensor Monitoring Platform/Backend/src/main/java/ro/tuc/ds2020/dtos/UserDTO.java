package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.RepresentationModel;
import ro.tuc.ds2020.entities.DeviceEntity;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserDTO extends RepresentationModel<UserDTO> {

    private UUID id;
    private String username;
    private String password;
    private String name;
    private String address;
    private Date birthDate;
    private String type;
    private List<DeviceEntity> devices;

    public UserDTO(UUID id, String username, String password, String name, String address, Date birthDate, String type, List<DeviceEntity> devices) {
        this.id = id;
        this.username=username;
        this.password=password;
        this.name = name;
        this.address = address;
        this.birthDate = birthDate;
        this.type = type;
        this.devices=devices;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DeviceEntity> getDevices() {
        return devices;
    }

    public void setDevices(List<DeviceEntity> devices) {
        this.devices = devices;
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
        UserDTO user = (UserDTO) o;
        return  Objects.equals(name, user.name) &&
                Objects.equals(address, user.address) &&
                Objects.equals(birthDate, user.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, birthDate);
    }
}
