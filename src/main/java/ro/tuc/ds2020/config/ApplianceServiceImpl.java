package ro.tuc.ds2020.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.Record;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.services.DeviceService;
import ro.tuc.ds2020.services.RecordService;
import ro.tuc.ds2020.services.UserService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class ApplianceServiceImpl implements ApplianceService {

    private final RecordService recordService;
    private final DeviceService deviceService;
    private final UserService userService;

    @Autowired
    public ApplianceServiceImpl(RecordService recordService, DeviceService deviceService, UserService userService) {
        this.recordService = recordService;
        this.deviceService = deviceService;
        this.userService = userService;
    }

    @Override
    public UUID login(String username, String password) {
        User user = userService.findByUsername(username);
        if (user != null && password.equals(user.getPassword())) {
            return user.getId();
        } else {
            return null;
        }
    }

    @Override
    public Double[] getAverageChart(UUID id) {
        Double[] energyConsumption = new Double[24];
        for (int i = 0; i < 24; i++) {
            energyConsumption[i] = 0.0;
        }

        Date dateLimit = new Date();

        LocalDateTime localDateTime = LocalDateTime.ofInstant(dateLimit.toInstant(), ZoneId.systemDefault());
        localDateTime = localDateTime.minusDays(7);
        Date oldDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());

        List<Record> recordList = recordService.findAll();
        for (Record record : recordList) {

            if (record.getSensor().getDevice().getUser().getId().equals(id)) {
                Date recordDate = record.getTimestamp();
                if (recordDate.after(oldDate)) {
                    energyConsumption[record.getTimestamp().getHours()] += record.getEnergyConsumption();
                }
            }
        }
        for (int i = 0; i < 24; i++) {
            energyConsumption[i] /= 7;
        }
        return energyConsumption;
    }

    @Override
    public Double[][] getHistoricalChart(UUID id, int days) {
        Double[][] energyConsumption = new Double[days][24];
        for (int i = 0; i < days; i++) {
            for (int j = 0; j < 24; j++) {
                energyConsumption[i][j] = 0.0;
            }
        }

        Date now = new Date();

        List<Record> recordList = recordService.findAll();

        while (days > 0) {

            for (Record record : recordList) {
                if (record.getSensor().getDevice().getUser().getId().equals(id)) {
                    Date recordDate = record.getTimestamp();
                    Calendar cal1 = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    cal1.setTime(now);
                    cal1.add(Calendar.DATE, -1);
                    cal2.setTime(recordDate);
                    if (cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
                        energyConsumption[days - 1][record.getTimestamp().getHours()] += record.getEnergyConsumption();
                    }
                }
            }
            LocalDateTime localDateTime = LocalDateTime.ofInstant(now.toInstant(), ZoneId.systemDefault());
            localDateTime = localDateTime.minusDays(1);
            now = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            days--;
        }
        return energyConsumption;
    }

    @Override
    public String[] getDevicesByClient(UUID id) {

        List<Device> devices = userService.findDevicesByUser(id);
        String[] names = new String[devices.size()];

        int count = 0;
        for (Device device : devices) {
            names[count++] = device.getDescription();
        }
        return names;
    }

    @Override
    public int getStartTime(UUID id, int duration) {
        Double[] energyConsumption = getAverageChart(id);
        int start = 0;
        double max;
        double minMax = Double.MAX_VALUE;
        for (int i = 0; i < 24; i++) {
            max = 0.0;
            if (i + duration <= 24) {
                for (int j = i; j < (i + duration); j++) {
                    if (energyConsumption[j] > max) max = energyConsumption[j];
                    //System.out.println("max: " + max);
                }
            }
            if (0.0 < max && max < minMax) {
                minMax = max;
                start = i;
                //System.out.println("MinMax:" + minMax);
            }
        }
        //System.out.println("Start:" + start);
        return start;
    }

    @Override
    public Double getMaxDeviceConsumption(String device) {
        return deviceService.findDeviceByDescription(device).getMaximumEnergyConsumption();
    }
}
