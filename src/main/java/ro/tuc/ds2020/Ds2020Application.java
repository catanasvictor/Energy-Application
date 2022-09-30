package ro.tuc.ds2020;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import ro.tuc.ds2020.config.QueueConfig;
import ro.tuc.ds2020.entity.Measurement;
import ro.tuc.ds2020.reader.MeasurementReader;

import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.UUID;

@SpringBootApplication
@Validated
public class Ds2020Application extends SpringBootServletInitializer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Ds2020Application.class);
    }

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        SpringApplication.run(Ds2020Application.class, args);
    }

    @Bean
    CommandLineRunner init() {
        return args ->
        {
            if (args.length > 0) {
                UUID id = UUID.fromString(args[0]);
                MeasurementReader measurementReader = new MeasurementReader();
                Measurement receivedMeasurement = new Measurement(LocalDateTime.now(), 0.0D, id);

                for (Measurement measurement : measurementReader.getMeasurements(id)) {
                    try {
                        receivedMeasurement.setTimestamp(measurement.getTimestamp());
                        receivedMeasurement.setMeasurementValue(measurement.getMeasurementValue());
                        receivedMeasurement.setSensorId(measurement.getSensorId());
                        Thread.sleep(2000);
                        System.out.println("Record: " + measurement.getMeasurementValue());
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    try {
                        rabbitTemplate.convertAndSend(QueueConfig.EXCHANGE, QueueConfig.ROUTING_KEY, receivedMeasurement);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("\nFinished\n");
            }
        };
    }
}
