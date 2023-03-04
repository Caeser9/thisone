package jobtopia.GUI;

import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import jobtopia.Entities.Candidature;
import jobtopia.Entities.EtatEnum;
import jobtopia.Services.ServiceCandidature;

/**
 *
 * @author HP
 */
public class ClientHomeCandidatureFXMLController implements Initializable {

    @FXML
    private TextField id;
    @FXML
    private TextField offID;
    @FXML
    private TextField freeID;
    @FXML
    private Label result;
    @FXML
    private TableColumn<Candidature, Integer> showID;
    @FXML
    private TableColumn<Candidature, String> etatcol;
    @FXML
    private TableColumn<Candidature, String> showoffreTitle;
    @FXML
    private TableColumn<Candidature, String> showoffreDescription;
    @FXML
    private TableColumn<Candidature, String> showfreelancerName;
    @FXML
    private TableColumn<Candidature, String> showfreelancerLName;
    @FXML
    private TableColumn<Candidature, String> showfreelancerEmail;
    @FXML
    private TableColumn<Candidature, String> showfreelancerProfession;
    @FXML
    private TableView<Candidature> Candidtable;
    @FXML
    private TextField upID;
    @FXML
    private TextField upEtat;
    @FXML
    private TextField upOff;
    @FXML
    private TextField upFree;
    @FXML
    private TextField delID;
    @FXML
    private ComboBox<String> etatChoice;
    @FXML
    private Label message;
    @FXML
    private Label candidID;
    @FXML
    private TableColumn<Candidature, Void> modifierCol;
    @FXML
    private TableColumn<Candidature, Void> supprimerCol;
    @FXML
    TableColumn<Candidature, Void> archiveCol;
    @FXML
    private Button chercherCatg;
    @FXML
    private Label titreErrorLabel;
    @FXML
    private Label descpErrorLabel;
    @FXML
    private Label CatgErrorLabel;
    @FXML
    private Label CreationErrorLabel;
    @FXML
    private Label limiteErrorLabel;
    @FXML
    private Label IDErrorLabel;
    @FXML
    private TextField search;
    @FXML

    Connection cnx;
    Statement stm;

    ServiceCandidature sc = new ServiceCandidature();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Candidature> candidatures = FXCollections.observableArrayList(sc.afficherClient());

        //Data from DB
        EnumSet.allOf(EtatEnum.class).stream().forEach(s -> etatChoice.getItems().add(s.toString()));
        ServiceCandidature sc = new ServiceCandidature();

        Candidtable.setItems(candidatures);
        showID.setCellValueFactory(new PropertyValueFactory<>("id"));
        etatcol.setCellValueFactory(new PropertyValueFactory<>("etatID"));
        showoffreTitle.setCellValueFactory(new PropertyValueFactory<>("offreTitle"));
        showoffreDescription.setCellValueFactory(new PropertyValueFactory<>("offreDescription"));
        showfreelancerName.setCellValueFactory(new PropertyValueFactory<>("freelancerName"));
        showfreelancerLName.setCellValueFactory(new PropertyValueFactory<>("freelancerLName"));
        showfreelancerEmail.setCellValueFactory(new PropertyValueFactory<>("freelancerEmail"));
        showfreelancerProfession.setCellValueFactory(new PropertyValueFactory<>("freelancerProfession"));
        
        //Recherche 
        FilteredList<Candidature> filteredList = new FilteredList<>(candidatures, p -> true);
        Candidtable.setItems(filteredList);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(Candidature -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (Candidature.getOffreTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                /*if (Candidature.getOffreCategorie().toLowerCase().contains(lowerCaseFilter)){
                    return true;
                }*/
                return false;
            });
        });
        //Delete+update buttons
        modifierCol.setCellFactory(new Callback<TableColumn<Candidature, Void>, TableCell<Candidature, Void>>() {
            @Override
            public TableCell<Candidature, Void> call(final TableColumn<Candidature, Void> param) {
                final TableCell<Candidature, Void> cell = new TableCell<Candidature, Void>() {

                    private final Button btn = new Button("Modifier");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            // Récupérer la candidature correspondante
                            Candidature candidature = getTableView().getItems().get(getIndex());

                            // Ouvrir la fenêtre de modification pour cette candidature
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientModificationCandidatureFXML.fxml"));
                            Parent root = null;
                            try {
                                root = loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(ClientHomeCandidatureFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            ClientModificationCandidatureFXMLController controller = loader.getController();
                            controller.setCandidature(candidature);
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.showAndWait();

                            // Rafraîchir la table des candidatures
                            Candidtable.setItems(FXCollections.observableArrayList(sc.afficherClient()));
                        });
                    }

                    // Cette méthode updateItem() sera appelée pour chaque ligne de la tableOffres,
                    // ce qui permettra de mettre un bouton "Modifier" pour chaque ligne
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        });
        supprimerCol.setCellFactory(new Callback<TableColumn<Candidature, Void>, TableCell<Candidature, Void>>() {
            @Override
            public TableCell<Candidature, Void> call(final TableColumn<Candidature, Void> param) {
                final TableCell<Candidature, Void> cell = new TableCell<Candidature, Void>() {

                    private final Button btn = new Button("Supprimer");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Candidature candidature = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation de la suppression");
                            alert.setHeaderText("Voulez-vous vraiment supprimer cette offre ?");
                            alert.setContentText("L'offre sera définitivement supprimée.");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                sc.supprimer(candidature);
                                Candidtable.setItems(FXCollections.observableArrayList(sc.afficherClient()));
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        });
        archiveCol.setCellFactory(new Callback<TableColumn<Candidature, Void>, TableCell<Candidature, Void>>() {
            @Override
            public TableCell<Candidature, Void> call(final TableColumn<Candidature, Void> param) {
                final TableCell<Candidature, Void> cell = new TableCell<Candidature, Void>() {

                    private final Button btn = new Button("Archiver");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            Candidature candidature = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation de l'archivation");
                            alert.setHeaderText("Voulez-vous vraiment archiver cette candidature ?");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                sc.archiver(candidature);
                                Candidtable.setItems(FXCollections.observableArrayList(sc.afficherClient()));
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        });
    }

    @FXML

    private void afficher(ActionEvent event) {
        ServiceCandidature sc = new ServiceCandidature();
        //Convention c = new Convention();
        //sc.afficher();
        //lresultat.setText(sc.afficher().toString());
        ObservableList<Candidature> candidatures = FXCollections.observableArrayList(sc.afficherClient());
        Candidtable.setItems(candidatures);
        showID.setCellValueFactory(new PropertyValueFactory<>("id"));
        etatcol.setCellValueFactory(new PropertyValueFactory<>("etatID"));
        showoffreTitle.setCellValueFactory(new PropertyValueFactory<>("offreTitle"));
        showoffreDescription.setCellValueFactory(new PropertyValueFactory<>("offreDescription"));
        showfreelancerName.setCellValueFactory(new PropertyValueFactory<>("freelancerName"));
        showfreelancerLName.setCellValueFactory(new PropertyValueFactory<>("freelancerLName"));
        showfreelancerEmail.setCellValueFactory(new PropertyValueFactory<>("freelancerEmail"));
        showfreelancerProfession.setCellValueFactory(new PropertyValueFactory<>("freelancerProfession"));
    }

    @FXML
    private boolean ajouter(ActionEvent event) {

        String titreOf = offID.getText();
        String description = freeID.getText();

        //Controle de saisie des champs 
        boolean isValid = true;
        if (titreOf == null || titreOf.trim().isEmpty()) {
            titreErrorLabel.setText("IL faut saisir le titre!");
            isValid = false;
        }
        if (description == null || description.trim().isEmpty()) {
            descpErrorLabel.setText("La description doit-etre rempli!!!");
            isValid = false;
        }

        if (etatChoice.getValue() == null) {
            CatgErrorLabel.setText("La catégorie ne peut pas être vide.");
            isValid = false;
        }

        if (isValid) {
            Candidature o = new Candidature();
            o.setEtatID(etatChoice.getValue());
            o.setOffreTitle(offID.getText());
            o.setFreelancerName(freeID.getText());

            sc.add(o);
            Candidtable.getItems().setAll(sc.afficherFreelancer());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Candidature ajoutée");
            alert.setHeaderText("Candidature ajoutée avec succès");
            alert.showAndWait();
        }

        return isValid;
    }

    @FXML
    private void update(ActionEvent event) {
        ServiceCandidature sc = new ServiceCandidature();
        Candidature c = new Candidature(upEtat.getText(), upOff.getText(), upFree.getText());
        sc.modifier(c);
    }

    @FXML
    private void archiver(ActionEvent event) {
        ServiceCandidature sc = new ServiceCandidature();
        Candidature c = new Candidature(Integer.parseInt(delID.getText()));
        sc.archiver(c);

    }

    @FXML
    private void supprimer(ActionEvent event) {
        ServiceCandidature sc = new ServiceCandidature();
        Candidature c = new Candidature(Integer.parseInt(delID.getText()));
        sc.supprimer(c);

    }

    /*@FXML
    private void showId(ActionEvent event){
         ServiceCandidature s = new ServiceCandidature();
        candidID.setText(s.affciheID().toString());
    }*/
    @FXML
    private void restaure(ActionEvent event) {
        ServiceCandidature sc = new ServiceCandidature();
        Candidature c = new Candidature(Integer.parseInt(delID.getText()));
        sc.restaure(c);

    }

}
