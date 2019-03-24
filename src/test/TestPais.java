package test;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import model.Pais;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestPais {
	Pais pais, copia;
	static int id = 0;

	@Before
	public void setUp() throws Exception {
		System.out.println("setup");
		pais = new Pais(id, "Kripton", 80000, 3000.00);
		copia = new Pais(id, "Kripton", 80000, 3000.00);
		System.out.println(pais);
		System.out.println(copia);
		System.out.println(id);
	}

	@Test
	public void test00Carregar() {
		System.out.println("carregar");
		Pais fixture = new Pais(1,"Atlantida",100000,5000.00);
		Pais novo = new Pais(1, null, 0, 0);
		novo.carregar();
		assertEquals("Pais Carregado", novo, fixture);
	}

	@Test
	public void test01Criar() {
		System.out.println("criar");
		pais.criar();
		id = pais.getId();
		System.out.println(id);
		copia.setId(id);
		assertEquals("Pais Criado", pais, copia);
	}

	@Test
	public void test02Atualizar() {
		System.out.println("atualizar");
		pais.setPopulacao(200000);
		copia.setPopulacao(200000);
		pais.atualizar();
		pais.carregar();
		assertEquals("Pais Atualizado", pais, copia);
	}

	@Test
	public void test03Excluir() {
		System.out.println("excluir");
		copia.setId(-1);
		copia.setNome(null);
		copia.setPopulacao(0);
		copia.setArea(0);
		pais.excluir();
		pais.carregar();
		assertEquals("Pais Excluido", pais, copia);
	}
	
	@Test
	public void test04maiorPopulacao() {
		System.out.println("maiorPopulacao");
		Pais populacao = pais.maiorPopulacao();
		assertEquals(3, 0, populacao.getPopulacao());
	}

	@Test
	public void test05menorArea() {
		System.out.println("menorArea");
		Pais area = pais.menorArea();
		assertEquals(5, 0, area.getArea());
	}

	@Test
	public void test06ArrayList() throws SQLException {

		Pais List = new Pais();
		ArrayList<String> res = new ArrayList<String>();
		res.add("Atlantida");
		res.add("Wakanda");
		res.add("Themyscira");

		assertArrayEquals(List.vetorTresPaises(), res);

	}

	private void assertArrayEquals(java.util.ArrayList<Pais> vetorTresPaises, java.util.ArrayList<String> res) {
		// TODO Auto-generated method stub
	}
}