package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.SensorDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.SensorBuilder;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.Record;
import ro.tuc.ds2020.entities.Sensor;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.RecordRepository;
import ro.tuc.ds2020.repositories.SensorRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SensorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SensorService.class);
    private final SensorRepository sensorRepository;
    private final DeviceRepository deviceRepository;
    private final RecordRepository recordRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository, DeviceRepository deviceRepository, RecordRepository recordRepository) {
        this.sensorRepository = sensorRepository;
        this.deviceRepository = deviceRepository;
        this.recordRepository = recordRepository;
    }

    public List<SensorDTO> findAllSensors() {
        List<Sensor> sensorList = sensorRepository.findAll();
        return sensorList.stream()
                .map(SensorBuilder::toSensorDTO)
                .collect(Collectors.toList());
    }

    public SensorDTO findSensorById(UUID id) {
        Optional<Sensor> prosumerOptional = sensorRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Sensor with id {} was not found in db", id);
            throw new ResourceNotFoundException(Sensor.class.getSimpleName() + " with id: " + id);
        }
        return SensorBuilder.toSensorDTO(prosumerOptional.get());
    }

    @Transactional
    public SensorDTO insert(SensorDTO sensorDTO) {
        Sensor sensor = SensorBuilder.toEntity(sensorDTO);
        sensor = sensorRepository.save(sensor);
        LOGGER.debug("Sensor with id {} was inserted in db", sensor.getId());
        return SensorBuilder.toSensorDTO(sensor);
    }

    @Transactional
    public SensorDTO update(SensorDTO sensorDTO) {
        Sensor sensor = SensorBuilder.toEntity(sensorDTO);
        sensor = sensorRepository.save(sensor);
        LOGGER.debug("Sensor with id {} was updated in db", sensor.getId());
        return SensorBuilder.toSensorDTO(sensor);
    }

    @Transactional
    public SensorDTO deleteSensorById(UUID id) {
        Sensor sensor = sensorRepository.findById(id).get();
        List<Record> records = recordRepository.findAll();
        for (Record record : records) {
            if (record.getSensor() != null && record.getSensor().getId().equals(id)) {
                record.setSensor(null);
                recordRepository.save(record);
            }
        }
        sensorRepository.delete(sensor);
        LOGGER.debug("Sensor with id {} was deleted from db", sensor.getId());
        return SensorBuilder.toSensorDTO(sensor);
    }

    public SensorDTO associateSensorByDevice(UUID deviceId, UUID sensorId) {
        Sensor sensor = sensorRepository.findById(sensorId).get();
        Device device = deviceRepository.findById(deviceId).get();
        sensor.setDevice(device);
        sensorRepository.save(sensor);
        return SensorBuilder.toSensorDTO(sensor);
    }
}
