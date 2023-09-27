package com.advancia.stage.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.advancia.stage.dao.Dao;
import com.advancia.stage.entity.Impasto;
import com.advancia.stage.entity.Ingrediente;
import com.advancia.stage.entity.Pizza;
import com.advancia.stage.entity.Utente;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Utente utenteTrovato = Dao.autenticaUtente(username, password);
		


		if (utenteTrovato != null) {
			List<Pizza> allPizza = Dao.getAllPizzaByUser(utenteTrovato);
			List<Ingrediente> ingredienti = Dao.getAllIngredienti();
			List<Impasto> impasti = Dao.getAllImpasti();
			request.getSession().setAttribute("authenticatedUser", utenteTrovato);
			request.getSession().setAttribute("pizze", allPizza);
			request.getSession().setAttribute("ingredienti", ingredienti);
			request.getSession().setAttribute("impasti", impasti);
			RequestDispatcher rd = request.getRequestDispatcher("/dashboard.jsp");

			rd.forward(request, response);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");

			rd.forward(request, response);
		}

	}

}