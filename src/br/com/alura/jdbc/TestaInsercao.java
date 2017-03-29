package br.com.alura.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaInsercao {

	public static void main(String[] args) throws SQLException {
		try (Connection connection = Database.getConnection()) {
			connection.setAutoCommit(false);
			String sql = "INSERT INTO produto (nome, descricao) VALUES (?, ?)";

			try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				adicionar("TV LED", "Sony KDL 42 polegadas", ps);
				adicionar("Blu-Ray", "Full HDMI 250GB", ps);
				connection.commit();
				System.out.println("Commit efetuado.");
			} catch (Exception e) {
				e.printStackTrace();
				connection.rollback();
				System.out.println("Rollback efetuado.");
			}
		}
	}

	public static void adicionar(String nome, String descricao, PreparedStatement ps) throws SQLException {
		// if (nome.equals("Blu-Ray")) {
		// throw new IllegalArgumentException("Problema forçado.");
		// }

		ps.setString(1, nome);
		ps.setString(2, descricao);
		ps.execute();

		try (ResultSet resultSet = ps.getGeneratedKeys()) {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				System.out.println("ID: " + id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
