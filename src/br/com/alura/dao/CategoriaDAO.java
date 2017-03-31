package br.com.alura.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.modelo.Categoria;
import br.com.alura.modelo.Produto;

public class CategoriaDAO {
	
	private Connection connection;

	public CategoriaDAO(Connection connection) {
		this.connection = connection;
	}

	public List<Categoria> listarTodos() {
		List<Categoria> categorias = new ArrayList<>();
		String sql = "SELECT * FROM categoria";
		
		try (PreparedStatement ps = connection.prepareStatement(sql)) {
			ps.execute();
			try (ResultSet rs = ps.getResultSet()) {
				while (rs.next()) {
					Integer id = rs.getInt("id");
					String nome = rs.getString("nome");
					Categoria categoria = new Categoria(id, nome);
					categorias.add(categoria);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categorias;
	}

	public List<Categoria> listarComProdutos() {
		List<Categoria> categorias = new ArrayList<>();
		Categoria ultima = null;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.id AS c_id");
		sql.append(", c.nome AS c_nome");
		sql.append(", p.id AS p_id");
		sql.append(", p.nome AS p_nome");
		sql.append(", p.descricao AS p_descricao");
		sql.append(" FROM categoria AS c");
		sql.append(" JOIN produto AS p");
		sql.append(" ON p.categoria_id = c.id");
		
		try (PreparedStatement ps = connection.prepareStatement(sql.toString())) {
			ps.execute();
			try (ResultSet rs = ps.getResultSet()) {
				while (rs.next()) {
					Integer id = rs.getInt("c_id");
					String nome = rs.getString("c_nome");
					if (ultima == null || !ultima.getNome().equals(nome)) {
						Categoria categoria = new Categoria(id, nome);
						categorias.add(categoria);
						ultima = categoria;
					}
					Integer idProduto = rs.getInt("p_id");
					String nomeProduto = rs.getString("p_nome");
					String descricao = rs.getString("p_descricao");
					Produto produto = new Produto(idProduto, nomeProduto, descricao);
					ultima.adicionar(produto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categorias;
	}
	
	
}
