package seedu.address.ui;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import seedu.address.model.person.Analytics;

public class AnalyticsPanel extends UiPart<Region> {
    private static final String FXML = "AnalyticsPanel.fxml";

    @FXML
    private PieChart pieChart;

    /**
     * Creates a {@code AnalyticsPanel} with the given {@code ObjectProperty}.
     */
    public AnalyticsPanel(ObjectProperty<Analytics> analytics) {
        super(FXML);
        analytics.addListener((observable, oldValue, newValue) -> {
            updateChart(newValue);
        });
    }

    private void updateChart(Analytics analytics) {
        System.out.println("Updated Chart due to change in analytics");
        pieChart.getData().clear();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Overdue", analytics.getNumOverdueLoans()),
                new PieChart.Data("Not Overdue", analytics.getNumLoans() - analytics.getNumOverdueLoans())
        );
        pieChart.setData(pieChartData);
    }
}
