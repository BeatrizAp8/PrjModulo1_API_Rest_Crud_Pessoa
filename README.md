Pessoa API
Projeto exemplo com Spring Boot 3.x - CRUD de Pessoa com paginação e logs para Graylog.

Como rodar
Build e subir com docker-compose: docker-compose up --build

Endpoints:

GET /api/pessoas?page=0
GET /api/pessoas/{id}
POST /api/pessoas
PUT /api/pessoas/{id}
DELETE /api/pessoas/{id}
Graylog UI: http://localhost:9000 (ver docker-compose para usuário/senha)
