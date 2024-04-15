package seedu.address.ui;

import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tabindicator.TabIndicator;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private LoanListPanel loanListPanel;

    @FXML
    private StackPane loanListPanelPlaceholder;

    @FXML
    private AnalyticsPanel analyticsPanel;

    @FXML
    private StackPane analyticsPanelPlaceholder;
    @FXML
    private VBox loanList;
    @FXML
    private VBox analytics;
    @FXML
    private VBox personList;
    private ObjectProperty<TabIndicator> tabIndicator;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        initializeLocalListeners();
        // Initial value of isLoansTab is false by default
        assert (!this.tabIndicator.getValue().getIsLoansTab());
        // Initial value of isAnalyticsTab is false by default
        assert (!this.tabIndicator.getValue().getIsAnalyticsTab());
        personListPanel = new PersonListPanel(logic.getFilteredPersonList());

        loanListPanel = new LoanListPanel(logic.getSortedLoanList(), this.tabIndicator);
        analyticsPanel = new AnalyticsPanel(logic.getAnalytics());
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        initializePlaceholderSettings();

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    private void initializePlaceholderSettings() {
        VBox.setVgrow(personList, Priority.ALWAYS);
        VBox.setVgrow(loanList, Priority.NEVER);
        VBox.setVgrow(analytics, Priority.NEVER);
    }

    private void initializeLocalListeners() {
        this.tabIndicator = logic.getTabIndicator();

        this.tabIndicator.addListener((observable, oldValue, newValue) -> {
            toggleTabs();
        });


    }

    private void activateDualPanelView() {
        clearAllPlaceholders();
        VBox.setVgrow(personList, Priority.ALWAYS);
        VBox.setVgrow(loanList, Priority.NEVER);
        VBox.setVgrow(analytics, Priority.NEVER);
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
        VBox.setVgrow(loanList, Priority.ALWAYS);
        loanListPanelPlaceholder.getChildren().add(loanListPanel.getRoot());
        VBox.setVgrow(analytics, Priority.NEVER);
        personListPanelPlaceholder.setMaxHeight(105);
        personListPanelPlaceholder.setMinHeight(105);
        VBox.setVgrow(personList, Priority.NEVER);
    }

    private void activatePersonListOnlyView() {
        clearAllPlaceholders();
        personListPanelPlaceholder.setMaxHeight(Double.POSITIVE_INFINITY);
        VBox.setVgrow(personList, Priority.ALWAYS);
        VBox.setVgrow(loanList, Priority.NEVER);
        VBox.setVgrow(analytics, Priority.NEVER);
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }

    private void activateAnalyticsView() {
        clearAllPlaceholders();
        VBox.setVgrow(analytics, Priority.ALWAYS);
        VBox.setVgrow(personList, Priority.NEVER);
        VBox.setVgrow(loanList, Priority.NEVER);
        personListPanelPlaceholder.setMinHeight(0);
        analyticsPanelPlaceholder.getChildren().add(analyticsPanel.getRoot());
    }

    private void activateLoanListOnlyView() {
        clearAllPlaceholders();
        VBox.setVgrow(loanList, Priority.ALWAYS);
        VBox.setVgrow(personList, Priority.NEVER);
        VBox.setVgrow(analytics, Priority.NEVER);
        loanListPanelPlaceholder.getChildren().add(loanListPanel.getRoot());
        personListPanelPlaceholder.setMinHeight(0);
    }

    private void toggleTabs() {
        // At most one of these can be active at a time
        assert (!(this.tabIndicator.getValue().getIsLoansTab() && this.tabIndicator.getValue().getIsAnalyticsTab()));

        if (this.tabIndicator.getValue().getIsPersonTab() && this.tabIndicator.getValue().getIsLoansTab()) {
            activateDualPanelView();
        } else if (this.tabIndicator.getValue().getIsPersonTab()) {
            activatePersonListOnlyView();
        } else if (this.tabIndicator.getValue().getIsLoansTab()) {
            activateLoanListOnlyView();
        } else {
            activateAnalyticsView();
        }
    }

    private void clearAllPlaceholders() {
        personListPanelPlaceholder.getChildren().clear();
        VBox.setVgrow(personList, Priority.NEVER);
        loanListPanelPlaceholder.getChildren().clear();
        VBox.setVgrow(loanList, Priority.NEVER);
        analyticsPanelPlaceholder.getChildren().clear();
        VBox.setVgrow(analytics, Priority.NEVER);
    }


    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("An error occurred while executing command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
