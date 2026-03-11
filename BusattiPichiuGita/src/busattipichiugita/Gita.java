/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package busattipichiugita;

/**
 *
 * @author Busatti Mattia e Pichiu Florin
 */
public class Gita {
    private String id;
    private String nome;
    private String destinazione;

    public Gita(String id, String nome, String destinazione) {
        this.id = id;
        this.nome = nome;
        this.destinazione = destinazione;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDestinazione() {
        return destinazione;
    }

    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Gita{" + "id=" + id + ", nome=" + nome + ", destinazione=" + destinazione + '}';
    }
}
