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
public class RecordStudente {
    /*
    -)id stringa lunga 2
    -)nome stringa lunga 20
    -)cognome stringa lunga 20
    -)classe stringa lunga 6
    quindi ogni record occupa (2+20+20+6)*2 = 96 byte
    */
    public static final int DIM_RECORD = 96;
    
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

    private String aggiustaNomeCognome(String s) {
        String aggiustata = s;
        if (s.length() < 20) {
            for (int i = 0; i < (20 - s.length()); i++) {
                aggiustata += "*";
            }
            return aggiustata;
        } else if (s.length() > 20) {
            aggiustata = s.substring(0, 20);
            return aggiustata;
        }
        return s;
    }

    private String aggiustaClasse(String s) {
        String aggiustata = s;
        if (s.length() < 6) {
            for (int i = 0; i < (6 - s.length()); i++) {
                aggiustata += "*";
            }
            return aggiustata;
        } else if (s.length() > 6) {
            aggiustata = s.substring(0, 6);
            return aggiustata;
        }
        return s;
    }
    
    public void scriviRecord(Studente s) {
        try {
            RandomAccessFile file = new RandomAccessFile("studenti.dat", "rw");
            int nRecord = (int) (file.length() / DIM_RECORD);
            file.seek(nRecord * DIM_RECORD);

            file.writeChars(aggiustaID(s.getId()));
            file.writeChars(aggiustaNomeCognome(s.getNome()));
            file.writeChars(aggiustaNomeCognome(s.getCognome()));
            file.writeChars(aggiustaClasse(s.getClasse()));

            file.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File studenti.dat non trovato");
        } catch (IOException ex) {
            System.out.println("Problema in lettura-scrittura file studenti.dat");
        }
    }

    public void scriviRecordInPosizione(Studente s, int posizione) {
        try {
            RandomAccessFile file = new RandomAccessFile("studenti.dat", "rw");
            file.seek((long) posizione * DIM_RECORD);

            file.writeChars(aggiustaID(s.getId()));
            file.writeChars(aggiustaNomeCognome(s.getNome()));
            file.writeChars(aggiustaNomeCognome(s.getCognome()));
            file.writeChars(aggiustaClasse(s.getClasse()));

            file.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File studenti.dat non trovato");
        } catch (IOException ex) {
            System.out.println("Problema in lettura-scrittura file studenti.dat");
        }
    }

    public Studente leggiRecord(int posizione) {
        Studente s = null;
        try {
            RandomAccessFile file = new RandomAccessFile("studenti.dat", "r");
            int nRecord = (int) (file.length() / DIM_RECORD);

            if (posizione >= 0 && posizione < nRecord) {
                file.seek((long) posizione * DIM_RECORD);

                String id = "";
                for (int i = 0; i < 2; i++) {
                    id += file.readChar();
                }
                String nome = "";
                for (int i = 0; i < 20; i++) {
                    nome += file.readChar();
                }
                String cognome = "";
                for (int i = 0; i < 20; i++) {
                    cognome += file.readChar();
                }
                String classe = "";
                for (int i = 0; i < 6; i++) {
                    classe += file.readChar();
                }

                s = new Studente(
                    id.replace("*", "").trim(),
                    nome.replace("*", "").trim(),
                    cognome.replace("*", "").trim(),
                    classe.replace("*", "").trim()
                );
            }
            file.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File studenti.dat non trovato");
        } catch (IOException ex) {
            System.out.println("Problema in lettura-scrittura file studenti.dat");
        }
        return s;
    }

    public ArrayList<Studente> leggiTutti() {
        ArrayList<Studente> lista = new ArrayList<>();
        try {
            RandomAccessFile file = new RandomAccessFile("studenti.dat", "r");
            int nRecord = (int) (file.length() / DIM_RECORD);

            for (int recordAttuale = 0; recordAttuale < nRecord; recordAttuale++) {
                file.seek((long) recordAttuale * DIM_RECORD);

                String id = "";
                for (int i = 0; i < 2; i++) {
                    id += file.readChar();
                }
                String nome = "";
                for (int i = 0; i < 20; i++) {
                    nome += file.readChar();
                }
                String cognome = "";
                for (int i = 0; i < 20; i++) {
                    cognome += file.readChar();
                }
                String classe = "";
                for (int i = 0; i < 6; i++) {
                    classe += file.readChar();
                }

                lista.add(new Studente(
                    id.replace("*", "").trim(),
                    nome.replace("*", "").trim(),
                    cognome.replace("*", "").trim(),
                    classe.replace("*", "").trim()
                ));
            }
            file.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File studenti.dat non trovato");
        } catch (IOException ex) {
            System.out.println("Problema in lettura-scrittura file studenti.dat");
        }
        return lista;
    }

    public int cercaPerID(String id) {
        int posizioneTrovata = -1;
        String idCercato = aggiustaID(id);
        try {
            RandomAccessFile file = new RandomAccessFile("studenti.dat", "r");
            int nRecord = (int) (file.length() / DIM_RECORD);

            for (int recordAttuale = 0; recordAttuale < nRecord; recordAttuale++) {
                file.seek((long) recordAttuale * DIM_RECORD);

                String idLetto = "";
                for (int i = 0; i < 2; i++) {
                    idLetto += file.readChar();
                }

                if (idLetto.equals(idCercato)) {
                    posizioneTrovata = recordAttuale;
                    break;
                }
            }
            file.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File studenti.dat non trovato");
        } catch (IOException ex) {
            System.out.println("Problema in lettura-scrittura file studenti.dat");
        }
        return posizioneTrovata;
    }

    public int contaRecord() {
        int nRecord = 0;
        try {
            RandomAccessFile file = new RandomAccessFile("studenti.dat", "rw");
            nRecord = (int) (file.length() / DIM_RECORD);
            file.close();
        } catch (IOException ex) {
            System.out.println("Problema con il file studenti.dat");
        }
        return nRecord;
    }
}