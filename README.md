# q-api
Api para facilitar o acesso aos dados do Q-Acadêmico de forma programática

### O problema

O IFSul e possívelmente outros IFs utilizam o sistema Q-Acadêmico para o gerenciamento institucional. O software é antigo e sua interface e funcionalidades são limitadas. Com isso em mente surgiu a ideia de criar uma api que permita extrair os dados do sistema de uma forma simples e assim talvez estimular o desenvolvimento de uma novas aplicações para acessar o Q-Acadêmico.

<br/>

#### Obter Token de Login % GET /api/login?username={usuario}&password={senha}

Autentica o usuário com o Q-Acadêmico e retorna o token de acesso que identifica o usuário.

###### Resposta em caso de sucesso
```json
{
  "token": "9824612931215846"
}
```

#### Get Materiais de aula % GET /api/materiais?token={token}

Utilizando o token informado, realiza a comunicação com o Q-Acadêmico e retorna uma lista com todos os materiais da disciplina

###### Resposta em caso de sucesso
```json
[
  {
    "nome" : "Nome do arquivo",
    "link" : "http://qacademico.if.edu.br/UPLOADS/linkDoArquivo.ext",
    "data" : "2020-07-09"
  }
]
```

#### Get Horario % GET /api/horario?token={token}&ano={ano}&periodo={periodo}

Utilizando o token informado, realiza a comunicação com o Q-Acadêmico e retorna uma lista com o horário das disciplinas

###### Resposta em caso de sucesso
```json
{
  "segunda": [
    {
      "fim": "08:44",
      "inicio": "08:00",
      "nome": "Criação de Páginas WEB",
      "sigla": "CPW"
    },
    {
      "fim": "09:30",
      "inicio": "08:45",
      "nome": "Criação de Páginas WEB",
      "sigla": "CPW"
    },
    ...
  ]
}
```
