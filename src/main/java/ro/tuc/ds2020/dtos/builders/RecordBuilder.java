package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.RecordDTO;
import ro.tuc.ds2020.entities.Record;

public class RecordBuilder {
    private RecordBuilder() {
    }

    public static RecordDTO toRecordDTO(Record record) {
        return new RecordDTO(record.getId(), record.getTimestamp(), record.getEnergyConsumption());
    }

    public static Record toEntity(RecordDTO recordDTO) {
        return new Record(recordDTO.getId(), recordDTO.getTimestamp(), recordDTO.getEnergyConsumption());
    }
}
