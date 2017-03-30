package br.com.alura.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.alura.dao.ProdutoDAO;
import br.com.alura.modelo.Produto;

public class TestaProdutoDAO {

	public static void main(String[] args) throws SQLException {
		try (Connection connection = new ConnectionPool().getConnection()) {
			connection.setAutoCommit(false);
			Produto produto = new Produto("Mesa Azul", "Mesa com 4 cadeiras");
			
			ProdutoDAO produtoDAO = new ProdutoDAO(connection);
			
			// insert do DAO
			produtoDAO.salvar(produto);
			
			// select do DAO
			List<Produto> produtos = produtoDAO.listarTodos();
			for (Produto p : produtos) {
				System.out.println(p.toString());
			}
			
			// delete do DAO
			int count = produtoDAO.deletar(produtos.get(5));
			System.out.println("Linhas deletadas: " + count);
		}
	}
}
