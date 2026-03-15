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
public class RecordGita {
    /*
    -)id stringa lunga 2
    -)nome stringa lunga 20
    -)destinazione stringa lunga 20
    quindi ogni record occupa (2+20+20)*2 = 84 byte
    */
    public static final int DIM_RECORD = 84;

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

    private String aggiustaNomeDestinazione(String s) {
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

    /**
     * Aggiunge una gita in fondo al file gite.dat.
     */
    public void scriviRecord(Gita g) {
        try {
            RandomAccessFile file = new RandomAccessFile("gite.dat", "rw");
            int nRecord = (int) (file.length() / DIM_RECORD);
            file.seek(nRecord * DIM_RECORD);

            file.writeChars(aggiustaID(g.getId()));
            file.writeChars(aggiustaNomeDestinazione(g.getNome()));
            file.writeChars(aggiustaNomeDestinazione(g.getDestinazione()));

            file.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File gite.dat non trovato");
        } catch (IOException ex) {
            System.out.println("Problema in lettura-scrittura file gite.dat");
        }
    }

    /**
     * Sovrascrive il record alla posizione indicata.
     * Utile per modificare una gita esistente.
     */
    public void scriviRecordInPosizione(Gita g, int posizione) {
        try {
            RandomAccessFile file = new RandomAccessFile("gite.dat", "rw");
            file.seek((long) posizione * DIM_RECORD);

            file.writeChars(aggiustaID(g.getId()));
            file.writeChars(aggiustaNomeDestinazione(g.getNome()));
            file.writeChars(aggiustaNomeDestinazione(g.getDestinazione()));

            file.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File gite.dat non trovato");
        } catch (IOException ex) {
            System.out.println("Problema in lettura-scrittura file gite.dat");
        }
    }

    /**
     * Legge il record alla posizione indicata.
     * Restituisce null se la posizione è fuori range.
     */
    public Gita leggiRecord(int posizione) {
        Gita g = null;
        try {
            RandomAccessFile file = new RandomAccessFile("gite.dat", "r");
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
                String destinazione = "";
                for (int i = 0; i < 20; i++) {
                    destinazione += file.readChar();
                }

                g = new Gita(
                    id.replace("*", "").trim(),
                    nome.replace("*", "").trim(),
                    destinazione.replace("*", "").trim()
                );
            }
            file.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File gite.dat non trovato");
        } catch (IOException ex) {
            System.out.println("Problema in lettura-scrittura file gite.dat");
        }
        return g;
    }

    /**
     * Restituisce tutte le gite presenti nel file.
     */
    public ArrayList<Gita> leggiTutte() {
        ArrayList<Gita> lista = new ArrayList<>();
        try {
            RandomAccessFile file = new RandomAccessFile("gite.dat", "r");
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
                String destinazione = "";
                for (int i = 0; i < 20; i++) {
                    destinazione += file.readChar();
                }

                lista.add(new Gita(
                    id.replace("*", "").trim(),
                    nome.replace("*", "").trim(),
                    destinazione.replace("*", "").trim()
                ));
            }
            file.close();
        } catch (FileNotFoundException ex) {
            System.out.println("File gite.dat non trovato");
        } catch (IOException ex) {
            System.out.println("Problema in lettura-scrittura file gite.dat");
        }
        return lista;
    }

    /**
     * Cerca una gita per id.
     * Restituisce la posizione logica del record, oppure -1 se non trovata.
     */
    public int cercaPerID(String id) {
        int posizioneTrovata = -1;
        String idCercato = aggiustaID(id);
        try {
            RandomAccessFile file = new RandomAccessFile("gite.dat", "r");
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
            System.out.println("File gite.dat non trovato");
        } catch (IOException ex) {
            System.out.println("Problema in lettura-scrittura file gite.dat");
        }
        return posizioneTrovata;
    }

    /**
     * Restituisce il numero totale di record nel file.
     */
    public int contaRecord() {
        int nRecord = 0;
        try {
            RandomAccessFile file = new RandomAccessFile("gite.dat", "rw");
            nRecord = (int) (file.length() / DIM_RECORD);
            file.close();
        } catch (IOException ex) {
            System.out.println("Problema con il file gite.dat");
        }
        return nRecord;
    }
}