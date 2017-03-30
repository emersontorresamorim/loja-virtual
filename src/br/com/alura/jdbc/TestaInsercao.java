package br.com.alura.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.alura.modelo.Produto;

public class TestaInsercao {

	public static void main(String[] args) throws SQLException {
		try (Connection connection = new ConnectionPool().getConnection()) {
			connection.setAutoCommit(false);
			String sql = "INSERT INTO produto (nome, descricao) VALUES (?, ?)";

			try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				adicionar(new Produto("Mesa Azul", "Mesa com 4 cadeiras"), ps);
				connection.commit();
				System.out.println("Commit efetuado.");
			} catch (Exception e) {
				e.printStackTrace();
				connection.rollback();
				System.out.println("Rollback efetuado.");
			}
		}
	}

	public static void adicionar(Produto produto, PreparedStatement ps) throws SQLException {
		// if (nome.equals("Blu-Ray")) {
		// throw new IllegalArgumentException("Problema forçado.");
		// }

		ps.setString(1, produto.getNome());
		ps.setString(2, produto.getDescricao());
		ps.execute();

		try (ResultSet resultSet = ps.getGeneratedKeys()) {
			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				produto.setId(id);
				System.out.println(produto.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
