package ro.tuc.ds2020.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Record {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "timestamp", nullable = false)
    private Date timestamp;

    @NotNull
    private Double energyConsumption;

    @ManyToOne(fetch = FetchType.EAGER)
    private Sensor sensor;

    public Record() {
    }

    public Record(UUID id, Date timestamp, Double energyConsumption) {
        this.id = id;
        this.timestamp = timestamp;
        this.energyConsumption = energyConsumption;
    }

    public Record(UUID id, Date timestamp, Double energyConsumption, Sensor sensor) {
        this.id = id;
        this.timestamp = timestamp;
        this.energyConsumption = energyConsumption;
        this.sensor = sensor;
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

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return Objects.equals(id, record.id) && Objects.equals(timestamp, record.timestamp) && Objects.equals(energyConsumption, record.energyConsumption) && Objects.equals(sensor, record.sensor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp, energyConsumption, sensor);
    }
}
