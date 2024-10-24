# TownSq Challenge

---


## About

---
## Tech Stack

 * Java 21
 * Spring-boot 3.3.4
 * Docker
 * Maven
 * JPA
 * Postgres
---
## Getting started


#### requirements
 * Docker
 * Docker compose

### How to run

Run the command bellow inside the project folder

```
docker-compose up --build
```

Wait to complete the build processing, it may take about 10 to 15 minutes in the first time.

After the first time, you can run the command without the '--build'

```
docker-compose up
```
---

## Using the Api

You can find a postman collection inside the project, suit yourself to import and use it

### Endpoints:

#### post /auth
Authenticate using a user and password

````
curl --location 'localhost:8080/auth' \
--header 'Content-Type: application/json' \
--data '{
    "username":"townsq_super",
    "password":"verySecurePassword"
}'
````

expected response:
````
{
    "token": "Bearer eyJhbGciopOg"
}
````

---
#### post /users
(ADMIN users can use this endpoint) \
Create a new user with DEFAULT role

````
curl --location 'localhost:8080/users' \
--header 'Authorization: PLACE_YOUR_TOKEN_HERE' \
--header 'Content-Type: application/json' \
--data '{
    "username": "mateus",
    "password": "senha"
}'
````

expected response:
````
{
    "id": 2,
    "username": "mateus",
    "role": "DEFAULT"
}
````

---
#### patch /users/{Id}
(ADMIN users can use this endpoint) \
Edit a user, allowing to change username and role (ADMIN, ACCOUNT_MANAGER, DEFAULT)

````
curl --location --request PATCH 'localhost:8080/users/{Id}' \
--header 'Authorization: PLACE_YOUR_TOKEN_HERE' \
--header 'Content-Type: application/json' \
--data '{
    "username": "newUsername",
    "role": "ACCOUNT_MANAGER"
}'
````

expected response:
````
{
    "id": 2,
    "username": "mateus",
    "role": "ACCOUNT_MANAGER"
}
````

---
#### get /users
Retrieves information about current authenticated user

````
curl --location 'localhost:8080/users' \
--header 'Authorization: PLACE_YOUR_TOKEN_HERE'
````
expected response:
````
{
    "id": 1,
    "username": "townsq_super",
    "role": "ADMIN"
}
````

---
##### post /orders
Create a new order

```
curl --location 'localhost:8080/orders' \
--header 'Authorization: PLACE_YOUR_TOKEN_HERE' \
--header 'Content-Type: application/json' \
--data '{
"items": [
{"itemName": "Pastel", "quantity": 2, "unityValue": 6.5},
{"itemName": "Coca-cola", "quantity": 1, "unityValue": 10.5},
{"itemName": "Abacate", "quantity": 1, "unityValue": 2.3}
]
}'
```

expected response:
````
{
    "id": 1,
    "items": [
        {
            "itemName": "Pastel",
            "quantity": 2,
            "unityValue": 6.5
        },
        {
            "itemName": "Coca-cola",
            "quantity": 1,
            "unityValue": 10.5
        },
        {
            "itemName": "Abacate",
            "quantity": 1,
            "unityValue": 2.3
        }
    ],
    "totalValue": 25.8
}
````

---
#### get /orders
Get orders from authenticated user

````
curl --location 'localhost:8080/orders' \
--header 'Authorization: PLACE_YOUR_TOKEN_HERE'
````

expected response:
````
[
    {
        "id": 1,
        "items": [
            {
                "itemName": "Pastel",
                "quantity": 2,
                "unityValue": 6.50
            },
            {
                "itemName": "Coca-cola",
                "quantity": 1,
                "unityValue": 10.50
            },
            {
                "itemName": "Abacate",
                "quantity": 1,
                "unityValue": 2.30
            }
        ],
        "totalValue": 25.80
    }
]
````

---
#### get /orders/{Id}
Retrieve an order by Id

````
curl --location 'localhost:8080/orders/{Id}' \
--header 'Authorization: PLACE_YOUR_TOKEN_HERE'
````

expected response:
````
{
    "id": 1,
    "items": [
        {
            "itemName": "Pastel",
            "quantity": 2,
            "unityValue": 6.50
        },
        {
            "itemName": "Coca-cola",
            "quantity": 1,
            "unityValue": 10.50
        },
        {
            "itemName": "Abacate",
            "quantity": 1,
            "unityValue": 2.30
        }
    ],
    "totalValue": 25.80
}
````

---
#### post /payments
(ACCOUNT_MANAGER users can use this endpoint) \
Process a payment by passing the order id

````
curl --location 'localhost:8080/payments' \
--header 'Authorization: PLACE_YOUR_TOKEN_HERE' \
--header 'Content-Type: application/json' \
--data '{
    "orderId": 1
}'
````

expected response:
````
{
    "id": 1,
    "isPaid": true,
    "orderId": 1,
    "paidValue": 25.80
}
````

---
#### get /payments/{Id}
Get a payment by Id

````
curl --location 'localhost:8080/payments/1' \
--header 'Authorization: PLACE_YOUR_TOKEN_HERE'
````

expected response:
````
{
    "id": 1,
    "isPaid": true,
    "orderId": 1,
    "paidValue": 25.80
}
````