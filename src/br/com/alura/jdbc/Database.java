package br.com.alura.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/loja-virtual", "SA", "");
	}
}
