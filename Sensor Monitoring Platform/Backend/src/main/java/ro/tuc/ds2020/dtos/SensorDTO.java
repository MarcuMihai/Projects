package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.RepresentationModel;
import ro.tuc.ds2020.entities.ConsumptionHistory;
import ro.tuc.ds2020.entities.DeviceEntity;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SensorDTO extends RepresentationModel<SensorDTO> {

    private UUID id;
    private String description;
    private double maxValue;
    private DeviceEntity device;
    private List<ConsumptionHistory> readValues;

    public SensorDTO(UUID id, String description, double maxValue, DeviceEntity device, List<ConsumptionHistory> readValues) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorDTO sensor = (SensorDTO) o;
        return  Objects.equals(description, sensor.description) &&
                Objects.equals(maxValue, sensor.maxValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, maxValue);
    }
}
