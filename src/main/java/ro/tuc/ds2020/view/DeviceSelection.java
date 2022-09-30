package ro.tuc.ds2020.view;

import ro.tuc.ds2020.service.ApplianceService;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class DeviceSelection extends JFrame {

    JComboBox comboBox1;
    JComboBox comboBox2;
    JLabel label1;
    JLabel label2;
    JButton getChartButton;

    public DeviceSelection(ApplianceService applianceService, UUID id) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(300, 230));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        label1 = new JLabel("Select Device");
        this.add(label1);

        String[] devices = applianceService.getDevicesByClient(id);

        comboBox1 = new JComboBox(devices);
        comboBox1.setEditable(true);

        this.add(comboBox1);

        label2 = new JLabel("Select Program Duration (hours)");
        this.add(label2);

        Integer[] hours = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        comboBox2 = new JComboBox(hours);
        comboBox2.setEditable(true);

        this.add(comboBox2);

        getChartButton = new JButton("Confirm");
        getChartButton.addActionListener(e -> {
            String device = (String) comboBox1.getSelectedItem();
            Integer duration = (Integer) comboBox2.getSelectedItem();
            new OptimalChart(applianceService, id, device, duration.intValue()).setVisible(true);

        });
        this.add(getChartButton);
        this.pack();
        this.setVisible(true);
    }
}