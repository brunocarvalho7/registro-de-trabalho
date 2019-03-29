
# Registro de Horas Trabalhadas
#### Api para o registro de horas trabalhadas de um usuário

A partir de um commit realizado na branch *master* do projeto, será disparado uma task de intergração contínua no  [CircleCI](https://circleci.com/gh/brunocarvalho7/registro-de-trabalho/tree/master), na qual esse fará todos os testes unitários e de integração e caso o build seja bem sucedido será feito uma análise de qualidade de código no [SonarCloud.io](https://sonarcloud.io/dashboard?id=brunocarvalho7_registro-de-trabalho) e posteriormente o deploy automático da aplicação no [Heroku](https://api-desafio-bruno.herokuapp.com/swagger-ui.html)

> **Documentação da API:** *https://api-desafio-bruno.herokuapp.com/*

> **API Base URL:** *https://api-desafio-bruno.herokuapp.com/v1/*
> **Usuário padrão:** *admin*
> **Senha padrão:** *123*




### Tecnologias Utilizadas

* Java
* Spring Boot
* Spring Security
* H2 - Banco de Dados
* Swagger 2 - Geração da documentação da API
* JUnit - Testes
* [SonarCloud.io](https://sonarcloud.io/dashboard?id=brunocarvalho7_registro-de-trabalho) - Controle de Qualidade do código
* [CircleCI](https://circleci.com/gh/brunocarvalho7/registro-de-trabalho/tree/master) - Servidor de Integração Contínua
* [Heroku](https://api-desafio-bruno.herokuapp.com/swagger-ui.html) - Servidor da aplicação