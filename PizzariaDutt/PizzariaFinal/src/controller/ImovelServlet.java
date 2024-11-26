package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sqlite.SQLiteConfig;

import com.google.gson.Gson;

import model.Imovel;

/**
 * Servlet implementation class ImovelServlet
 */
@WebServlet("/ImovelServlet")
public class ImovelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImovelServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		response.setCharacterEncoding("utf-8");
		String acao  = request.getParameter("acao");
		try { 
			if (acao.equals("inserir")) {
				inserir(request, response);
			}else if (acao.equals("alterar")) {
				alterar(request, response);
			}else if (acao.equals("consultar")) {
				consultar(request, response);
			}else if (acao.equals("excluir")) {
				excluir(request, response);
			}
		}catch(Exception e) {
			response.getWriter().append("Ocorreu um erro na solicitação para " + acao);
		}		
	}
	
	public Connection getConection() throws Exception{
		Connection conn = null;
		Class.forName("org.sqlite.JDBC");
		String diretorio = System.getProperty("wtp.deploy").toString().split(".metadata")[0];
		String dataBase = diretorio + "\\imobiliaria.db";
		conn = DriverManager.getConnection("jdbc:sqlite:"+dataBase);
		return conn;
	}

	public void consultar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection conn = getConection();		
		String sql = "select * from imovel";
		PreparedStatement psm = conn.prepareStatement(sql);
		ResultSet rs = psm.executeQuery();
		List<Imovel> listImovel = new ArrayList<>();
		while(rs.next()) {
			Imovel imovel = new Imovel();
			imovel.setCodigo(rs.getInt(1));
			imovel.setFoto(rs.getString(2)!=null?rs.getString(2):"");
			imovel.setTipo(rs.getString(3));
			imovel.setValor(rs.getDouble(4));
			listImovel.add(imovel);
		}
		Gson g = new Gson();
		response.getWriter().append(g.toJson(listImovel));
		
		conn.close();	
	}
	
	
	public void inserir(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String foto  = request.getParameter("foto");
		String tipo  = request.getParameter("tipo"); 
		Double valor = Double.parseDouble(request.getParameter("valor"));		
		Connection conn = getConection();		
		String sql = "insert into imovel(foto,tipo,valor) values(?,?,?)";
		PreparedStatement psm = conn.prepareStatement(sql);
		psm.setString(1, foto);
		psm.setString(2, tipo);
		psm.setDouble(3, valor);		
		int qtdAfetadas = psm.executeUpdate();		
		if (qtdAfetadas>0) {
			String msg = "Dados inseridos com sucesso!";
			response.getWriter().append(msg);
		}else {
			String msg = "Não foi possível inserir!";
			response.getWriter().append(msg);
		}
		conn.close();	
	}
	
	public void alterar(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int codigo   = Integer.parseInt(request.getParameter("codigo"));
		String foto  = request.getParameter("foto");
		String tipo  = request.getParameter("tipo"); 
		Double valor = Double.parseDouble(request.getParameter("valor"));		
		Connection conn = getConection();		
		String sql = "update imovel set foto=?, tipo=?, valor=? where codigo=?";
		PreparedStatement psm = conn.prepareStatement(sql);
		psm.setString(1, foto);
		psm.setString(2, tipo);
		psm.setDouble(3, valor);
		psm.setInt(4, codigo);
		int qtdAfetadas = psm.executeUpdate();		
		if (qtdAfetadas>0) {
			String msg = "Dados alterados com sucesso!";
			response.getWriter().append(msg);
		}else {
			String msg = "Não foi possível alterar!";
			response.getWriter().append(msg);
		}
		conn.close();
	}
	
	public void excluir(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int codigo   = Integer.parseInt(request.getParameter("codigo")); 
		Connection conn = getConection();
		String sql = "delete from imovel where codigo=?";
		PreparedStatement psm = conn.prepareStatement(sql);
		psm.setInt(1, codigo);
		int qtdAfetadas = psm.executeUpdate();		
		if (qtdAfetadas>0) {
			String msg = "Dados excluídos com sucesso!";
			response.getWriter().append(msg);
		}else {
			String msg = "Não foi possível excluir!";
			response.getWriter().append(msg);
		}
		conn.close();
	}

}
