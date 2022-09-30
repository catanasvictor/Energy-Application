package ro.tuc.ds2020.view;

import ro.tuc.ds2020.service.ApplianceService;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class ClientPage {
    private JButton getHistoricalChartButton;
    private JButton getAverageChartButton;
    private JButton computeBestTimeButton;
    private JPanel panel1;
    private JFrame frame;
    private ApplianceService applianceService;

    public ClientPage(ApplianceService applianceService, UUID id) {

        frame = new JFrame("Client Form");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 230));
        frame.add(panel1);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        getHistoricalChartButton.addActionListener(e -> new HistoricDaySelection(applianceService, id).setVisible(true));

        getAverageChartButton.addActionListener(e -> new AverageChart(applianceService, id).setVisible(true));

        computeBestTimeButton.addActionListener(e -> new DeviceSelection(applianceService, id).setVisible(true));

    }
}
