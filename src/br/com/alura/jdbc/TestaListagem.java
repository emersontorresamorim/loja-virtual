package br.com.alura.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaListagem {

	public static void main(String[] args) throws SQLException {
		Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/loja-virtual", "SA", "");
		Statement statement = connection.createStatement();
		statement.execute("SELECT * FROM produto");
		ResultSet resultSet = statement.getResultSet();
		
		while (resultSet.next()) {
			int id = resultSet.getInt("id");
			String nome = resultSet.getString("nome");
			String descricao = resultSet.getString("descricao");
			System.out.println("ID: " + id + " - Nome: " + nome + " - Descrição: " + descricao);
		}
		
		resultSet.close();
		statement.close();
		connection.close();
	}
}
