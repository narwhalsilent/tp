package seedu.address.ui;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.Region;
import seedu.address.model.person.Analytics;

/**
 * Panel containing the analytics of the loan records.
 */
public class AnalyticsPanel extends UiPart<Region> {
    private static final String FXML = "AnalyticsPanel.fxml";

    @FXML
    private PieChart pieChart;

    /**
     * Creates a {@code AnalyticsPanel} with the given {@code ObjectProperty}.
     */
    public AnalyticsPanel(ObjectProperty<Analytics> analytics) {
        super(FXML);
        pieChart.setData(FXCollections.observableArrayList());
        analytics.addListener((observable, oldValue, newValue) -> {
            updateChart(newValue);
        });
    }

    private void updateChart(Analytics analytics) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Active Loans", analytics.getNumActiveLoans()),
                new PieChart.Data("Overdue Loans", analytics.getNumOverdueLoans())
        );
        pieChart.setData(pieChartData);
    }
}
