package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.RecordDTO;
import ro.tuc.ds2020.dtos.builders.RecordBuilder;
import ro.tuc.ds2020.entities.Record;
import ro.tuc.ds2020.repositories.RecordRepository;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecordService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecordService.class);
    private final RecordRepository recordRepository;

    @Autowired
    public RecordService(RecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    public List<RecordDTO> findAllRecords() {
        List<Record> recordList = recordRepository.findAll();
        return recordList.stream()
                .map(RecordBuilder::toRecordDTO)
                .collect(Collectors.toList());
    }

    public List<Record> findAll() {
        return recordRepository.findAll();
    }

    public RecordDTO findRecordById(UUID id) {
        Optional<Record> prosumerOptional = recordRepository.findById(id);
        if (!prosumerOptional.isPresent()) {
            LOGGER.error("Record with id {} was not found in db", id);
            throw new ResourceNotFoundException(Record.class.getSimpleName() + " with id: " + id);
        }
        return RecordBuilder.toRecordDTO(prosumerOptional.get());
    }

    @Transactional
    public RecordDTO insert(RecordDTO recordDTO) {
        Record record = RecordBuilder.toEntity(recordDTO);
        record = recordRepository.save(record);
        LOGGER.debug("Record with id {} was inserted in db", record.getId());
        return RecordBuilder.toRecordDTO(record);
    }

    @Transactional
    public RecordDTO update(RecordDTO recordDTO) {
        Record record = RecordBuilder.toEntity(recordDTO);
        record = recordRepository.save(record);
        LOGGER.debug("Record with id {} was updated in db", record.getId());
        return RecordBuilder.toRecordDTO(record);
    }

    @Transactional
    public RecordDTO deleteRecordById(UUID id) {
        Record record = recordRepository.findById(id).get();
        recordRepository.delete(record);
        LOGGER.debug("Record with id {} was deleted from db", record.getId());
        return RecordBuilder.toRecordDTO(record);
    }

    public Double[] chartClient(UUID id, String dateString) {
        Double[] energyConsumption = new Double[24];
        for (int i = 0; i < 24; i++) {
            energyConsumption[i] = 0.0;
        }

        String[] stringParse = dateString.split("-");
        Integer[] integers = new Integer[3];
        int i = 0;
        for (String string : stringParse) {
            integers[i] = Integer.parseInt(string);
            i++;
        }
        List<Record> recordList = recordRepository.findAll();
        for (Record record : recordList) {
            System.out.println("Records sensor: " + record.getSensor().getId());
            if (record.getSensor().getDevice().getUser().getId().equals(id) && ((record.getTimestamp().getYear() + 1900) == integers[0]) && ((record.getTimestamp().getMonth() + 1) == integers[1]) && (record.getTimestamp().getDate() == integers[2])) {
                energyConsumption[record.getTimestamp().getHours()] = record.getEnergyConsumption();
            }
        }
        return energyConsumption;
    }

    public List<RecordDTO> findRecordsByUser(UUID id) {
        List<Record> recordList = recordRepository.findAll();
        List<Record> result = new ArrayList<>();
        for (Record record : recordList) {
            if (record.getSensor() != null && record.getSensor().getDevice() != null && record.getSensor().getDevice().getUser() != null
                    && record.getSensor().getDevice().getUser().getId().equals(id)) {
                result.add(record);
            }
        }
        return result.stream()
                .map(RecordBuilder::toRecordDTO)
                .collect(Collectors.toList());
    }
}