package ro.tuc.ds2020.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
public class SensorEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "maximum_value", nullable = false)
    private double maxValue;

    @OneToOne()
    @JoinColumn(name="device_id",referencedColumnName = "id")
    private DeviceEntity device;

    @JsonIgnore
    @OneToMany(mappedBy = "readSensor", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<ConsumptionHistory> readValues;

    public SensorEntity() {
    }

    public SensorEntity(UUID id, String description, double maxValue, DeviceEntity device, List<ConsumptionHistory> readValues) {
        this.id = id;
        this.description = description;
        this.maxValue = maxValue;
        this.device=device;
        this.readValues=readValues;
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

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public DeviceEntity getDevice() {
        return device;
    }

    public void setDevice(DeviceEntity device) {
        this.device = device;
    }

    public List<ConsumptionHistory> getReadValues() {
        return readValues;
    }

    public void setReadValues(List<ConsumptionHistory> readValues) {
        this.readValues = readValues;
    }
}
