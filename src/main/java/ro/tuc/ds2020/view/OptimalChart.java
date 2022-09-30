package ro.tuc.ds2020.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import ro.tuc.ds2020.service.ApplianceService;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class OptimalChart extends JFrame {

    public OptimalChart(ApplianceService applianceService, UUID id, String device, int duration) {
        super("XY Line Chart");

        JPanel chartPanel = createChartPanel(applianceService, id, device, duration);
        add(chartPanel, BorderLayout.CENTER);

        setSize(640, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel createChartPanel(ApplianceService applianceService, UUID id, String device, int duration) {
        String chartTitle = "Optimal Energy Consumption Chart";
        String xAxisLabel = "Hours";
        String yAxisLabel = "kW";

        XYDataset dataset = createDataset(applianceService, id, device, duration);

        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);

        customizeChart(chart);

        File imageFile = new File("OptimalChart.png");
        int width = 640;
        int height = 480;

        try {
            ChartUtilities.saveChartAsPNG(imageFile, chart, width, height);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return new ChartPanel(chart);
    }

    private XYDataset createDataset(ApplianceService applianceService, UUID id, String device, int duration) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Optimal Energy Consumption");

        Double[] averageValues = applianceService.getAverageChart(id);

        Double maxDeviceConsumption = applianceService.getMaxDeviceConsumption(device);
        System.out.println("Device MAX Energy Consumption:" + maxDeviceConsumption);

        int startTime = applianceService.getStartTime(id, duration);

        for (int i = startTime; i < (startTime + duration); i++) {
            averageValues[i] += maxDeviceConsumption;
        }

        for (double i = 0; i < 24.0; i++) {
            series1.add(i, averageValues[(int) i]);
        }

        dataset.addSeries(series1);
        return dataset;
    }

    private void customizeChart(JFreeChart chart) {
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.GREEN);
        renderer.setSeriesStroke(0, new BasicStroke(4.0f));

        plot.setOutlinePaint(Color.BLUE);
        plot.setOutlineStroke(new BasicStroke(2.0f));
        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.DARK_GRAY);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);
    }
}
