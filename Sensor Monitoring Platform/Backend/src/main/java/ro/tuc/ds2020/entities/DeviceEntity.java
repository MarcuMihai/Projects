package ro.tuc.ds2020.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class DeviceEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "maximum_energy_consumption", nullable = false)
    private double maximumEnergyConsumption;

    @Column(name = "average_energy_consumption", nullable = false)
    private double averageEnergyConsumption;

    @ManyToOne()
    @JoinColumn(name="user_id")
    private UserEntity user;

    @JsonIgnore
    @OneToOne(mappedBy = "device", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private SensorEntity sensor;

    public DeviceEntity() {
    }

    public DeviceEntity(UUID id, String description, String address, double maximumEnergyConsumption, double averageEnergyConsumption, UserEntity user, SensorEntity sensor) {
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


}
