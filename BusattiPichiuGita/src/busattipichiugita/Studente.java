/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package busattipichiugita;

/**
 *
 * @author Busatti Mattia e Pichiu Florin
 */
public class Studente {
    private String id;
    private String nome;
    private String cognome;
    private String classe;

    public Studente(String id, String nome, String cognome, String classe) {
        this.nome = nome;
        this.cognome = cognome;
        this.classe = classe;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Studente{" + "id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", classe=" + classe + '}';
    }
}
