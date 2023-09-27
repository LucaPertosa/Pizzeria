package com.advancia.stage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.advancia.stage.dao.Dao;
import com.advancia.stage.entity.Impasto;
import com.advancia.stage.entity.Ingrediente;
import com.advancia.stage.entity.Pizza;
import com.advancia.stage.entity.Utente;

@WebServlet("/")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DashboardServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utente utenteLoggato = (Utente) session.getAttribute("authenticatedUser");

		EntityManager em = Dao.creaEntityManager();
		em.getTransaction().begin();

		String nomePizza = request.getParameter("nomePizza");
		String tipoImpasto = request.getParameter("impasto");
		String pizzaIdStr = request.getParameter("pizzaId");
		String modPizzaId = request.getParameter("modPizzaId");
		String[] ingredientiSelezionati = request.getParameterValues("ingredienti");

//		Prelevo i parametri della pizza da modificare
		String nomePizzaModificato = request.getParameter("nomePizzaModificata");
		String tipoImpastoModificato = request.getParameter("impastoModificato");
		String pizzaIdModificato = request.getParameter("pizzaModificataId");
		String[] ingredientiModificati = request.getParameterValues("ingredientiModificati");

		if (nomePizzaModificato != null) {
			int idPizzaDaModificare = Integer.parseInt(pizzaIdModificato);
			Pizza pizzaDaModificare = Dao.getPizzaById(idPizzaDaModificare);
			List<Ingrediente> ingredienti = new ArrayList<>();
			if (ingredientiModificati != null) {
				for (String ingredienteId : ingredientiModificati) {
					Ingrediente ingrediente = em.find(Ingrediente.class, Integer.parseInt(ingredienteId));
					ingredienti.add(ingrediente);
				}
			}
			Dao.updatePizza(pizzaDaModificare, nomePizzaModificato, tipoImpastoModificato, ingredienti);
			response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
		}

		if (modPizzaId != null) {
			int idPizzaDaModificare = Integer.parseInt(modPizzaId);
			Pizza pizzaDaModificare = Dao.getPizzaById(idPizzaDaModificare);
			RequestDispatcher rd = request.getRequestDispatcher("modificaPizza.jsp");
			request.setAttribute("pizzaDaModificare", pizzaDaModificare);

			rd.forward(request, response);
		}

		if (pizzaIdStr != null) {
			int pizzaId = Integer.parseInt(pizzaIdStr);
			Dao.eliminaPizza(pizzaId);
			response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
		}

		if (nomePizza != null) {
			Impasto impasto = em.find(Impasto.class, Integer.parseInt(tipoImpasto));
			List<Ingrediente> ingredienti = new ArrayList<>();
			if (ingredientiSelezionati != null) {
				for (String ingredienteId : ingredientiSelezionati) {
					Ingrediente ingrediente = em.find(Ingrediente.class, Integer.parseInt(ingredienteId));
					ingredienti.add(ingrediente);
				}
			}
			Pizza pizza = Dao.creaPizza(nomePizza, impasto, ingredienti, utenteLoggato);

			em.persist(pizza);
			em.getTransaction().commit();
			em.close();

			response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
			request.setAttribute("messaggio", "La tua pizza " + nomePizza + " è stata creata con successo");
		}

		List<Pizza> pizze = Dao.getAllPizzaByUser(utenteLoggato); // Recupera l'elenco aggiornato delle pizze
		session.setAttribute("pizze", pizze);
		// RequestDispatcher rd = request.getRequestDispatcher("/dashboard.jsp");
		// rd.forward(request, response);
	}

//	private void listaPizze(HttpServletRequest request, HttpServletResponse response) {
//		HttpSession session = request.getSession();
//		Utente utenteLoggato = (Utente) session.getAttribute("authenticatedUser");
//		try {
//			List<Pizza> pizze = Dao.getAllPizzaByUser(utenteLoggato); // Recupera l'elenco aggiornato delle pizze
//			session.setAttribute("pizze", pizze);
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/dashboard.jsp");
//			dispatcher.forward(request, response);
//		} catch (ServletException | IOException e) {
//			e.printStackTrace();
//		}
//
//	}
//
//	private void handleCreaPizza(HttpServletRequest request, HttpServletResponse response) {
//		HttpSession session = request.getSession();
//		Utente utenteLoggato = (Utente) session.getAttribute("authenticatedUser");
//		String nomePizza = request.getParameter("nomePizza");
//		String tipoImpasto = request.getParameter("impasto");
//		String[] ingredientiSelezionati = request.getParameterValues("ingredienti");
//		EntityManager em = Dao.creaEntityManager();
//		em.getTransaction().begin();
//		Impasto impasto = em.find(Impasto.class, Integer.parseInt(tipoImpasto));
//		List<Ingrediente> ingredienti = new ArrayList<>();
//		if (ingredientiSelezionati != null) {
//			for (String ingredienteId : ingredientiSelezionati) {
//				Ingrediente ingrediente = em.find(Ingrediente.class, Integer.parseInt(ingredienteId));
//				ingredienti.add(ingrediente);
//			}
//		}
//		Pizza pizza = Dao.creaPizza(nomePizza, impasto, ingredienti, utenteLoggato);
//		em.persist(pizza);
//		em.getTransaction().commit();
//		em.close();
//
//		try {
//			response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		request.setAttribute("messaggio", "La tua pizza " + nomePizza + " è stata creata con successo");
//
//
//	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
