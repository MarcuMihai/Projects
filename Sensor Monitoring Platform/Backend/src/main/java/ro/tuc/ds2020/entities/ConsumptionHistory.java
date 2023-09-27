package ro.tuc.ds2020.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
public class ConsumptionHistory {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @ManyToOne()
    @JoinColumn(name="sensor_id")
    private SensorEntity readSensor;

    @Column(name = "value", nullable = false)
    private double value;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "timestamp", nullable = false)
    private Date timestamp;

    public ConsumptionHistory(UUID id, SensorEntity readSensor, double value, Date timestamp) {
        this.id = id;
        this.readSensor = readSensor;
        this.value = value;
        this.timestamp = timestamp;
    }

    public ConsumptionHistory() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public SensorEntity getReadSensor() {
        return readSensor;
    }

    public void setReadSensor(SensorEntity readSensor) {
        this.readSensor = readSensor;
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
}
