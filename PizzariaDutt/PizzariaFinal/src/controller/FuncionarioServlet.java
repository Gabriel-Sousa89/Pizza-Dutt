package controller;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import model.Imovel;


/**
 * Servlet implementation class FuncionarioServlet
 */
@WebServlet("/FuncionarioServlet")
public class FuncionarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FuncionarioServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		response.setCharacterEncoding("utf-8");
		
		String acao  = request.getParameter("acao");
		try { 
			if (acao.equals("login")) {
				login(request, response);
			}else if (acao.equals("logout")) {
				logout(request, response);
			}else if (acao.equals("consultar")) {
				consultar(request, response);
			}else if (acao.equals("inserir")) {
				inserir(request, response);
			}else if (acao.equals("alterar")) {
				alterar(request, response);
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
	
	public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		session.invalidate();
		response.getWriter().append("ok");
	}
	
	public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String email  = request.getParameter("email");
		String senha  = request.getParameter("senha");
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
	    senha = hash.toString(16); //transformando a senha em hash		
		
	    Connection conn = getConection();		
		String sql = "select * from funcionario where email=? and senha=?";
		PreparedStatement pstm = conn.prepareStatement(sql);				
		pstm.setString(1, email);
		pstm.setString(2, senha);
		ResultSet rs = pstm.executeQuery();				
		if(rs.next()) {
			response.getWriter().append("ok");
			HttpSession session = request.getSession(true);
			session.setAttribute("nome", rs.getString(3));
		}else {
			response.getWriter().append("erro");
			HttpSession session = request.getSession(true);
			session.invalidate();
		}
		conn.close();	
	}
	
	public void consultar(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection conn = getConection();		
		String sql = "select * from funcionario";
		PreparedStatement pstm = conn.prepareStatement(sql);								
		ResultSet rs = pstm.executeQuery();				
		while(rs.next()) {
			String col1 = "<td><img src='imagens/edit.png' onclick='editar("+ rs.getInt(1) + ",\""+ rs.getString(2) +"\",\""+  rs.getString(3) + "\")'/></td>";
			String col2 = "<td><img src='imagens/delete.png' onclick='confirmarExcluir("+ rs.getInt(1) + ")'/></td>";
			String col3 = "<td>" + rs.getInt(1) + "</td>"; //matricula
			String col4 = "<td>" + rs.getString(2) + "</td>"; //email
			String col5 = "<td>" + rs.getString(3) + "</td>"; //nome
			String linha = "<tr>"+col1+col2+col3+col4+col5+"</tr>";
			response.getWriter().append(linha);
		}
		//Neste exemplo estamos já retornando as linhas montadas para a tabela html
		//Poderíamos retornar um json da mesma forma que fizemos com os imóveis
		conn.close();	
	}
	
	public void inserir(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String email  = request.getParameter("email");
		String nome  = request.getParameter("nome");
		String senha  = request.getParameter("senha");
		
		MessageDigest md = MessageDigest.getInstance("MD5");
		BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
	    senha = hash.toString(16); //transformando a senha em hash		
		
		Connection conn = getConection();		
		String sql = "insert into funcionario(email,nome,senha) values(?,?,?)";
		PreparedStatement psm = conn.prepareStatement(sql);
		psm.setString(1, email);
		psm.setString(2, nome);
		psm.setString(3, senha);		
		int qtdAfetadas = psm.executeUpdate();
		if (qtdAfetadas>0) {
			String msg = "ok";
			response.getWriter().append(msg);
		}else {
			String msg = "Não foi possível inserir!";
			response.getWriter().append(msg);
		}
		conn.close();	
	}
	
	public void alterar(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int matricula   = Integer.parseInt(request.getParameter("matricula"));
		String email    = request.getParameter("email");
		String nome     = request.getParameter("nome");
		String senha    = request.getParameter("senha"); 

		MessageDigest md = MessageDigest.getInstance("MD5");
		BigInteger hash = new BigInteger(1, md.digest(senha.getBytes()));
	    senha = hash.toString(16); //transformando a senha em hash para proteger a senha		
		
		Connection conn = getConection();		
		String sql = "update funcionario set email=?, nome=?, senha=? where matricula=?";
		PreparedStatement psm = conn.prepareStatement(sql);
		psm.setString(1, email);
		psm.setString(2, nome);
		psm.setString(3, senha);
		psm.setInt(4, matricula);		
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

		int matricula   = Integer.parseInt(request.getParameter("matricula")); 
		Connection conn = getConection();
		String sql = "delete from funcionario where matricula=?";
		PreparedStatement psm = conn.prepareStatement(sql);
		psm.setInt(1, matricula);
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