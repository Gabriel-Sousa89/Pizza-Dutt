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


body {
    font-family: Arial, sans-serif;
    background-color: #f8f8f8;
    padding: 20px;
}


h2 {
    font-size: 28px;
    color: #333;
    margin-bottom: 20px;
}


a {
    text-decoration: none;
    color: #007bff;
    font-size: 16px;
    margin-bottom: 20px;
    display: inline-block;
}

a:hover {
    text-decoration: underline;
}


#imoveis {
    display: flex;
    flex-wrap: wrap;
    gap: 30px;
    justify-content: flex-start; 
}


#imoveis div {
    background-color: #fff;
    border-radius: 8px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    padding: 20px; 
    width: 250px; 
    text-align: center;
    font-size: 16px;
    color: #333;
    margin-bottom: 20px; 
}


#imoveis img {
    width: 180px; 
    height: 180px;
    border-radius: 8px;
    object-fit: cover; 
    border: 2px solid #ddd;
    margin-bottom: 10px; 
}


#imoveis div p {
    font-size: 14px;
    color: #555;
    margin: 5px 0;
}


.container {
    max-width: 1200px;
    margin: 0 auto; 
    padding: 20px;
}

</style>
</head>
<body>
	<div class="container">
<br />
<a href="PageLogin.jsp">Fazer Login</a>
<br /><br />

    


<h2>Lista de Sabores</h2><br />

<div id="imoveis">

<script>
	function consultar(){
		var jsonEnvio = {};
		jsonEnvio.acao = "consultar";
		$.ajax({
			url: "ImovelServlet",
			data: jsonEnvio,
			type:"post",
			success: function (resp){
				var jsonVetor = JSON.parse(resp);
				var linhas = "";
				
				for (i=0;i<jsonVetor.length;i++){
					
					var codigo = jsonVetor[i].codigo;
					var foto   = jsonVetor[i].foto;
					var tipo   = jsonVetor[i].tipo;
					var valor  = jsonVetor[i].valor;
					
					linhas += `
						<img src="` + foto + `" style="width: 120px; height: 120px; border: 1px solid black" /><br />
						Sabor: ` + tipo + `<br />
						Valor: R$ ` + valor + `
						<br />
						<br />						
					`;
				}
				
				document.getElementById("imoveis").innerHTML = linhas;
			},
			error: function (){
				alert("Ocorreu um erro ao consultar!!!");
			}
		});
	}
	consultar();
</script>

</div>
</div>
</body>
</html>