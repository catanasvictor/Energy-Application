package ro.tuc.ds2020.service;
import java.util.UUID;

public interface ApplianceService {
    UUID login(String username, String password);
    Double[] getAverageChart(UUID id);
    Double[][] getHistoricalChart(UUID id, int days);
    String[] getDevicesByClient(UUID id);
    int getStartTime(UUID id, int duration);
    Double getMaxDeviceConsumption(String device);
}
