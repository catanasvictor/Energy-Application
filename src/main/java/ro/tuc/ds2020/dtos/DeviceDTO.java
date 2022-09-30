package ro.tuc.ds2020.dtos;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;
import java.util.UUID;

public class DeviceDTO extends RepresentationModel<DeviceDTO> {
    private UUID id;
    private String description;
    private String location;
    private Double maximumEnergyConsumption;
    private Double baselineEnergyConsumption;

    public DeviceDTO(UUID id, String description, String location, Double maximumEnergyConsumption, Double baselineEnergyConsumption) {
        this.id = id;
        this.description = description;
        this.location = location;
        this.maximumEnergyConsumption = maximumEnergyConsumption;
        this.baselineEnergyConsumption = baselineEnergyConsumption;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getMaximumEnergyConsumption() {
        return maximumEnergyConsumption;
    }

    public void setMaximumEnergyConsumption(Double maximumEnergyConsumption) {
        this.maximumEnergyConsumption = maximumEnergyConsumption;
    }

    public Double getBaselineEnergyConsumption() {
        return baselineEnergyConsumption;
    }

    public void setBaselineEnergyConsumption(Double baselineEnergyConsumption) {
        this.baselineEnergyConsumption = baselineEnergyConsumption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceDTO deviceDTO = (DeviceDTO) o;
        return Objects.equals(description, deviceDTO.description) &&
                Objects.equals(location, deviceDTO.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, location);
    }
}
