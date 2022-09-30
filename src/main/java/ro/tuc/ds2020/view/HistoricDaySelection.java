package ro.tuc.ds2020.view;

import ro.tuc.ds2020.service.ApplianceService;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class HistoricDaySelection extends JFrame {

    JComboBox comboBox;
    JLabel label;
    JButton getChartButton;

    public HistoricDaySelection(ApplianceService applianceService, UUID id) {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(300, 230));
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        label = new JLabel("Number of Days");
        this.add(label);

        Integer[] days = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        comboBox = new JComboBox(days);
        comboBox.setEditable(true);

        this.add(comboBox);

        getChartButton = new JButton("Confirm");
        getChartButton.addActionListener(e -> {
            Integer integer = (Integer) comboBox.getSelectedItem();
            new HistoricalChart(applianceService, id, integer.intValue()).setVisible(true);
        });
        this.add(getChartButton);

        this.pack();
        this.setVisible(true);
    }
}