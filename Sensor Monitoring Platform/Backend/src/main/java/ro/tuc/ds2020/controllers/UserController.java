package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.UserLoginDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> dtos = userService.findUsers();
        for (UserDTO dto : dtos) {
            Link userLink = linkTo(methodOn(UserController.class)
                    .getUser(dto.getId())).withRel("userDetails");
            dto.add(userLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UUID> insertProsumer(@Valid @RequestBody UserDTO userDTO) {
        UUID userID = userService.insert(userDTO);
        return new ResponseEntity<>(userID, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") UUID userId) {
        UserDTO dto = userService.findUserById(userId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UUID> deleteProsumer(@PathVariable("id") UUID userId) {
        UserDTO userDTO = userService.findUserById(userId);
        UUID userID = userService.delete(userDTO);
        return new ResponseEntity<>(userID, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<UUID> updateProsumer(@Valid @RequestBody UserDTO userDTO) {
        UUID userID = userService.update(userDTO);
        return new ResponseEntity<>(userID, HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<UserDTO> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        UserDTO userDTO = userService.findUserByUsername(userLoginDTO.getUsername());
        if(userDTO.getPassword().equals(userLoginDTO.getPassword())){
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
        else return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}/devices")
    public ResponseEntity<List<DeviceDTO>> getDevicesOfUser(@PathVariable("id") UUID userId) {
        UserDTO user = userService.findUserById(userId);
        List<DeviceDTO> dtos=user.getDevices().stream().map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

}
