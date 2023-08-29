

<div align="center">
  <h3 align="center">🚀 # VotoCoop-Api, Projeto de votação de pautas - Spring Rest </h3>
 </div>
 




<h3 align="center"> Apresentando a documentação Swagger</h3>
<h4 align="center"> Link de acesso local: http://localhost:8080/swagger-ui/index.html</h4>


![image](https://github.com/Dark1922/AlgaFoods/assets/48605830/cf288288-281e-4927-ad8a-f6d09fa7cc45)




<hr />

   **Conteúdo**

- [x] <strong>Criação de Associado</strong>
- [x] <strong>Alteração de Associado</strong>
- [x] <strong>Consulta de Associado por id ,ou por parginação</strong>
- [x] <strong>Criação de Pauta com tema e descrição</strong>
- [x] <strong>Consulta de Pauta por Tema e id</strong>
- [x] <strong>Criação de sessão de voto</strong>
- [x] <strong>Validação de tempo e monitoramento para fechar a sessão de votos</strong>
- [x] <strong>Envio de mensageria e fila com RabittMQ</strong>
- [x] <strong>Criação de votos a partir de uma sessão aberta, podendo votar somente uma vez</strong>
- [x] <strong>Vizualziar contabilização de votos de uma sessão de votação</strong>
- [x] <strong>Documentação da API com OpenAPI, Swagger UI e SpringFox</strong>
- [x] <strong>Tratações de Exception</strong>
- [x] <strong>Configuração de Paginação</strong>
- [x] <strong>Modelagem de api</strong>
- [x] <strong>FlyWay Migração de dados e criação de tabela</strong>
- [x] <strong>Testes com Mock e de integração</strong>
- [x] <strong>DTO com modelmapper</strong>
- [x] <strong>Teste de performance com gatling</strong>
- [x] <strong>Docker para subir imagem do RabittMQ</strong>
- [x] <strong>Validação de CPF</strong>

<hr />

## :heavy_check_mark: Tecnologias <a name="technologies"></a>

<dl>
<dt><strong>Spring Boot</strong></dt>
<dt><strong>Postgres</strong></dt>
<dt><strong>JDK 17</strong></dt>
<dt><strong>Swagger OpenApi</strong></dt>
<dt><strong>Spring Data</strong></dt>
<dt><strong>JPA</strong></dt>
<dt><strong>Docker</strong></dt>
  <dt><strong>FlyWayDB</strong></dt>
  <dt><strong>Modelmapper</strong></dt>
    <dt><strong>Gatling</strong></dt>
     <dt><strong>TDD</strong></dt>

<dt><strong>Bean validation</strong></dt>
<dd>Ultilizada para fazer validações no sistema e tratamento de erros.</dd>
</dl>
 </div>
  <hr /> 
 
 
 Build do projeto
  
```bash
#Clonar repositório
gh gh repo clone Dark1922/VotaCoop

#Executar o projeto

./gradlew build

starta o projeto

comando docker para startar o rabbitmq

docker pull rabbitmq:management

docker run -d --name rabbitmq -p 15672:15672 -p 5672:5672 rabbitmq:management

http://localhost:15672/  url para ter acesso a mensageria e vê as mensagem enviado pelo servido após as sessão de votação ser encerrada.


```


<hr>
