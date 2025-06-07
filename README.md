# 🌍 ORBIS — Conectando Solidariedade em Tempos de Emergência

![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white)
![Spring](https://img.shields.io/badge/SpringBoot-6DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Oracle](https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-4EA94B?style=for-the-badge&logo=mongodb&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-FF6600.svg?style=for-the-badge&logo=rabbitmq&logoColor=white)

## ✨ Sobre

A ORBIS é uma aplicação desenvolvida com foco em situações de emergência, como enchentes, deslizamentos e falhas de infraestrutura. O sistema permite que pessoas em risco façam pedidos de ajuda geolocalizados, conectando-as a doadores, voluntários e ONGs da região. Leve, resiliente e com suporte a mensagens assíncronas via RabbitMQ, a ORBIS é ideal para operar mesmo em locais com conectividade limitada.

A plataforma é composta por duas aplicações Spring Boot independentes: uma produtora e outra consumidora de mensagens, integradas via mensageria para garantir agilidade no fluxo de informações. Além disso, a API oferece suporte a bancos de dados Oracle (relacional) e MongoDB (não relacional), internacionalização e dashboard com Spring Boot Actuator.

---

## 👩‍💻 Desenvolvedores

- [Sabrina Couto](https://github.com/sabrinacouto) - Backend Developer & QA - RM552728
- [Juliana Mo.](https://github.com/julianamo93) - Solutions Data Architect & Cloud - RM554113
- [Kevin Nobre](https://github.com/KevinNobre) - FullStack Developer - RM552590

Projeto desenvolvido por alunos do segundo ano do curso de Análise e Desenvolvimento de Sistemas da FIAP, para a Global Solution 01/2025.

---

### Configurar o Projeto no IntelliJ IDEA
<ul>
  <li>Selecione a opção "Open" e navegue até o diretório do projeto Smartooth AI clonado.</li>
  <li>Em Project Structure garanta que o SDK esteja para o Java 17.</li>
  <li>Verifique no IntelliJ se a aba do Gradle está presente na barra lateral.</li>
  <li>Caso o projeto não seja automaticamente reconhecido como Gradle, abra o arquivo build.gradle e aceite a importação do Gradle quando o IntelliJ perguntar.</li>
</ul>

### Configurar o JDBC para o Oracle Database
Configurar as credenciais do banco de dados Oracle no application.properties:
  ```bash
  spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/XEPDB1
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
```

## 💾 Rodando o Projeto Localmente

### Clonar o repositório

```bash
https://github.com/sabrinacouto/OrbisApplication.git
```

## 🌐 Endpoints da API

### 📍 Usuário

| Método  | Endpoint                  | Descrição                       |
|---------|---------------------------|----------------------------------|
| GET     | `/usuario`                | Lista todos os usuários         |
| POST    | `/usuario/cadastro`       | Cadastra novo usuário           |
| GET     | `/usuario/editar/{id}`    | Formulário de edição            |
| PATCH   | `/usuario/editar/{id}`    | Atualiza dados do usuário       |
| DELETE  | `/usuario/deletar/{id}`   | Remove usuário                  |


## 📬 Mensageria com RabbitMQ

O ORBIS utiliza RabbitMQ para comunicação assíncrona entre serviços, garantindo escalabilidade e desacoplamento. A aplicação produtora envia mensagens para uma fila específica, e a aplicação consumidora escuta e processa essas mensagens.


### Verificação do envio de mensagens via RabbitMQ:
A cada ação realizada (cadastro, edição ou exclusão), uma mensagem é enviada para o sistema de mensageria RabbitMQ.
Para acompanhar as mensagens enviadas:

- Acesse o painel do CloudAMQP: https://fly.rmq.cloudamqp.com/#/
- Faça login com as credenciais.
- Localize a fila.
- Verifique se há mensagens sendo enviadas para a fila conforme as ações executadas na aplicação.

### 🔧 Configuração no `application.properties` (rodando localmente)

```properties
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

orbis.broker.queue.usuario=usuarioQueue
orbis.broker.exchange.usuario=usuarioExchange
orbis.broker.routingkey.usuario.created=usuario.created.routingkey
```

## 📦 Deploys

- 🔗 Repositório GitHub: https://github.com/sabrinacouto/OrbisApplication
- 🚀 Deploy da aplicação: https://orbis-backend.fly.dev
- ☁️ Acesso RabbitMQ (CloudAMQP): https://fly.rmq.cloudamqp.com/#/

## Vídeo Pitch
https://www.youtube.com/watch?v=A1gLzrINBCg

## Vídeo da Aplicação
https://www.youtube.com/watch?v=wWm955Ofms8

## 📍 Dependências
```gradle
dependencies {
implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	implementation 'org.springframework.boot:spring-boot-starter-validation'
	implementation 'org.springframework.boot:spring-boot-starter-security'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	runtimeOnly 'com.oracle.database.jdbc:ojdbc11:23.3.0.23.09'
	implementation 'org.springframework.boot:spring-boot-starter-data-mongodb'
	implementation 'org.springframework.boot:spring-boot-starter'

	compileOnly 'org.projectlombok:lombok:1.18.30'
	annotationProcessor 'org.projectlombok:lombok:1.18.30'
	runtimeOnly 'com.oracle.database.jdbc:ojdbc11'

	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testImplementation 'org.springframework.security:spring-security-test'
	implementation 'org.apache.commons:commons-lang3:3.12.0'
	implementation 'org.springframework.boot:spring-boot-starter-amqp'
	testImplementation 'org.mockito:mockito-core:5.12.0'
	testImplementation 'org.testcontainers:junit-jupiter'
	testImplementation 'org.testcontainers:rabbitmq'
	testImplementation 'com.h2database:h2:2.1.214'
	testImplementation 'org.junit.platform:junit-platform-suite-api:1.10.0'
	testImplementation 'org.junit.jupiter:junit-jupiter:5.9.3'
}
```


  




