package br.com.alura.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.alura.dao.CategoriaDAO;
import br.com.alura.modelo.Categoria;
import br.com.alura.modelo.Produto;

public class TestaCategoriaDAO {

	public static void main(String[] args) throws SQLException {
		try (Connection connection = new ConnectionPool().getConnection()) {
			CategoriaDAO categoriaDAO = new CategoriaDAO(connection);
			List<Categoria> categorias = categoriaDAO.listarComProdutos();
			for (Categoria c : categorias) {
				System.out.println(c.getNome());
				for (Produto p : c.getProdutos()) {
					System.out.println("Produto: " + p.getNome() + " - Descrição: " + p.getDescricao());
				}
				System.out.println();
			}
		}
	}
}
