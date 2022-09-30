package ro.tuc.ds2020.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ro.tuc.ds2020.entities.Measurement;
import ro.tuc.ds2020.entities.Record;
import ro.tuc.ds2020.entities.Sensor;
import ro.tuc.ds2020.repositories.RecordRepository;
import ro.tuc.ds2020.repositories.SensorRepository;

import java.util.Date;

@Component
public class MeasurementListener {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    private final RecordRepository recordRepository;
    private final SensorRepository sensorRepository;

    public MeasurementListener(RecordRepository recordRepository, SensorRepository sensorRepository) {
        this.recordRepository = recordRepository;
        this.sensorRepository = sensorRepository;
    }

    @RabbitListener(queues = MQConfig.QUEUE)
    public void listener(Measurement measurement) {
        Record[] records = recordRepository.findAll().toArray(new Record[0]);

        Sensor sensor = sensorRepository.findById(measurement.getSensorId()).get();

        Record record = new Record(null, new Date(measurement.getTimestamp().getYear() - 1900, measurement.getTimestamp().getMonthValue() - 1, measurement.getTimestamp().getDayOfMonth(),
                measurement.getTimestamp().getHour(), measurement.getTimestamp().getMinute(), measurement.getTimestamp().getSecond())
                , measurement.getMeasurementValue(), sensor);

        double difference = record.getEnergyConsumption() - records[records.length - 1].getEnergyConsumption();
        if (difference > sensor.getMaximumValue()) {
            simpMessagingTemplate.convertAndSend("/broker/websocket/record", " Measurement power peak that exceeds maximum threshold: \n" + "Record1: " + record.getEnergyConsumption() + "\nRecord2: " + records[records.length - 1].getEnergyConsumption() + "\nDifference: " + difference + "\nMaximum Value: " + sensor.getMaximumValue());
            return;
        } else {
            recordRepository.save(record);
        }
        System.out.println("record = " + record.getId().toString() + " " + record.getTimestamp() + " " + record.getEnergyConsumption());
    }
}