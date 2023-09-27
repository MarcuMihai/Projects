package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.RepresentationModel;
import ro.tuc.ds2020.entities.SensorEntity;
import ro.tuc.ds2020.entities.UserEntity;

import java.util.Objects;
import java.util.UUID;

public class DeviceDTO extends RepresentationModel<DeviceDTO> {
    private UUID id;
    private String description;
    private String address;
    private double maximumEnergyConsumption;
    private double averageEnergyConsumption;
    private UserEntity user;
    private SensorEntity sensor;

    public DeviceDTO(UUID id, String description, String address, double maximumEnergyConsumption, double averageEnergyConsumption, UserEntity user, SensorEntity sensor) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.maximumEnergyConsumption = maximumEnergyConsumption;
        this.averageEnergyConsumption = averageEnergyConsumption;
        this.user=user;
        this.sensor=sensor;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getMaximumEnergyConsumption() {
        return maximumEnergyConsumption;
    }

    public void setMaximumEnergyConsumption(double maximumEnergyConsumption) {
        this.maximumEnergyConsumption = maximumEnergyConsumption;
    }

    public double getAverageEnergyConsumption() {
        return averageEnergyConsumption;
    }

    public void setAverageEnergyConsumption(double averageEnergyConsumption) {
        this.averageEnergyConsumption = averageEnergyConsumption;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public SensorEntity getSensor() {
        return sensor;
    }

    public void setSensor(SensorEntity sensor) {
        this.sensor = sensor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDTO device = (DeviceDTO) o;
        return  Objects.equals(description, device.description) &&
                Objects.equals(address, device.address) &&
                maximumEnergyConsumption==device.maximumEnergyConsumption &&
                averageEnergyConsumption==device.averageEnergyConsumption;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, address, maximumEnergyConsumption, averageEnergyConsumption);
    }
}
