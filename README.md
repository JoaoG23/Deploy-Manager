# API Gerenciador de Deploys Aplicação 🧑🏽‍💼

O projeto do Gerenciador de Deploys de Aplicação tem como objetivo criar uma ferramenta que facilite e gerencie os informações deploys de aplicações.

### Requisitos Funcionais

- [X]  Um usuário deve poder fazer login no sistema usando seu nome de usuário e senha
- [X]  Usuário poderá cadastrar, editar, visualizar, deletar uma usuário
- [X]  Usuário poderá cadastrar, editar, visualizar, deletar uma empresa
- [X]  Usuário poderá cadastrar, editar, visualizar, deletar uma aplicação

### Não Funcionais

Aqui serão descritos os requisitos não funcionais do projeto, estes são os critérios que não se relacionam diretamente com o comportamento específico do sistema, mas com a qualidade do sistema como um todo.

- [X]  Uso de criptografia **[Bcrypt](https://www.npmjs.com/package/bcrypt)** para senhas, a fim de proteger as informações confidenciais do usuário
- [X]  Implementação de autenticação JWT para garantir a segurança dos dados do usuário


## Tecnologias utilizadas 🪄

  [![Insomnia](https://img.shields.io/badge/Insomnia-5849BE?style=for-the-badge&logo=insomnia&logoColor=white)](https://insomnia.rest/)
  [![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
  [![Java Spring Web](https://img.shields.io/badge/Java%20Spring%20Web-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
  [![Lombok](https://img.shields.io/badge/Lombok-BC4521?style=for-the-badge&logo=lombok&logoColor=white)](https://projectlombok.org/)
  [![Spring DevTools](https://img.shields.io/badge/Spring%20DevTools-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.devtools)
  [![Arquitetura REST](https://img.shields.io/badge/Arquitetura%20REST-blue?style=for-the-badge&logo=rest&logoColor=white)](https://www.redhat.com/en/topics/api/what-is-a-rest-api)
  [![PostgresSQL](https://img.shields.io/badge/PostgresSQL-336791?style=for-the-badge&logo=postgresql&logoColor=white)](https://www.postgresql.org/)


## Como instalar 🔑

Para configurar o projeto Deploy-manager, siga as instruções abaixo:

#### Backend (Spring Boot);

`Ambiente DEVELOPER`

1. Entrar na pasta do backend:
   ```bash
   cd backend
   ```

2. Configurar o arquivo `application.properties` para as configurações do banco de dados (localizado em `src/main/resources`):

    ```properties
    # HIBERNATE
    spring.datasource.url=jdbc:postgresql://localhost:5432/db_deploy_manager
    spring.datasource.username=admin
    spring.datasource.password=admin
    
    # SERVER
    server.error.include-stacktrace=never
    server.port=8081
    server.servlet.contextPath=/api
    
    # TOKENS
    api.security.token.secret=${JWT_Secret:joao}
    ```

3. Construir o projeto com Maven:
   ```bash
   ./mvnw clean install
   ```

4. Iniciar o servidor Spring Boot:
   ```bash
   ./mvnw spring-boot:run
   ```
   
`Ambiente PRODUCTION DOCKER`

1. Crie uma pasta chamada `db` neste diretório.
2. Dentro desta pasta, crie dois arquivos:
    - `db.env` com as seguintes informações:

        ```
        POSTGRES_USER=nome_usuario
        POSTGRES_PASSWORD=senha
        
        ```

    - `init.sql` com os seguintes comandos:
      -- Substitua `nome_usuario` pelo nome do usuário selecionado para a aplicação.

        ```
        CREATE USER db_deploy_manager;
        CREATE DATABASE db_deploy_manager;
        GRANT ALL PRIVILEGES ON DATABASE db_deploy_manager TO nome_usuario;
        
        ```

3. No arquivo `application-prod.properties`, altere as informações para corresponderem aos dados do seu banco:

    ```
    # JPA
    spring.datasource.url=jdbc:postgresql://db-deploy-manager-container:5432/db_deploy_manager
    spring.datasource.username=nome_usuario
    spring.datasource.password=senha
    
    # SERVER
    server.error.include-stacktrace=never
    server.port=8080
    server.servlet.contextPath=/api
    
    # TOKENS
    api.security.token.secret=${JWT_Secret:joao}
    
    #spring.jpa.hibernate.ddl-auto=update
    #spring.jpa.properties.hibernate.jdbc.lab.non_contextual_creation=true
    #flyway.ignoreMigrationPatterns="repeatable:missing"
    
    ```

4. Agora, apenas execute o comando `docker-compose up -d` no diretório raiz.


## Como usar 👨🏽‍🏫

1. Certifique-se de que o banco de dados PostgreSQL está configurado e em execução.
2. No terminal, navegue até o diretório raiz do projeto.
3. Execute o seguinte comando para iniciar o servidor Spring Boot:

   ```bash
   ./mvnw spring-boot:run
   ```

4. O servidor Spring Boot será iniciado e estará ouvindo as requisições na porta definida.

    ```bash
    http://localhost:8081/login
    ```

5. Utilize as rotas e endpoints disponibilizados pelo servidor para gerenciar os processos de deploy.

Link documentação: https://doc-deploy-manager.netlify.app/#req_57f32835a4da4a64946ef9bff6a1330e

1. **Acesse a Pasta de Documentos**:
    - Navegue até a pasta `/docs` no seu computador.

2. **Localize o Arquivo da Coleção**:
    - Encontre o arquivo JSON chamado `insomnia.json`.

3. **Abrir o Insomnia**:
    - Inicie o Insomnia no seu computador.

4. **Importar a Coleção**:
    - Clique no ícone do menu no canto superior esquerdo (três linhas horizontais).
    - Selecione "Import/Export".
    - Clique em "Import Data".
    - Escolha "From File".
    - Navegue até a pasta `/docs`, selecione o arquivo da coleção e clique em "Open".

5. **Verificar Importação**:
    - Após a importação, verifique se a coleção foi adicionada corretamente no Insomnia.

Pronto! A coleção deve estar agora disponível no Insomnia para você utilizar.

## Autor

O Deploy-manager foi criado por João Guilherme, um desenvolvedor de software com vasta experiência em automação de processos e implantação de sistemas. Este projeto foi desenvolvido para gerenciar informações de deploy em diferentes ambientes.

![avatar](https://images.weserv.nl/?url=https://avatars.githubusercontent.com/u/80895578?v=4?v=4&h=100&w=100&fit=cover&mask=circle&maxage=7d)

<sub><b>João Guilherme</b></sub></h4> <a href="https://github.com/JoaoG23/">🚀</a>

Feito com 🤭 por João Guilherme 👋🏽 Entre em contato logo abaixo!

[![Linkedin Badge](https://img.shields.io/badge/-Joao-blue?style=flat-square&logo=Linkedin&logoColor=white&link=https://www.linkedin.com/in/jaoo/)](https://www.linkedin.com/in/joaog123/)
[![Badge](https://img.shields.io/badge/-joaoguilherme94@live.com-c80?style=flat-square&logo=Microsoft&logoColor=white&link=mailto:joaoguilherme94@live.com)](mailto:joaoguilherme94@live.com)

## Licença

[![Licence](https://img.shields.io/github/license/Ileriayo/markdown-badges?style=for-the-badge)](./LICENSE)
