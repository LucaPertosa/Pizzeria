package com.advancia.stage.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Pizza")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_impasto")
    private Impasto impasto;

    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente utente;

    @ManyToMany
    @JoinTable(
        name = "Pizza_Ingrediente",
        joinColumns = @JoinColumn(name = "id_pizza"),
        inverseJoinColumns = @JoinColumn(name = "id_ingrediente")
    )
    private List<Ingrediente> ingredienti;

    // Costruttori, getter e setter

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return the impasto
	 */
	public Impasto getImpasto() {
		return impasto;
	}

	/**
	 * @param impasto the impasto to set
	 */
	public void setImpasto(Impasto impasto) {
		this.impasto = impasto;
	}

	/**
	 * @return the utente
	 */
	public Utente getUtente() {
		return utente;
	}

	/**
	 * @param utente the utente to set
	 */
	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	/**
	 * @return the ingredienti
	 */
	public List<Ingrediente> getIngredienti() {
		return ingredienti;
	}

	/**
	 * @param ingredienti the ingredienti to set
	 */
	public void setIngredienti(List<Ingrediente> ingredienti) {
		this.ingredienti = ingredienti;
	}
	
	public boolean contieneIngrediente(Ingrediente ingredienteDaVerificare) {
	    for (Ingrediente ingrediente : this.ingredienti) {
	        if (ingrediente.getId() == ingredienteDaVerificare.getId()) {
	            return true;
	        }
	    }
	    return false;
	}

}