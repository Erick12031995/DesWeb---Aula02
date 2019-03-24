package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

// Nome: Erick Augusto
// R.A: 81523752
public class Pais {
		private int id;
		private String nome;
		private long populacao;
		private double area;
		
		static {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}

		public Pais() {

		}
		
		public Pais(int id, String nome, long populacao, double area) {
			this.id = id;
			this.nome = nome;
			this.populacao = populacao;
			this.area = area;
		}
		
		public Connection obtemConexao() throws SQLException {
			return DriverManager.getConnection("jdbc:mysql://localhost/aula02?user=root&password=root");
		}

		public void criar() {
			String sqlInsert = "INSERT INTO Pais(nome, populacao, area) VALUES (?, ?, ?)";
			try (Connection conn = obtemConexao(); PreparedStatement stm = conn.prepareStatement(sqlInsert);) {
				stm.setString(1, getNome());
				stm.setLong(2, getPopulacao());
				stm.setDouble(3, getArea());
				stm.execute();
				String sqlQuery = "SELECT LAST_INSERT_ID()";
				try (PreparedStatement stm2 = conn.prepareStatement(sqlQuery); ResultSet rs = stm2.executeQuery();) {
					if (rs.next()) {
						setId(rs.getInt(1));
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		public void atualizar() {
			String sqlUpdate = "UPDATE Pais SET nome=?, populacao=?, area=? WHERE id=?";
			// usando o try with resources do Java 7, que fecha o que abriu
			try (Connection conn = obtemConexao(); PreparedStatement stm = conn.prepareStatement(sqlUpdate);) {
				stm.setString(1, getNome());
				stm.setLong(2, getPopulacao());
				stm.setDouble(3, getArea());
				stm.setInt(4, getId());
				stm.execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void excluir() {
			String sqlDelete = "DELETE FROM Pais WHERE id = ?";
			// usando o try with resources do Java 7, que fecha o que abriu
			try (Connection conn = obtemConexao(); PreparedStatement stm = conn.prepareStatement(sqlDelete);) {
				stm.setInt(1, getId());
				stm.execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void carregar() {
			String sqlSelect = "SELECT nome, populacao, area FROM Pais WHERE Pais.id = ?";
			// usando o try with resources do Java 7, que fecha o que abriu
			try (Connection conn = obtemConexao(); PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
				stm.setInt(1, getId());
				try (ResultSet rs = stm.executeQuery();) {
					if (rs.next()) {
						setNome(rs.getString("nome"));
						setPopulacao(rs.getLong("populacao"));
						setArea(rs.getDouble("area"));
					} else {
						setId(-1);
						setNome(null);
						setPopulacao(0);
						setArea(0);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (SQLException e1) {
				System.out.print(e1.getStackTrace());
			}
		}

		public Pais maiorPopulacao() {
			Pais populacao = new Pais();
			String sqlSelect = "select * from Pais order by populacao desc limit 1";

			try (Connection conn = obtemConexao(); PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
				try (ResultSet rs = stm.executeQuery()) {
					if (rs.next()) {
						populacao.setPopulacao(rs.getLong("populacao"));
					} else {
						populacao.setPopulacao(-1);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (SQLException e1) {
				System.out.println(e1.getStackTrace());
			}
			return populacao;
		}

		public Pais menorArea() {
			Pais area = new Pais();
			String sqlSelect = "select * from Pais order by area asc limit 1";

			try (Connection conn = obtemConexao(); PreparedStatement stm = conn.prepareStatement(sqlSelect);) {
				try (ResultSet rs = stm.executeQuery()) {
					if (rs.next()) {
						area.setArea(rs.getDouble("area"));
					} else {
						area.setArea(-1);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (SQLException e1) {
				System.out.println(e1.getStackTrace());
			}
			return area;
		}

		public ArrayList<Pais> vetorTresPaises() throws SQLException {
			Connection conn = obtemConexao();
			final String query = "select * from Pais";
			PreparedStatement prepare = null;
			ResultSet result = null;
			ArrayList<Pais> lista = new ArrayList<Pais>();
			try {
				prepare = conn.prepareStatement(query);
				result = prepare.executeQuery();
				while (result.next()) {
					Pais pais = new Pais();
					pais.setNome(result.getString("Atlantida"));
					pais.setNome(result.getString("Wakanda"));
					pais.setNome(result.getString("Themyscira"));
					lista.add(pais);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return lista;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public long getPopulacao() {
			return populacao;
		}

		public void setPopulacao(long populacao) {
			this.populacao = populacao;
		}

		public double getArea() {
			return area;
		}

		public void setArea(double area) {
			this.area = area;
		}

		@Override
		public String toString() {
			return "Pais [id=" + id + ", nome=" + nome + ", populacao=" + populacao + ", area=" + area + "]";
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Pais other = (Pais) obj;
			if (area == 0) {
				if (other.area != 0)
					return false;
			} else if (area != (other.area))
				return false;
			if (populacao == 0) {
				if (other.populacao != 0)
					return false;
			} else if (populacao != (other.populacao))
				return false;
			if (id != other.id)
				return false;
			if (nome == null) {
				if (other.nome != null)
					return false;
			} else if (!nome.equals(other.nome))
				return false;
			return true;
		}
	}
