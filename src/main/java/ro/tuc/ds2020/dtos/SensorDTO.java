package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;
import java.util.UUID;

public class SensorDTO extends RepresentationModel<SensorDTO> {
    private UUID id;
    private String description;
    private int maximumValue;

    public SensorDTO(UUID id, String description, int maximumValue) {
        this.id = id;
        this.description = description;
        this.maximumValue = maximumValue;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaximumValue() {
        return maximumValue;
    }

    public void setMaximumValue(int maximumValue) {
        this.maximumValue = maximumValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SensorDTO sensorDTO = (SensorDTO) o;
        return maximumValue == sensorDTO.maximumValue &&
                Objects.equals(description, sensorDTO.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, maximumValue);
    }
}
