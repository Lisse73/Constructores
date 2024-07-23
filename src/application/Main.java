package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main extends Application {

    private TableView<ConstructorPoints> table = new TableView<>();
    private ObservableList<ConstructorPoints> data = FXCollections.observableArrayList();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Constructores con Más Puntos");

        // Definir las columnas
        TableColumn<ConstructorPoints, String> constructorNameCol = new TableColumn<>("Constructor");
        constructorNameCol.setCellValueFactory(new PropertyValueFactory<>("constructorName"));
        constructorNameCol.setPrefWidth(400); 

        TableColumn<ConstructorPoints, Number> totalPointsCol = new TableColumn<>("Puntos Totales");
        totalPointsCol.setCellValueFactory(new PropertyValueFactory<>("totalPoints"));
        totalPointsCol.setPrefWidth(200); 

        table.getColumns().addAll(constructorNameCol, totalPointsCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Cargar datos desde la base de datos
        loadDataFromDatabase();

        // Configurar el BorderPane y la escena
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(table);

        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadDataFromDatabase() {
        String jdbcUrl = "jdbc:postgresql://localhost:5432/Formula 1";
        String jdbcUser = "postgres";
        String jdbcPassword = "julio2004";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
             Statement statement = connection.createStatement()) {

            // Consulta SQL
            String sql = "SELECT c.name AS constructor_name, SUM(cr.points) AS total_points " +
                         "FROM constructor_results cr " +
                         "JOIN constructors c ON cr.constructor_id = c.constructor_id " +
                         "GROUP BY c.name " +
                         "ORDER BY total_points DESC";
            ResultSet resultSet = statement.executeQuery(sql);

            
            while (resultSet.next()) {
                String constructorName = resultSet.getString("constructor_name");
                int totalPoints = resultSet.getInt("total_points");

                data.add(new ConstructorPoints(constructorName, totalPoints));
            }
            table.setItems(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
