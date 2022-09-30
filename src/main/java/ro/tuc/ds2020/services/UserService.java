package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.Role;
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
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;
    private final SensorRepository sensorRepository;

    public UserService(UserRepository userRepository, DeviceRepository deviceRepository, SensorRepository sensorRepository) {
        this.userRepository = userRepository;
        this.deviceRepository = deviceRepository;
        this.sensorRepository = sensorRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserDTO> findAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserBuilder::toUserDTO)
                .collect(Collectors.toList());
    }

    public UserDTO findUserById(UUID id) {
        Optional<User> prosumerOptional = userRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("User with id {} was not found in db", id);
            throw new ResourceNotFoundException(User.class.getSimpleName() + " with id: " + id);
        }
        return UserBuilder.toUserDTO(prosumerOptional.get());
    }

    @Transactional
    public UserDTO insert(UserDTO userDTO) {
        User user = UserBuilder.toEntity(userDTO);
        user.setRole(Role.CLIENT);
        user = userRepository.save(user);
        LOGGER.debug("User with id {} was inserted in db", user.getId());
        return UserBuilder.toUserDTO(user);
    }

    @Transactional
    public UserDTO update(UserDTO userDTO) {
        User user = UserBuilder.toEntity(userDTO);
        user = userRepository.save(user);
        if (user.getUsername() != "Admin") {
            user.setRole(Role.CLIENT);
        } else {
            user.setRole(Role.ADMIN);
        }
        LOGGER.debug("User with id {} was updated in db", user.getId());
        return UserBuilder.toUserDTO(user);
    }

    @Transactional
    public UserDTO deleteUserById(UUID id) {
        User user = userRepository.findById(id).get();
        List<Device> devices = deviceRepository.findAll();
        for (Device device : devices) {
            if (device.getUser() != null && device.getUser().getId().equals(id)) {
                device.setUser(null);
                deviceRepository.save(device);
            }
        }
        userRepository.delete(user);
        LOGGER.debug("User with id {} was deleted from db", user.getId());
        return UserBuilder.toUserDTO(user);
    }

    public User logout(UUID id) {
        User user = userRepository.findById(id).get();
        userRepository.save(user);
        System.out.println("user-ul cu id = " + id + " s-a delogat");
        return user;
    }

    public List<DeviceDTO> findUserDevices(UUID id) {
        List<Device> deviceList = deviceRepository.findAll();
        List<DeviceDTO> result = new ArrayList<>();

        for (Device device : deviceList) {
            if (device.getUser() != null) {
                if (device.getUser().getId().equals(id)) {
                    result.add(DeviceBuilder.toDeviceDTO(device));
                }
            }
        }
        return result;
    }

    public List<Device> findDevicesByUser(UUID id) {
        List<Device> deviceList = deviceRepository.findAll();
        List<Device> result = new ArrayList<>();

        for (Device device : deviceList) {
            if (device.getUser() != null) {
                if (device.getUser().getId().equals(id)) {
                    result.add(device);
                }
            }
        }
        return result;
    }
}
