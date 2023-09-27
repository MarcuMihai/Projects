package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.UserEntity;
import ro.tuc.ds2020.services.DeviceService;
import ro.tuc.ds2020.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/device")
public class DeviceController {
    private final DeviceService deviceService;
    private final UserService userService;

    @Autowired
    public DeviceController(DeviceService deviceService, UserService userService) {
        this.deviceService = deviceService;
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        List<DeviceDTO> dtos = deviceService.findDevices();
        for (DeviceDTO dto : dtos) {
            Link deviceLink = linkTo(methodOn(DeviceController.class)
                    .getDevice(dto.getId())).withRel("deviceDetails");
            dto.add(deviceLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping(value = "/{idUser}")
    public ResponseEntity<UUID> insertProsumer(@PathVariable(value = "idUser") UUID userId, @Valid @RequestBody DeviceDTO deviceDTO) {
        UUID nullId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        if (userId.equals(nullId)) {
            deviceDTO.setUser(null);
        } else {
            UserDTO userDTO = userService.findUserById(userId);
            UserEntity user = UserBuilder.toEntity(userDTO);
            deviceDTO.setUser(user);
        }
        UUID deviceID = deviceService.insert(deviceDTO);
        return new ResponseEntity<>(deviceID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DeviceDTO> getDevice(@PathVariable("id") UUID deviceId) {
        DeviceDTO dto = deviceService.findDeviceById(deviceId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UUID> deleteProsumer(@PathVariable("id") UUID deviceId) {
        DeviceDTO deviceDTO = deviceService.findDeviceById(deviceId);
        UUID deviceID = deviceService.delete(deviceDTO);
        return new ResponseEntity<>(deviceID, HttpStatus.OK);
    }

    @PutMapping(value = "/{userId}")
    public ResponseEntity<UUID> updateProsumer(@PathVariable("userId") UUID userId, @Valid @RequestBody DeviceDTO deviceDTO) {
        UUID nullId = UUID.fromString("00000000-0000-0000-0000-000000000000");
        if (userId.equals(nullId)) {
            deviceDTO.setUser(null);
        } else {
            UserDTO userDTO = userService.findUserById(userId);
            UserEntity user = UserBuilder.toEntity(userDTO);
            deviceDTO.setUser(user);
        }
        UUID deviceID = deviceService.update(deviceDTO);
        return new ResponseEntity<>(deviceID, HttpStatus.OK);
    }

}
