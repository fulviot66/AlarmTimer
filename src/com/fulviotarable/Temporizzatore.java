package com.fulviotarable;

import static com.fulviotarable.Suoneria.clip;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @file Temporizzatore.java 
 * @version 1.1 
 * @date 17/07/2017
 * @author Fulvio Tarable
 */
public class Temporizzatore extends Application {
// Dichiarazione delle variabili utilizzate
    long timeMillisStart;
    long timeMillisCurrent;
    long timeSecondsStart;
    long timeSecondsCurrent;
    long secondsElapsed;
    
// Metodo run tramite una classe che implementa l'interfaccia Runnable
    class DoJobRun implements Runnable {
//    private String msg = " sarà occupato per i prossimi ";

    public void run()
    {
// Imposta il temporizzatore a 5 minuti, cioè 300 secondi                       
// Aggiorna la rilevazione           
            do
            {
            timeMillisCurrent = System.currentTimeMillis();
            timeSecondsCurrent = TimeUnit.MILLISECONDS.toSeconds(timeMillisCurrent);
// Calcolo quanto tempo è trascorso
            secondsElapsed = timeSecondsCurrent - timeSecondsStart;
// Se sono trascorsi 5 minuti, ovvero 300 secondi,            
            } 
            while(secondsElapsed < 300);
// Stampo i risultati ottenuti
//            System.out.println("Sono trascorsi " + secondsElapsed + " sec");            
// Attiva la suoneria            
            Suoneria suoneria = new Suoneria();
            suoneria.suona();
//            System.out.println(Thread.currentThread().getName() + " finito");
//        }
    
    }    
}
    
// Definisce l'interfaccia grafica di comando
    @Override
    public void start(Stage primaryStage) {        
        primaryStage.setTitle("TEMPORIZZATORE");
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
// Titolo di Descrizione dell'Applicazione    
        Text scenetitle = new Text("TEMPORIZZATORE fisso da 5'");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));
        grid.add(scenetitle, 0, 0, 2, 1);
// Collocazione 1° pulsante    
        Button btn1 = new Button("VIA");
        HBox hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.BOTTOM_LEFT);
        hbBtn1.getChildren().add(btn1);
        grid.add(hbBtn1, 0, 4);
// Collocazione 2° pulsante    
        Button btn2 = new Button("STOP");       
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn2.getChildren().add(btn2);
        grid.add(hbBtn2, 1, 4);
// Disabilita l'uso del tasto STOP all'avvio dell'applicazione       
        btn2.setDisable(true);
        
// Rimane in attesa che uno dei tasti venga premuto    
        btn1.setOnAction(new EventHandler<ActionEvent>(){
        @Override
            public void handle(ActionEvent e){
//            System.out.println("E' stato premuto il tasto VIA");
//            System.out.println("Temporizzatore AVVIATO");
// Avvia il conteggio
            timeMillisStart = System.currentTimeMillis();
            timeSecondsStart = TimeUnit.MILLISECONDS.toSeconds(timeMillisStart);
// Modifica il colore della scritta
            scenetitle.setFill(Color.ORANGERED);
// Disabilita l'uso del tasto VIA
            btn1.setDisable(true);
// Riabilita l'uso del tasto STOP            
            btn2.setDisable(false);

// Creo il thread indipendente dal thread main
// per realizzare una funzione temporizzatore            
        DoJobRun job = new DoJobRun(); 
        Thread nt = new Thread(job, "***THREAD 1***");
        nt.start();
            }    
        });

        btn2.setOnAction(new EventHandler<ActionEvent>() {
        @Override
            public void handle(ActionEvent e) 
            {
//            System.out.println("E' stato premuto il tasto STOP");
            try {
// Riporta il colore della scritta a NERO 
                scenetitle.setFill(Color.BLACK);
// Blocca la riproduzione audio della suoneria
                clip.stop();
                }
            catch (NullPointerException npe) {}
// Riabilita l'uso del tasto VIA
            btn1.setDisable(false);
// Disabilita l'uso del tasto STOP       
            btn2.setDisable(true);
            }
        });

// Visualizza l'interfaccia di comando    
        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
/*
        System.out.println("Thread principale (" + Thread.currentThread().getName() + ") finito");
*/        
        launch(args);
        
        }    
}