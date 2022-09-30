package ro.tuc.ds2020;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

import ro.tuc.ds2020.entities.*;
import ro.tuc.ds2020.entities.Record;

import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.RecordRepository;
import ro.tuc.ds2020.repositories.SensorRepository;
import ro.tuc.ds2020.repositories.UserRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@SpringBootApplication
@Validated
public class Ds2020Application extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Ds2020Application.class);
    }

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(Ds2020Application.class, args);
    }

    @Bean
    CommandLineRunner init(
            UserRepository userRepository, DeviceRepository deviceRepository,
            SensorRepository sensorRepository, RecordRepository recordRepository
    ) {
        return args ->
        {
            User user1 = new User(null, "admin", "admin", "Kaj", "Cluj", new Date(1999 - 1900, Calendar.JUNE, 27), Role.ADMIN);
            User user2 = new User(null, "victor", "pass123", "Victor", "Bucuresti", new Date(1989 - 1900, Calendar.APRIL, 25), Role.CLIENT);
            //User user3 = new User(null, "vic", "pass123", "Vic", "Bucuresti", new Date(1989 - 1900, Calendar.APRIL, 25), Role.CLIENT);

            userRepository.save(user1);
            userRepository.save(user2);
            //userRepository.save(user3);

            Device device1 = new Device(null, "SG s21 ultra", "Cluj", 75.0, 65.0, user2);
//            Device device2 = new Device(null, "AI 13 pro max", "Zalau", 200.0, 90.0, user2);
//            Device device3 = new Device(null, "Huawei 15", "Zalau", 250.0, 70.0, user3);
            deviceRepository.save(device1);
//            deviceRepository.save(device2);
//            deviceRepository.save(device3);

            Sensor sensor1 = new Sensor(null, "S1 810", 8, device1);
            //Sensor sensor2 = new Sensor(null, "S2 440", 90, device2);
            sensorRepository.save(sensor1);
            //sensorRepository.save(sensor2);

            Record record1 = new Record(null, new Date(2022 - 1900, 1 - 1, 3, 5, 0), 50.0, sensor1);
//            Record record2 = new Record(null, new Date(2022 - 1900, 1 - 1, 2, 13, 0), 26.0, sensor1);
//            Record record3 = new Record(null, new Date(2022 - 1900, 1 - 1, 1, 12, 0), 30.0, sensor1);
//            Record record4 = new Record(null, new Date(2022 - 1900, 1 - 1, 4, 1, 0), 19.0, sensor1);
//            Record record5 = new Record(null, new Date(2021 - 1900, 12 - 1, 29, 8, 0), 18.0, sensor2);
//            Record record6 = new Record(null, new Date(2021 - 1900, 12 - 1, 28, 9, 0), 35.0, sensor2);
//            Record record7 = new Record(null, new Date(2021 - 1900, 11 - 1, 9, 17, 0), 16.0, sensor2);
//            Record record8 = new Record(null, new Date(2021 - 1900, 11 - 1, 9, 23, 0), 50.0, sensor2);
            recordRepository.save(record1);
//            recordRepository.save(record2);
//            recordRepository.save(record3);
//            recordRepository.save(record4);
//            recordRepository.save(record5);
//            recordRepository.save(record6);
//            recordRepository.save(record7);
//            recordRepository.save(record8);

            System.out.println("\nFinished\n");
        };
    }
}
