package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.SensorDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.dtos.builders.SensorBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.Record;
import ro.tuc.ds2020.entities.Sensor;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.SensorRepository;
import ro.tuc.ds2020.repositories.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;
    private final SensorRepository sensorRepository;
    private final UserRepository userRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, SensorRepository sensorRepository, UserRepository userRepository) {
        this.deviceRepository = deviceRepository;
        this.sensorRepository = sensorRepository;
        this.userRepository = userRepository;
    }

    public List<DeviceDTO> findAllDevices() {
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    public DeviceDTO findDeviceById(UUID id) {
        Optional<Device> prosumerOptional = deviceRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Device with id {} was not found in db", id);
            throw new ResourceNotFoundException(Device.class.getSimpleName() + " with id: " + id);
        }
        return DeviceBuilder.toDeviceDTO(prosumerOptional.get());
    }

    public Device findDeviceByDescription(String device) {
        return deviceRepository.findDeviceByDescription(device);
    }

    @Transactional
    public DeviceDTO insert(DeviceDTO deviceDTO) {
        Device device = DeviceBuilder.toEntity(deviceDTO);
        device = deviceRepository.save(device);
        LOGGER.debug("Device with id {} was inserted in db", device.getId());
        return DeviceBuilder.toDeviceDTO(device);
    }


    @Transactional
    public DeviceDTO update(DeviceDTO deviceDTO) {
        Device device = DeviceBuilder.toEntity(deviceDTO);
        device = deviceRepository.save(device);
        LOGGER.debug("Device with id {} was updated in db", device.getId());
        return DeviceBuilder.toDeviceDTO(device);
    }

    @Transactional
    public DeviceDTO deleteDeviceById(UUID id) {
        Device device = deviceRepository.findById(id).get();
        Sensor sensor = sensorRepository.findFirstByDevice(device);
        if (sensor == null) {
            deviceRepository.delete(device);
            return DeviceBuilder.toDeviceDTO(device);
        }
        if (sensor.getDevice().getId() != null) {
            sensor.setDevice(null);
            sensorRepository.save(sensor);
        }
        deviceRepository.delete(device);
        LOGGER.debug("Device with id {} was deleted from db", device.getId());
        return DeviceBuilder.toDeviceDTO(device);
    }

    public DeviceDTO associateDeviceByClient(UUID userId, UUID deviceId) {
        Device device = deviceRepository.findById(deviceId).get();
        User user = userRepository.findById(userId).get();
        device.setUser(user);
        deviceRepository.save(device);
        return DeviceBuilder.toDeviceDTO(device);
    }

    public List<SensorDTO> findSensorByDevice(UUID id) {
        List<Sensor> sensorList = sensorRepository.findAll();
        List<SensorDTO> result = new ArrayList<>();
        for (Sensor sensor : sensorList) {
            if (sensor.getDevice() != null) {
                if (sensor.getDevice().getId().equals(id)) {
                    result.add(SensorBuilder.toSensorDTO(sensor));
                }
            }
        }
        return result;
    }
}
