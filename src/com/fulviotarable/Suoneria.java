package com.fulviotarable;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Fulvio
 */
public class Suoneria 
{
// Definisce le variabili usate dal sistema audio    
    
    public static Mixer mixer;
    public static Clip clip;
    
//    public static void main(String[] args)
//    {

public void suona()
{
    
    Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
     
// Ottiene un elenco dei dispositivi audio presenti nel sistema     
/*
     for(Mixer.Info info : mixInfos)
     {
     System.out.println(info.getName() + "---" + info.getDescription());
     }
*/     
// Seleziona il dispositivo di riproduzione audio di default
     
     mixer = AudioSystem.getMixer(mixInfos[0]);
     
     DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
     try { clip = (Clip)mixer.getLine(dataInfo); }
     catch (LineUnavailableException lue){ lue.printStackTrace(); }

// Carica il file audio da riprodurre    

     try
     {
        URL soundURL = Suoneria.class.getResource("alarm.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
        clip.open(audioStream);
     }
     
// Intercetta eventuali errori di gestione della riproduzione     
     
     catch (NullPointerException npe){ npe.printStackTrace();}
     
     catch (LineUnavailableException lue){ lue.printStackTrace();}
     catch (UnsupportedAudioFileException uafe) { uafe.printStackTrace();}
     catch (IOException ioe) { ioe.printStackTrace();}

// Avvia la riproduzione del file audio caricato     
     
     clip.start();

     do
     {
         try { Thread.sleep(50); }
         catch (InterruptedException ie) {ie.printStackTrace(); }
    } while (clip.isActive());   
//    }
    }
}