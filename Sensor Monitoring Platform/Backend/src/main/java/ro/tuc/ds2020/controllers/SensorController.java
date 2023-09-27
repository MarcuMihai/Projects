package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.SensorDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.entities.DeviceEntity;
import ro.tuc.ds2020.services.DeviceService;
import ro.tuc.ds2020.services.SensorService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/sensor")
public class SensorController {
    private final SensorService sensorService;
    private final DeviceService deviceService;

    @Autowired
    public SensorController(SensorService sensorService, DeviceService deviceService) {
        this.sensorService = sensorService;
        this.deviceService = deviceService;
    }

    @GetMapping()
    public ResponseEntity<List<SensorDTO>> getSensors() {
        List<SensorDTO> dtos = sensorService.findSensors();
        for (SensorDTO dto : dtos) {
            Link sensorLink = linkTo(methodOn(SensorController.class)
                    .getSensor(dto.getId())).withRel("sensorDetails");
            dto.add(sensorLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping(value = "/{idDevice}")
    public ResponseEntity<UUID> insertProsumer(@PathVariable("idDevice") UUID deviceId, @Valid @RequestBody SensorDTO sensorDTO) {
        UUID nullId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        if (deviceId.equals(nullId)) {
            sensorDTO.setDevice(null);
        } else {
            DeviceDTO deviceDTO = deviceService.findDeviceById(deviceId);
            DeviceEntity device = DeviceBuilder.toEntity(deviceDTO);
            sensorDTO.setDevice(device);
        }
        UUID sensorID = sensorService.insert(sensorDTO);
        return new ResponseEntity<>(sensorID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<SensorDTO> getSensor(@PathVariable("id") UUID sensorId) {
        SensorDTO dto = sensorService.findSensorById(sensorId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UUID> deleteProsumer(@PathVariable("id") UUID sensorId) {
        SensorDTO sensorDTO = sensorService.findSensorById(sensorId);
        UUID sensorID = sensorService.delete(sensorDTO);
        return new ResponseEntity<>(sensorID, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UUID> updateProsumer(@PathVariable("id") UUID deviceId, @Valid @RequestBody SensorDTO sensorDTO) {
        UUID nullId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        if (deviceId.equals(nullId)) {
            sensorDTO.setDevice(null);
        } else {
            DeviceDTO deviceDTO = deviceService.findDeviceById(deviceId);
            DeviceEntity device = DeviceBuilder.toEntity(deviceDTO);
            sensorDTO.setDevice(device);
        }
        UUID sensorID = sensorService.update(sensorDTO);
        return new ResponseEntity<>(sensorID, HttpStatus.OK);
    }
}
