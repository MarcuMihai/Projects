package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.ConsumptionHistoryDTO;
import ro.tuc.ds2020.dtos.SensorDTO;
import ro.tuc.ds2020.dtos.builders.SensorBuilder;
import ro.tuc.ds2020.entities.SensorEntity;
import ro.tuc.ds2020.services.ConsumptionHistoryService;
import ro.tuc.ds2020.services.SensorService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/consumption")
public class ConsumptionHistoryController {
    private final ConsumptionHistoryService consumptionHistoryService;
    private final SensorService sensorService;

    @Autowired
    public ConsumptionHistoryController(ConsumptionHistoryService consumptionHistoryService, SensorService sensorService) {
        this.consumptionHistoryService = consumptionHistoryService;
        this.sensorService=sensorService;
    }

    @GetMapping()
    public ResponseEntity<List<ConsumptionHistoryDTO>> getEntries() {
        List<ConsumptionHistoryDTO> dtos = consumptionHistoryService.findEntries();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping(value = "/{idSensor}")
    public ResponseEntity<UUID> insertProsumer(@PathVariable(value = "idSensor") UUID sensorId, @Valid @RequestBody ConsumptionHistoryDTO consumptionHistoryDTO) {
        SensorDTO sensorDTO = sensorService.findSensorById(sensorId);
        SensorEntity sensor = SensorBuilder.toEntity(sensorDTO);
        consumptionHistoryDTO.setReadSensor(sensor);
        UUID id = consumptionHistoryService.insert(consumptionHistoryDTO);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UUID> deleteProsumer(@PathVariable("id") UUID entryId) {
        ConsumptionHistoryDTO consumptionHistoryDTO = consumptionHistoryService.findEntryById(entryId);
        UUID id = consumptionHistoryService.delete(consumptionHistoryDTO);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
