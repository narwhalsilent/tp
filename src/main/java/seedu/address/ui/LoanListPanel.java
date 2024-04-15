package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Loan;
import seedu.address.model.tabindicator.TabIndicator;

/**
 * Panel containing the list of persons.
 */
public class LoanListPanel extends UiPart<Region> {
    private static final String FXML = "LoanListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(LoanListPanel.class);

    @FXML
    private ListView<Loan> loanListView;

    //    private final BooleanProperty isShowLoaneeInfo;
    private final ObjectProperty<TabIndicator> tabIndicator;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public LoanListPanel(ObservableList<Loan> loanList, ObjectProperty<TabIndicator> tabIndicator) {
        super(FXML);
        loanListView.setItems(loanList);
        loanListView.setCellFactory(listView -> new LoanListViewCell());
        this.tabIndicator = tabIndicator;
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class LoanListViewCell extends ListCell<Loan> {
        @Override
        protected void updateItem(Loan loan, boolean empty) {
            super.updateItem(loan, empty);

            if (empty || loan == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new LoanCard(loan, getIndex() + 1,
                        tabIndicator.getValue().getIsShowLoaneeInfo()).getRoot());
            }
        }
    }

}
