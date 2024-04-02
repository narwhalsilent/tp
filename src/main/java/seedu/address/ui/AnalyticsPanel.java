package seedu.address.ui;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
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

    private static final float MAX_IMPACT = 1000000;

    /**
     * Creates a {@code AnalyticsPanel} with the given {@code ObjectProperty}.
     */
    public AnalyticsPanel(ObjectProperty<DashboardData> dashboardData) {
        super(FXML);
        reliabilityChart.setData(FXCollections.observableArrayList());
        dashboardData.addListener((observable, oldValue, newValue) -> {
            updateChart(newValue);
        });
    }

    private void updateChart(DashboardData data) {
        Analytics analytics = data.getAnalytics();
        ObservableList<PieChart.Data> reliabilityData = FXCollections.observableArrayList(
                new PieChart.Data("Active Loans", analytics.getNumActiveLoans()),
                new PieChart.Data("Overdue Loans", analytics.getNumActiveLoans() - analytics.getNumOverdueLoans())
        );
        reliabilityChart.setData(reliabilityData);

        ObservableList<PieChart.Data> impactData = FXCollections.observableArrayList(
                new PieChart.Data("Impact Index", data.getImpactIndex()),
                new PieChart.Data("Benchmark", 1 - data.getImpactIndex())
        );
        impactChart.setData(impactData);

        ObservableList<PieChart.Data> urgencyData = FXCollections.observableArrayList(
                new PieChart.Data("Urgency Index", data.getUrgencyIndex()),
                new PieChart.Data("Benchmark", 1 - data.getUrgencyIndex())
        );
        urgencyChart.setData(urgencyData);
    }
}
