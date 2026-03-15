/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package busattipichiugita;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author Busatti Mattia e Pichiu Florin
 */
public class RecordRelazione {
    /*
     * -)idStudente stringa lunga 2
     * -)idGita stringa lunga 2
     *
    */
    private String aggiustaID(String s) {
        String aggiustata = s;
        if (s.length() < 2) {
            for (int i = 0; i < (2 - s.length()); i++) {
                aggiustata = "0" + aggiustata;
            }
            return aggiustata;
        } else if (s.length() > 2) {
            aggiustata = s.substring(0, 2);
            return aggiustata;
        }
        return s;
    }

    /**
     * Aggiunge una relazione studente-gita in fondo al file relazioni.dat.
     * Se la coppia esiste già, non viene duplicata.
     * Usa FileOutputStream in modalità append (true) per non sovrascrivere.
     */
    public void scriviRecord(String idStudente, String idGita) {
        if (esisteRelazione(idStudente, idGita)) {
            return;
        }
        try {
            // append = true → aggiunge in fondo senza cancellare il contenuto precedente
            DataOutputStream dos = new DataOutputStream(new FileOutputStream("relazioni.dat", true));

            String idS = aggiustaID(idStudente);
            String idG = aggiustaID(idGita);

            for (int i = 0; i < 2; i++) {
                dos.writeChar(idS.charAt(i));
            }
            for (int i = 0; i < 2; i++) {
                dos.writeChar(idG.charAt(i));
            }

            dos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File relazioni.dat non trovato");
        } catch (IOException ex) {
            System.out.println("Problema in lettura-scrittura file relazioni.dat");
        }
    }

    /**
     * Elimina la relazione tra uno studente e una gita.
     * Poiche' il file e' sequenziale, legge tutti i record in memoria,
     * rimuove la coppia cercata e riscrive l'intero file da capo.
     */
    public void eliminaRecord(String idStudente, String idGita) {
        String idSCercato = aggiustaID(idStudente);
        String idGCercato = aggiustaID(idGita);

        ArrayList<String[]> tutte = leggiTutteRelazioni();

        boolean trovato = false;
        for (int i = 0; i < tutte.size(); i++) {
            if (tutte.get(i)[0].equals(idSCercato) && tutte.get(i)[1].equals(idGCercato)) {
                tutte.remove(i);
                trovato = true;
                break;
            }
        }

        if (!trovato) {
            System.out.println("Relazione non trovata");
            return;
        }

        // append = false → sovrascrive il file dall'inizio con i record rimasti
        try {
            DataOutputStream dos = new DataOutputStream(new FileOutputStream("relazioni.dat", false));

            for (String[] coppia : tutte) {
                for (int i = 0; i < 2; i++) {
                    dos.writeChar(coppia[0].charAt(i));
                }
                for (int i = 0; i < 2; i++) {
                    dos.writeChar(coppia[1].charAt(i));
                }
            }

            dos.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File relazioni.dat non trovato");
        } catch (IOException ex) {
            System.out.println("Problema in lettura-scrittura file relazioni.dat");
        }
    }

    /**
     * Restituisce la lista degli id delle gite a cui partecipa uno studente.
     */
    public ArrayList<String> getGiteStudente(String idStudente) {
        ArrayList<String> gite = new ArrayList<>();
        String idSCercato = aggiustaID(idStudente);

        for (String[] coppia : leggiTutteRelazioni()) {
            if (coppia[0].equals(idSCercato)) {
                gite.add(coppia[1].replace("*", "").trim());
            }
        }
        return gite;
    }

    /**
     * Restituisce la lista degli id degli studenti iscritti a una gita.
     */
    public ArrayList<String> getStudentiGita(String idGita) {
        ArrayList<String> studenti = new ArrayList<>();
        String idGCercato = aggiustaID(idGita);

        for (String[] coppia : leggiTutteRelazioni()) {
            if (coppia[1].equals(idGCercato)) {
                studenti.add(coppia[0].replace("*", "").trim());
            }
        }
        return studenti;
    }

    /**
     * Controlla se esiste gia' una relazione tra uno studente e una gita.
     */
    public boolean esisteRelazione(String idStudente, String idGita) {
        String idSCercato = aggiustaID(idStudente);
        String idGCercato = aggiustaID(idGita);

        for (String[] coppia : leggiTutteRelazioni()) {
            if (coppia[0].equals(idSCercato) && coppia[1].equals(idGCercato)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Restituisce il numero totale di relazioni nel file.
     */
    public int contaRecord() {
        return leggiTutteRelazioni().size();
    }

    /**
     * Legge sequenzialmente tutto il file e restituisce una lista di coppie
     * {idStudente, idGita}. E' il metodo base su cui si appoggiano tutti gli altri.
     */
    private ArrayList<String[]> leggiTutteRelazioni() {
        ArrayList<String[]> lista = new ArrayList<>();
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream("relazioni.dat"));

            // Leggo finche' non finisco il file (EOFException = fine file raggiunta)
            while (true) {
                String idS = "";
                for (int i = 0; i < 2; i++) {
                    idS += dis.readChar();
                }
                String idG = "";
                for (int i = 0; i < 2; i++) {
                    idG += dis.readChar();
                }
                lista.add(new String[]{idS, idG});
            }

        } catch (EOFException ex) {
            // Fine file raggiunta: e' normale, esco dal ciclo
        } catch (FileNotFoundException ex) {
            // Il file non esiste ancora: nessuna relazione presente
        } catch (IOException ex) {
            System.out.println("Problema in lettura file relazioni.dat");
        }
        return lista;
    }
}