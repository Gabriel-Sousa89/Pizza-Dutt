<%@ include file="Protecao.jsp" %>

<html>
<head>
	<meta charset="utf-8"/>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.js" integrity="sha512-+k1pnlgt4F1H8L7t3z95o3/KO+o78INEcXTbnoJQ/F2VqDVhWoaiVml/OEHv9HsVgxUaVW+IbiZPUJQfF/YxZw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<title>Pizza Dutt</title>
<style>

.voltar{
    height: 100%;
    font-family: Arial, sans-serif;
    background-color: #f4f4f4;
    display: flex;
    justify-content: left;
    align-items: left;
}

* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

body, html {
    height: 100%;
    font-family: Arial, sans-serif;
    background-color: #f4f4f4;
    display: flex;
    justify-content: center;
    align-items: center;
}

.container {
    background-color: #fff;
    padding: 30px;
    border-radius: 8px;
    box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
    width: 100%;
    max-width: 1000px;
    text-align: center;
}

h2 {
    font-size: 28px;
    color: #333;
    margin-bottom: 20px;
}

input[type="text"], input[type="password"] {
    width: 100%;
    padding: 12px;
    margin-bottom: 15px;
    border: 2px solid #ccc;
    border-radius: 8px;
    font-size: 16px;
    transition: border-color 0.3s ease;
}

input[type="text"]:focus, input[type="password"]:focus {
    border-color: #007bff;
    outline: none;
}

.botoes {
    width: 200px;
    padding: 14px;
    background-color: #007bff;
    color: white;
    border: none;
    border-radius: 8px;
    cursor: pointer;
    font-size: 18px;
    transition: background-color 0.3s ease;
}

.botoes:hover {
    background-color: #0056b3;
}

a {
    color: #007bff;
    text-decoration: none;
    font-size: 14px;
    margin-left: 10px;
    transition: color 0.3s ease;
}

a:hover {
    color: #0056b3;
}

a:active {
    color: #003366;
}

table,tr,th{
        border: 1px solid black;
        border-spacing: 0px;
    }
    tr,td,th{
    	border: 1px solid black;
        padding: 8px;
        padding-left: 8px;
        padding-right:15px;
    }

</style>
</head>
<body>
	
		
	<a href="PageAreaAdministrativa.jsp" class="voltar" >Voltar</a>
	<div class="container">
	<h2>Gerenciar funcionários</h2>
	<br>
	<form>
		Matrícula: <input type="text" id="matricula" disabled/><br /> <br />
		E-mail: <input type="text" id="email" /><br /> <br />
		Nome: <input type="text" id="nome" /><br /> <br />
		Senha: <input type="password" id="senha" /><br /><br />
	
		<input type="button" value="Buscar" onclick="consultar()"/>
		<input type="button" value="Salvar" onclick="inserirOuAlterar()"/>
		<input type="reset" value="Limpar"/><br /><br />
	</form>
	<table>
	    <thead>
	        <tr>
	            <th>Editar</th>
	            <th>Excluir</th>
	            <th>Matricula</th>
	            <th>E-mail</th>
	            <th>Nome</th>
	        </tr>
	    </thead>
	    <tbody id="corpo_da_tabela">
	    </tbody>
	</table>
	<script>
		function editar(matricula, email, nome){
			document.getElementById("matricula").value = matricula;
			document.getElementById("email").value = email;
			document.getElementById("nome").value      = nome;
		}
		
		function confirmarExcluir(matricula){
			if (confirm("Deseja realmente excluir?")){
				excluir(matricula);
			}
		}
		
		function inserirOuAlterar(){
			
			var jsonEnvio = {};
			jsonEnvio.matricula = document.getElementById("matricula").value;
			jsonEnvio.email     = document.getElementById("email").value;
			jsonEnvio.nome      = document.getElementById("nome").value;
			jsonEnvio.senha     = document.getElementById("senha").value;
			if (jsonEnvio.matricula!=""){
				jsonEnvio.acao = "alterar";	
			}else{
				jsonEnvio.acao = "inserir";
			}
			$.ajax({
				url: "FuncionarioServlet",
				data: jsonEnvio,
				type:"post",
				success: function (resp){
					if (resp=="ok"){
						alert("Funcionario cadastrado com sucesso!");
						consultar();
					}else{
						alert(resp)
					}
				},
				error: function (){
					alert("Ocorreu um erro ao excluir!!!");
				}
			});
		}
		
		function consultar(){
			var jsonEnvio = {};
			jsonEnvio.acao = "consultar";
			$.ajax({
				url: "FuncionarioServlet",
				data: jsonEnvio,
				type:"post",
				success: function (resp){
					document.getElementById("corpo_da_tabela").innerHTML = resp;
				},
				error: function (){
					alert("Ocorreu um erro ao consultar!!!");
				}
			});
		}
		
		function excluir(matricula){
			var jsonEnvio = {};
			jsonEnvio.acao = "excluir";
			jsonEnvio.matricula = matricula;
			$.ajax({
				url: "FuncionarioServlet",
				data: jsonEnvio,
				type:"post",
				success: function (resp){
					alert(resp);
					consultar();
				},
				error: function (){
					alert("Ocorreu um erro ao excluir!!!");
				}
			});
		}
		
	</script>
	
	</div>
</body>
</html>