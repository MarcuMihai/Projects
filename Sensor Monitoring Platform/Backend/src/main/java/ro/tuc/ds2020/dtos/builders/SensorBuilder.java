package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.SensorDTO;
import ro.tuc.ds2020.entities.SensorEntity;

public class SensorBuilder {
    public SensorBuilder() {
    }

    public static SensorDTO toSensorDTO(SensorEntity sensor) {
        return new SensorDTO(sensor.getId(), sensor.getDescription(), sensor.getMaxValue(), sensor.getDevice(), sensor.getReadValues());
    }

    public static SensorEntity toEntity(SensorDTO sensorDTO) {
        return new SensorEntity(sensorDTO.getId(),
                sensorDTO.getDescription(),
                sensorDTO.getMaxValue(),
                sensorDTO.getDevice(),
                sensorDTO.getReadValues());
    }
}
