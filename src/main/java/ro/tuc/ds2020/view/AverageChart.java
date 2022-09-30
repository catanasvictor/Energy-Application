package ro.tuc.ds2020.view;

import java.io.File;
import java.io.IOException;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
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

public class AverageChart extends JFrame {

    public AverageChart(ApplianceService applianceService, UUID id) {
        super("XY Line Chart");

        JPanel chartPanel = createChartPanel(applianceService, id);
        add(chartPanel, BorderLayout.CENTER);

        setSize(640, 480);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private JPanel createChartPanel(ApplianceService applianceService, UUID id) {
        String chartTitle = "Weekly Average Chart";
        String xAxisLabel = "Hours";
        String yAxisLabel = "kW";

        XYDataset dataset = createDataset(applianceService, id);

        JFreeChart chart = ChartFactory.createXYLineChart(chartTitle, xAxisLabel, yAxisLabel, dataset);

        customizeChart(chart);

        File imageFile = new File("AverageChart.png");
        int width = 640;
        int height = 480;

        try {
            ChartUtilities.saveChartAsPNG(imageFile, chart, width, height);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return new ChartPanel(chart);
    }

    private XYDataset createDataset(ApplianceService applianceService, UUID id) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Average Energy Consumption");
        Double[] averageValues = applianceService.getAverageChart(id);

        for (double i = 0; i <= 23.0; i += 1) {
            series1.add(i, averageValues[(int) i]);
        }
        dataset.addSeries(series1);
        return dataset;
    }

    private void customizeChart(JFreeChart chart) {
        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.RED);
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