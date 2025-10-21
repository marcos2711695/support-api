# Support API (Microservice 2)

- Spring Boot WebFlux + R2DBC/H2 + JWT (functional endpoints)
- Manages exchange rates and logs transactions (audit)
- Swagger UI at `/swagger-ui.html`
- Port 9090

## Endpoints
- GET `/api/v1/rates?from=USD&to=PEN`
- POST `/api/v1/rates` body: `{ "from": "USD", "to":"PEN", "rate": 3.75 }`
- POST `/api/v1/transactions` body: `{ "userId":1, "userName":"Jane", "from":"USD", "to":"PEN", "amount":10, "rate":3.8, "convertedAmount":38 }`
