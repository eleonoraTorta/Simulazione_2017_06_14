/**
 * Sample Skeleton for 'Artsmia.fxml' Controller Class
 */

package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Exhibition;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxAnno"
    private ChoiceBox<Integer> boxAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtFieldStudenti"
    private TextField txtFieldStudenti; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void handleCreaGrafo(ActionEvent event) {
    	
    	int anno = boxAnno.getValue();
    	if( anno  < 1844){
    		txtResult.appendText("Errore: selezionare un anno");
    		return;
    	}
    	
    	// creo il grafo
    	model.creaGrafo(anno);

    	// verifico se il grafo � fortemente connesso
    	if( model.fortementeConnesso() == true){
    		txtResult.appendText("Il grafo � fortemente connesso \n");
    	} else{
    		txtResult.appendText("Il grafo  NON � fortemente connesso\n");
    	}
    	
    	// mostra con il maggior numero di opere in esposizione
    	Exhibition mostra = model.getMostraPiuPiena();
    	txtResult.appendText("La mostra con il maggior numero di opere in esposizione � " + mostra.getNome() + " con " + mostra.getListaOggetti().size() + "\n");

    }

    @FXML
    void handleSimula(ActionEvent event) {
    	// prendo il numero di studenti con cui fare la simulazione
    	String valore =  txtFieldStudenti.getText();
    	if( valore == null){
    		txtResult.appendText("Errore: inserire un numero di studenti\n");
    		return;
    	}
    	
    	int N =0;
    	try{
    		 N = Integer.parseInt(valore);
    	} catch (NumberFormatException e){
    		txtResult.appendText("Errore: devi inserire un  carattere numerico \n");
    		return;
    	}
    	
    	// prendo l'anno della mostra iniziale
    	int anno = boxAnno.getValue();
    	if( anno  < 1844){
    		txtResult.appendText("Errore: selezionare un anno");
    		return;
    	}
    	
   // 	 model.simula(N, anno);
    	 model.scegliMostraIniziale(anno);
    	 
    	

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtFieldStudenti != null : "fx:id=\"txtFieldStudenti\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		
		boxAnno.getItems().addAll(model.getAnni());
		
		
	}
}
