package ro.tuc.ds2020.reader;

import ro.tuc.ds2020.entity.Measurement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

public class MeasurementReader {

    public List<Measurement> getMeasurements(UUID id) {
        List<Measurement> measurements = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader("C:\\Users\\kajke\\Desktop\\simulator\\ds2021_30244_catanas_kaj_final_project_simulator\\sensor.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            LocalDateTime localDateTime = LocalDateTime.now();
            //localDateTime = localDateTime.minusDays(3);

            int i = 0;
            while ((line = bufferedReader.readLine()) != null && i <= 12) {
                StringTokenizer stringTokenizer = new StringTokenizer(line);
                if(i==1) {
                    localDateTime = localDateTime.minusDays(3);
                }
                if(i>0){
                localDateTime = localDateTime.plusHours(2);}
                Double measurementValue = Double.valueOf(stringTokenizer.nextToken());
                measurements.add(new Measurement(localDateTime, measurementValue, id));
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return measurements;
    }
}