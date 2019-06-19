package com.lti.training.MVC;

	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.List;

import com.lti.exception.DataAccessException;


	public class ProductDao {
       
			public List<Product> fetchAll(int from,int to) throws DataAccessException {
				Connection conn=null; //manages the connection  between the application and database
				PreparedStatement stmt=null; // helps us to execute SQL statement
				ResultSet rs=null; // helps us to fetch the result of a select query
		
					try {
						Class.forName("oracle.jdbc.driver.OracleDriver");
						conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","hr","hr");
						
						String sql=" select id,name,price,quantity from ( select t.* , row_number() over (order by ID) r  from  TBL_PRODUCT t ) where  r between ? and ?";
						stmt=conn.prepareStatement(sql);
						
						stmt.setInt(1, from);
						stmt.setInt(2, to);

						rs=stmt.executeQuery(); //we use it whenever we use select statement
						
						List<Product> products=new ArrayList<Product>();
						while(rs.next()) {
							Product p=new Product();
							p.setId(rs.getInt(1));
							p.setName(rs.getString(2));
							p.setPrice(rs.getDouble(3));
							p.setQuantity(rs.getInt(4));
							products.add(p);
						}
								return products;
					}
					catch(ClassNotFoundException e) {
						throw new DataAccessException("unable to load the JDBC Driver");
					}
					catch(SQLException e) {
						throw new DataAccessException("Problem while fetching products from DB",e);
					}
					finally {
						try 
						{
							conn.close();
							}
						catch(Exception e) {
							
						}
					}
		
	}

	}
		
