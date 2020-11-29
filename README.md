# q-api
Api para facilitar o acesso aos dados do Q-Acadêmico de forma programática

#### Obter Token de Login % GET /api/login?username={usuario}&password={senha}

Autentica o usuário com o Q-Acadêmico e retorna o token de acesso que identifica o usuário.

###### Resposta em caso de sucesso
```json
{
  "token": "9824612931215846"
}
```

#### Get Materiais de % GET /api/materiais

Autentica o usuário com o Q-Acadêmico e retorna o token de acesso que identifica o usuário.

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
