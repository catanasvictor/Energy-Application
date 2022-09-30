package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.SensorDTO;
import ro.tuc.ds2020.services.DeviceService;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin("*")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/device")
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        List<DeviceDTO> dtos = deviceService.findAllDevices();
        for (DeviceDTO dto : dtos) {
            Link deviceLink = linkTo(methodOn(DeviceController.class)
                    .getDeviceById(dto.getId())).withRel("deviceDetails");
            dto.add(deviceLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/device")
    public ResponseEntity<DeviceDTO> createDevice(@RequestBody DeviceDTO deviceDTO) {
        return new ResponseEntity<>(deviceService.insert(deviceDTO), HttpStatus.CREATED);
    }

    @GetMapping("/device/{id}")
    public DeviceDTO getDeviceById(@PathVariable UUID id) {
        return deviceService.findDeviceById(id);
    }

    @PutMapping("/device")
    public ResponseEntity<DeviceDTO> updateDevice(@RequestBody DeviceDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(deviceService.update(dto));
    }

    @DeleteMapping("/device/{id}")
    public ResponseEntity<DeviceDTO> deleteDeviceById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(deviceService.deleteDeviceById(id));
    }

    @PutMapping("/user/device/{userId}/{deviceId}")
    public ResponseEntity<DeviceDTO> associate(@PathVariable UUID userId, @PathVariable UUID deviceId) {
        return ResponseEntity.status(HttpStatus.OK).body(deviceService.associateDeviceByClient(userId, deviceId));
    }

    @GetMapping("/device/sensor/{id}")
    public ResponseEntity<List<SensorDTO>> findSensorByDevice(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(deviceService.findSensorByDevice(id));
    }

}
