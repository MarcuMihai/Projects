package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.RepresentationModel;
import ro.tuc.ds2020.entities.SensorEntity;

import java.util.Date;
import java.util.UUID;

public class ConsumptionHistoryDTO extends RepresentationModel<ConsumptionHistoryDTO> {
    private UUID id;
    private double value;
    private Date timestamp;
    private SensorEntity readSensor;

    public ConsumptionHistoryDTO() {
    }

    public ConsumptionHistoryDTO(UUID id, SensorEntity readSensor, double value, Date timestamp) {
        this.id = id;
        this.value = value;
        this.timestamp = timestamp;
        this.readSensor = readSensor;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public SensorEntity getReadSensor() {
        return readSensor;
    }

    public void setReadSensor(SensorEntity readSensor) {
        this.readSensor = readSensor;
    }
}
