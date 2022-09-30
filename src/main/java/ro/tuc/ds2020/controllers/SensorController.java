package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.SensorDTO;
import ro.tuc.ds2020.services.SensorService;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin("*")
public class SensorController {

    private final SensorService sensorService;

    @Autowired
    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @GetMapping("/sensor")
    public ResponseEntity<List<SensorDTO>> getAllSensors() {
        List<SensorDTO> dtos = sensorService.findAllSensors();
        for (SensorDTO dto : dtos) {
            Link sensorLink = linkTo(methodOn(SensorController.class)
                    .getSensorById(dto.getId())).withRel("sensorDetails");
            dto.add(sensorLink);
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping("/sensor")
    public ResponseEntity<SensorDTO> createSensor(@RequestBody SensorDTO sensorDTO) {
        return new ResponseEntity<>(sensorService.insert(sensorDTO), HttpStatus.CREATED);
    }

    @GetMapping("/sensor/{id}")
    public SensorDTO getSensorById(@PathVariable UUID id) {
        return sensorService.findSensorById(id);
    }

    @PutMapping("/sensor")
    public ResponseEntity<SensorDTO> updateSensor(@RequestBody SensorDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(sensorService.update(dto));
    }

    @DeleteMapping("/sensor/{id}")
    public ResponseEntity<SensorDTO> deleteSensorById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(sensorService.deleteSensorById(id));
    }

    @PutMapping("/device/sensor/{deviceId}/{sensorId}")
    public ResponseEntity<SensorDTO> associate(@PathVariable UUID deviceId, @PathVariable UUID sensorId) {
        return ResponseEntity.status(HttpStatus.OK).body(sensorService.associateSensorByDevice(deviceId, sensorId));
    }
}
