package ro.tuc.ds2020.view;

import java.io.File;
import java.io.IOException;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Random;
import java.util.UUID;
import javax.swing.JFrame;
import javax.swing.JPanel;

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

public class HistoricalChart extends JFrame {

    public HistoricalChart(ApplianceService applianceService, UUID id, int days) {
        super("XY Line Chart");

        JPanel chartPanel = createChartPanel(applianceService, id, days);
        add(chartPanel, BorderLayout.CENTER);

        setSize(640, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel createChartPanel(ApplianceService applianceService, UUID id, int days) {
        String chartTitle = ("Historical " + days + " days Chart");
        String xAxisLabel = "Hours";
        String yAxisLabel = "kW";

        XYDataset dataset = createDataset(applianceService, id, days);

        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);

        customizeChart(chart, days);

        File imageFile = new File("HistoricalChart.png");
        int width = 640;
        int height = 480;

        try {
            ChartUtilities.saveChartAsPNG(imageFile, chart, width, height);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return new ChartPanel(chart);
    }

    private XYDataset createDataset(ApplianceService applianceService, UUID id, int days) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries[] series = new XYSeries[days];
        for (int i = 0; i < days; i++) {
            series[i] = new XYSeries("Energy Consumption day:" + i);
        }

        Double[][] values = applianceService.getHistoricalChart(id, days);

//        for (int i = 0; i < days; i++) {
//            for (int j = 0; j < 24; j++) {
//                System.out.println(values[i][j]);
//            }
//        }

        for (int i = 0; i < days; i++) {
            for (double j = 0; j <= 23.0; j += 1) {
                series[i].add(j, values[i][(int) j]);
            }
        }

        for (int i = 0; i < days; i++) {
            dataset.addSeries(series[i]);
        }
        return dataset;
    }

    private void customizeChart(JFreeChart chart, int days) {
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        Random rand = new Random();
        for (int i = 0; i < days; i++) {
            renderer.setSeriesPaint(i, new Color(rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
        }

        for (int i = 0; i < days; i++) {
            renderer.setSeriesStroke(i, new BasicStroke(days - i + 1.0f));
        }

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