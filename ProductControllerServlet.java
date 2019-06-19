package com.lti.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import com.lti.exception.DataAccessException;
import com.lti.training.MVC.Product;
import com.lti.training.MVC.ProductDao;

@WebServlet("/ProductControllerServlet")
public class ProductControllerServlet extends HttpServlet {
	int currentPosition = 1;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int pageSize = 5; // how many records we are going to display at a time
		
		
		String page = request.getParameter("page"); // req.getPara("we can give any name")  is used for reading any data coming from the client
		if(page != null) {
			if(page.equals("next"))
				currentPosition += pageSize;
			else if (page.equals("prev")) 
				currentPosition -= pageSize; 
		}
		else
			currentPosition =1;
		ProductDao dao= new  ProductDao();
		try {
			List<Product>  products =
					dao.fetchAll(currentPosition, currentPosition + pageSize - 1); // will fect curretn first 5 products from the db
		    HttpSession session= request.getSession();
			session.setAttribute("current5Products",products);
			response. sendRedirect("viewProduct.jsp");
			
		} catch (DataAccessException e) {
			throw new ServletException("ProductControllerServlet encountered problem while "
					+ "accessing the DAO",e);
		}
			}
		
	}
