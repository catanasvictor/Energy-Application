package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.RepresentationModel;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class RecordDTO extends RepresentationModel<RecordDTO> {

    private UUID id;
    private Date timestamp;
    private Double energyConsumption;

    public RecordDTO(UUID id, Date timestamp, Double energyConsumption) {
        this.id = id;
        this.timestamp = timestamp;
        this.energyConsumption = energyConsumption;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Double getEnergyConsumption() {
        return energyConsumption;
    }

    public void setEnergyConsumption(Double energyConsumption) {
        this.energyConsumption = energyConsumption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RecordDTO recordDTO = (RecordDTO) o;
        return Objects.equals(id, recordDTO.id) && Objects.equals(timestamp, recordDTO.timestamp) && Objects.equals(energyConsumption, recordDTO.energyConsumption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, timestamp, energyConsumption);
    }
}
