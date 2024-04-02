package seedu.address.ui;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.analytics.DashboardData;
import seedu.address.model.person.Analytics;


/**
 * Panel containing the analytics of the loan records.
 */
public class AnalyticsPanel extends UiPart<Region> {
    private static final String FXML = "AnalyticsPanel.fxml";

    @FXML
    private PieChart reliabilityChart;

    @FXML
    private PieChart impactChart;

    @FXML
    private PieChart urgencyChart;

    @FXML
    private Label reliabilityIndex;

    @FXML
    private Label impactIndex;

    @FXML
    private Label urgencyIndex;

    /**
     * Creates a {@code AnalyticsPanel} with the given {@code ObjectProperty}.
     */
    public AnalyticsPanel(ObjectProperty<DashboardData> dashboardData) {
        super(FXML);
        initializeCharts();
        reliabilityChart.setData(FXCollections.observableArrayList());
        dashboardData.addListener((observable, oldValue, newValue) -> {
            updateChart(newValue);
        });
    }

    private void initializeCharts() {
        reliabilityChart.setStartAngle(90);
        impactChart.setStartAngle(90);
        urgencyChart.setStartAngle(90);
        reliabilityChart.setLabelsVisible(false);
        impactChart.setLabelsVisible(false);
        urgencyChart.setLabelsVisible(false);
        reliabilityChart.setLegendVisible(false);
        impactChart.setLegendVisible(false);
        urgencyChart.setLegendVisible(false);
    }

    private void updateChart(DashboardData data) {
        Analytics analytics = data.getAnalytics();
        if (analytics.getNumActiveLoans() == 0) {
            reliabilityIndex.setText("No active loans to analyze");
            reliabilityChart.setVisible(false);
        } else {
            reliabilityChart.setVisible(true);
            ObservableList<PieChart.Data> reliabilityData = FXCollections.observableArrayList(
                    new PieChart.Data("Reliability Index", analytics.getPropOverdueLoans()),
                    new PieChart.Data("", 1 - analytics.getPropOverdueLoans())
            );
            reliabilityChart.setData(reliabilityData);
            reliabilityIndex.setText(String.format("%.2f", (1 - analytics.getPropOverdueLoans()) * 100) + "%");
        }


        if (data.getMaxLoanValue() == 0) {
            impactIndex.setText("No loans to analyze");
            impactChart.setVisible(false);
        } else {
            impactChart.setVisible(true);
            ObservableList<PieChart.Data> impactData = FXCollections.observableArrayList(
                    new PieChart.Data("Impact Index", data.getImpactIndex()),
                    new PieChart.Data("", 1 - data.getImpactIndex())
            );
            impactChart.setData(impactData);
            impactIndex.setText(String.format("%.2f", data.getImpactIndex() * 100) + "%");
        }


        if (data.getUrgencyIndex() == null) {
            urgencyIndex.setText("No loans to analyze");
            urgencyChart.setVisible(false);
        } else {
            urgencyChart.setVisible(true);
            ObservableList<PieChart.Data> urgencyData = FXCollections.observableArrayList(
                    new PieChart.Data("Urgency Index", data.getUrgencyIndex()),
                    new PieChart.Data("", 1 - data.getUrgencyIndex())
            );
            urgencyChart.setData(urgencyData);
            urgencyIndex.setText(String.format("%.2f", data.getUrgencyIndex() * 100) + "%");
        }
    }
}
