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

    public void scriviRecord(String idStudente, String idGita) {
        if (esisteRelazione(idStudente, idGita)) {
            return;
        }
        try {
         
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

    public int contaRecord() {
        return leggiTutteRelazioni().size();
    }

    private ArrayList<String[]> leggiTutteRelazioni() {
        ArrayList<String[]> lista = new ArrayList<>();
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream("relazioni.dat"));

         
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
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            System.out.println("Problema in lettura file relazioni.dat");
        }
        return lista;
    }
}