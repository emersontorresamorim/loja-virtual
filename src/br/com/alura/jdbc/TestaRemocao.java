package br.com.alura.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaRemocao {

	public static void main(String[] args) throws SQLException {
		Connection connection = new ConnectionPool().getConnection();
		Statement statement = connection.createStatement();
		statement.execute("DELETE FROM produto WHERE id > 16");
		int linhasAtualizadas = statement.getUpdateCount();
		
		System.out.println(linhasAtualizadas + " linhas atualizadas");
		
		statement.close();
		connection.close();
	}
}
