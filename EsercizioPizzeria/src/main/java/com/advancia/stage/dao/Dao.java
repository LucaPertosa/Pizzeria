package com.advancia.stage.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.advancia.stage.entity.Impasto;
import com.advancia.stage.entity.Ingrediente;
import com.advancia.stage.entity.Pizza;
import com.advancia.stage.entity.Utente;

public class Dao {

	public static EntityManager creaEntityManager() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("PERSISTENCE");
		EntityManager em = emf.createEntityManager();
		return em;
	}

	public static Utente autenticaUtente(String username, String password) {
		EntityManager em = creaEntityManager();
		Utente utente = em.createQuery("SELECT u FROM Utente u WHERE u.username = :username AND u.password = :password",
						Utente.class)
				.setParameter("username", username).setParameter("password", password).getSingleResult();
		em.close();
		return utente;
	}

	public static List<Ingrediente> getAllIngredienti() {
		EntityManager em = creaEntityManager();
		List<Ingrediente> listaIngredienti = new ArrayList<>();
		listaIngredienti = em.createQuery("SELECT i FROM Ingrediente i", Ingrediente.class).getResultList();
		em.close();
		return listaIngredienti;
	}
	
	public static List<Impasto> getAllImpasti() {
		EntityManager em = creaEntityManager();
		List<Impasto> listaImpasti = new ArrayList<>();
		listaImpasti = em.createQuery("SELECT i FROM Impasto i", Impasto.class).getResultList();
		em.close();
		return listaImpasti;
	}
	
	public static Pizza creaPizza(String nome, Impasto impasto, List<Ingrediente> ingredienti, Utente utenteLoggato) {
		
		Pizza pizza = new Pizza();
		pizza.setNome(nome);
		
		pizza.setUtente(utenteLoggato);
		
		pizza.setImpasto(impasto);
		
		pizza.setIngredienti(ingredienti);
		
		return pizza;
	}
	
	public static void updatePizza(Pizza pizza, String nome, String impasto, List<Ingrediente> nuoviIngredienti) {
	    EntityManager em = creaEntityManager();
	    EntityTransaction tx = em.getTransaction();

        tx.begin();

        em.createQuery("UPDATE Pizza p " +
                "SET p.nome = :nome, p.impasto = :impasto " +
                "WHERE p.id = :id")
                .setParameter("nome", pizza.getNome())
                .setParameter("impasto", pizza.getImpasto())
                .setParameter("id", pizza.getId())
                .executeUpdate();

        // Rimuovi tutti gli ingredienti associati a questa pizza
        Pizza persisted = em.find(Pizza.class, pizza.getId());
        persisted.getIngredienti().clear();
       

        // Aggiungi gli ingredienti associati a questa pizza
        persisted.setIngredienti(nuoviIngredienti);
        tx.commit();
	    em.close();
	}
	
	public static Pizza getPizzaById(int id) {
		EntityManager em = creaEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		Pizza pizza = new Pizza();
		pizza = em.createQuery("SELECT p FROM Pizza p JOIN FETCH p.impasto imp JOIN FETCH p.ingredienti ing WHERE p.id = :id", Pizza.class).setParameter("id", id).getSingleResult();
		tx.commit();
		em.close();
		return pizza;
	}
	
	public static List<Pizza> getAllPizzaByUser(Utente utenteLoggato) {
		EntityManager em = creaEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		List<Pizza> listaPizze = new ArrayList<>();
		listaPizze = em.createQuery("SELECT DISTINCT p FROM Pizza p " +
				"JOIN FETCH p.impasto imp " +
				"JOIN FETCH p.ingredienti ing " +
				"WHERE p.utente.id = :userId", Pizza.class)
				.setParameter("userId", utenteLoggato.getId())
				.getResultList();
		tx.commit();
		em.close();
		return listaPizze.isEmpty() ? null : listaPizze;
	}
	
	public static void eliminaPizza(int pizza) {
		EntityManager em = creaEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		tx.begin();
		
		em.createQuery("DELETE FROM Pizza pi WHERE pi.id = :pizza").setParameter("pizza", pizza).executeUpdate();
		
//		em.remove(em.contains(pizza) ? pizza : em.merge(pizza));
		
		tx.commit();
		
		em.close();
	}
	
}
