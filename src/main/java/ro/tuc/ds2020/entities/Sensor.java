package ro.tuc.ds2020.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.UUID;

@Entity
public class Sensor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "uuid-binary")
    private UUID id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "maximumValue", nullable = false)
    private int maximumValue;

    @OneToOne
    private Device device;

    public Sensor() {
    }

    public Sensor(UUID id, String description, int maximumValue) {
        this.id = id;
        this.description = description;
        this.maximumValue = maximumValue;
    }

    public Sensor(UUID id, String description, int maximumValue, Device device) {
        this.id = id;
        this.description = description;
        this.maximumValue = maximumValue;
        this.device = device;
    }

    public UUID getId() {
        return id;
    }

    //@Value("${sensorId}")
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

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
