<html>
<head>
	<meta charset="utf-8"/>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.7.1/jquery.js" integrity="sha512-+k1pnlgt4F1H8L7t3z95o3/KO+o78INEcXTbnoJQ/F2VqDVhWoaiVml/OEHv9HsVgxUaVW+IbiZPUJQfF/YxZw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<title>Pizza Dutt</title>
<style>


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
    max-width: 400px;
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
</style>
</head>
<body>
<div class="container">
	<br />
<a href="PageLogin.jsp">Voltar</a>
<br /><br />

<h2>Novo funcionário</h2>
<br>

Nome: <input type="text" id="nome" /><br />
E-mail: <input type="text" id="email" /><br />
Senha: <input type="password" id="senha" /><br /><br />

<input type="button" class="botoes"  value="Concluir Cadastro" onclick="inserir()"/>

<script type="text/javascript">
	function inserir(){
		var jsonEnvio = {};
		jsonEnvio.email     = document.getElementById("email").value;
		jsonEnvio.nome      = document.getElementById("nome").value;
		jsonEnvio.senha     = document.getElementById("senha").value;
		jsonEnvio.acao = "inserir";
		$.ajax({
			url: "FuncionarioServlet",
			data: jsonEnvio,
			type:"post",
			success: function (resp){
				if (resp=="ok"){
					alert("Funcionario cadastrado com sucesso!");
					document.location.href = "PageLogin.jsp?email=" + jsonEnvio.email;
				}else{
					alert(resp)
				}
			},
			error: function (){
				alert("Ocorreu um erro ao cadastrar!!!");
			}
		});
	}
</script>
	</div>
</body>
</html>