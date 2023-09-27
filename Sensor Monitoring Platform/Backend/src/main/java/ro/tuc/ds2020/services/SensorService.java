package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.SensorDTO;
import ro.tuc.ds2020.dtos.builders.SensorBuilder;
import ro.tuc.ds2020.entities.SensorEntity;
import ro.tuc.ds2020.repositories.SensorRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SensorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SensorService.class);
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public List<SensorDTO> findSensors() {
        List<SensorEntity> sensorList = sensorRepository.findAll();
        return sensorList.stream()
                .map(SensorBuilder::toSensorDTO)
                .collect(Collectors.toList());
    }

    public SensorDTO findSensorById(UUID id) {
        Optional<SensorEntity> prosumerOptional = sensorRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Sensor with id {} was not found in db", id);
            throw new ResourceNotFoundException(SensorEntity.class.getSimpleName() + " with id: " + id);
        }
        return SensorBuilder.toSensorDTO(prosumerOptional.get());
    }

    public UUID insert(SensorDTO sensorDTO) {
        SensorEntity sensor = SensorBuilder.toEntity(sensorDTO);
        sensor = sensorRepository.save(sensor);
        LOGGER.debug("Sensor with id {} was inserted in db", sensor.getId());
        return sensor.getId();
    }

    public UUID delete(SensorDTO sensorDTO) {
        SensorEntity sensor = SensorBuilder.toEntity(sensorDTO);
        sensorRepository.delete(sensor);
        LOGGER.debug("Sensor with id {} was deleted from db", sensor.getId());
        return sensor.getId();
    }

    public UUID update(SensorDTO sensorDTO) {
        SensorEntity sensor = SensorBuilder.toEntity(sensorDTO);
        sensor = sensorRepository.save(sensor);
        LOGGER.debug("Sensor with id {} was updated in db", sensor.getId());
        return sensor.getId();
    }
}
