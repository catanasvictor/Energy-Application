package ro.tuc.ds2020.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "location", nullable = false)
    private String location;

    @NotNull(message = "maximumEnergyConsumption cannot be null")
    private Double maximumEnergyConsumption;

    @NotNull(message = "baselineEnergyConsumption cannot be null")
    private Double baselineEnergyConsumption;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Device() {
    }

    public Device(UUID id, String description, String location, Double maximumEnergyConsumption, Double baselineEnergyConsumption) {
        this.id = id;
        this.description = description;
        this.location = location;
        this.maximumEnergyConsumption = maximumEnergyConsumption;
        this.baselineEnergyConsumption = baselineEnergyConsumption;
    }

    public Device(UUID id, String description, String location, Double maximumEnergyConsumption, Double baselineEnergyConsumption, User user) {
        this.id = id;
        this.description = description;
        this.location = location;
        this.maximumEnergyConsumption = maximumEnergyConsumption;
        this.baselineEnergyConsumption = baselineEnergyConsumption;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
