package br.com.alura.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.modelo.Produto;

public class ProdutoDAO {
	
	private Connection connection;
	
	public ProdutoDAO(Connection connection) {
		this.connection = connection;
	}

	public void salvar(Produto produto) throws SQLException {
		String sql = "INSERT INTO produto (nome, descricao) VALUES (?, ?)";
		try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
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
			connection.commit();
			System.out.println("Commit efetuado.");
		} catch (Exception e) {
			e.printStackTrace();
			connection.rollback();
			System.out.println("Rollback efetuado.");
		}
	}

	public List<Produto> listarTodos() throws SQLException {
		List<Produto> produtos = new ArrayList<>();
		String sql = "SELECT * FROM produto";
		
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.execute();
			try (ResultSet rs = ps.getResultSet()) {
				while (rs.next()) {
					Integer id = rs.getInt("id");
					String nome = rs.getString("nome");
					String descricao = rs.getString("descricao");
					Produto produto = new Produto(id, nome, descricao);
					produtos.add(produto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return produtos;
	}
	
	public int deletar(Produto produto) throws SQLException {
		int linhasAtualizadas = 0;
		String sql = "DELETE FROM produto WHERE id = ?";
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.setInt(1, produto.getId());
			ps.execute();
			linhasAtualizadas = ps.getUpdateCount();
			connection.commit();
			System.out.println("Commit efetuado.");
		}catch (Exception e) {
			e.printStackTrace();
			connection.rollback();
			System.out.println("Rollback efetuado.");
		}
		return linhasAtualizadas;
	}
}
