# Customer Managing Microservices System
#### Video Demo:

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#installation">Installation</a>
    </li>
    <li>
      <a href="#usage">Usage</a>
    </li>
    <li>
      <a href="#architecture">Architecture</a>
    </li>
    <li>
      <a href="#acknowledgments">Acknowledgments</a>
    </li>
  </ol>
</details>

## About The Project
This is an enterprise-style customer management system which includes JWT-based authentication, usage of gRPC and REST APIs, rate limiting, caching, Kafka event flows and both load and integration testing. The main operations of the customer management system include:

- Creating customers
- Getting all customers
- Updating customers
- Deleting customers
- Searching customers

For a visual representation of how the system works refer to the architecture
section!

## Built With

#### Core Stack:

- [![Java][Java]][Java-url]
- [![Spring Boot][SpringBoot]][SpringBoot-url]
- [![PostgreSQL][PostgreSQL]][PostgreSQL-url]
- [![Redis][Redis]][Redis-url]
- [![Apache Kafka][Kafka]][Kafka-url]
- [![gRPC][gRPC]][gRPC-url]
- [![Docker][Docker]][Docker-url]

#### Tooling and Testing:

- [![Maven][Maven]][Maven-url]
- [![Python][Python]][Python-url]
- [![JUnit][JUnit]][JUnit-url]
- [![REST Assured][RestAssured]][RestAssured-url]
- [![Locust][Locust]][Locust-url]


## Installation

IF you want to use the project locally without docker install:

- Java 21
- Maven
- Python 3 and Locust for testing
- Get a randomly generated JSON token (JWT_SECRET), which can be done by running AuthIntegrationTest.Java in the integration-test folder

Clone the repository:

```Bash
git clone https://github.com/Armand0-Jesus/Customer-Management-Microservices.git
cd Customer-Management-Microservices
```

Add JWT_Secret to .env file:

```Bash
echo "JWT_SECRET=<value>" > .env
```

Start the containerized environment for using docker:

```Bash
docker compose up --build
```

## Usage 

Login:

```Bash
curl -X POST http://localhost:4004/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "testuser@test.com",
    "password": "password123"
  }'
```

Create a customer:

Here are examples of the terminal-based commands for the different functionalities of the project:

Login:

```Bash
curl -X POST http://localhost:4004/api/customers \
  -H "Content-Type: application/json" \
  -H "Authorization: JWT_SECRET" \
  -d '{
    "fullName": "New Customer",
    "email": "new.customer@example.com",
    "shippingAddress": "123 Example Street, San Juan, Puerto Rico 00901",
    "preferredDropDate": "2026-04-18",
    "memberSince": "2026-04-11"
  }'
```

Get all customers:

```Bash
curl http://localhost:4004/api/customers \
  -H "Authorization: Bearer JWT_SECRET"
```

Update a customer:

```Bash
curl http://localhost:4004/api/customers \
  -H "Authorization: Bearer JWT_SECRET"
```

Delete a customer:

```Bash
curl -X DELETE http://localhost:4004/api/customers/CUSTOMER_ID \
  -H "Authorization: Bearer JWT_SECRET"
```

Search a customer:

```Bash
curl "http://localhost:4004/api/customers/search?query=john" \
  -H "Authorization: Bearer JWT_SECRET"
```

Integration tests:

```Bash
cd integration-tests
mvn test
```

Load tests
```Bash
cd load-tests/locust
locust -f locustfile.py --host=http://localhost:4004
```

Ports for each service:

- `4004` API Gateway
- `4005` Auth Service
- `4000` Customer Service
- `4001` Payment Profile Service
- `4002` Analytics Service
- `9001` Payment Profile gRPC
- `5433` Auth DB
- `5434` Customer DB
- `6379` Redis
- `9094` Kafka 
## Architecture

Below is a diagram of the arhictecture of this project which shows how all the services are conected and how they function:

![](https://github.com/user-attachments/assets/...)<img width="1575" height="680" alt="Screenshot 2026-04-09 122544" src="https://github.com/user-attachments/assets/7021acfd-8358-4600-9d0a-37815a1f6ee9" />



## Acknowledgments

Some of the documentation and recourses which helped for the creation of this project:

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/)
- [Spring Cloud Gateway Documentation](https://docs.spring.io/spring-cloud-gateway/reference/)
- [Spring Data Redis Documentation](https://docs.spring.io/spring-data/redis/reference/)
- [Apache Kafka Documentation](https://kafka.apache.org/documentation/)
- [gRPC Documentation](https://grpc.io/docs/)
- [Docker Compose Documentation](https://docs.docker.com/compose/)
- [Locust Documentation](https://docs.locust.io/)
- [REST Assured Documentation](https://rest-assured.io/)
- [Best README Template](https://github.com/othneildrew/Best-README-Template)
- [YouTube Reference](https://www.youtube.com/watch?v=tseqdcFfTUY&t=1s)



[Java]: https://img.shields.io/badge/Java-000000?style=for-the-badge&logo=openjdk&logoColor=white
[Java-url]: https://openjdk.org/projects/jdk/21/
[SpringBoot]: https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white
[SpringBoot-url]: https://spring.io/projects/spring-boot
[SpringCloudGateway]: https://img.shields.io/badge/Spring_Cloud_Gateway-6DB33F?style=for-the-badge&logo=spring&logoColor=white
[SpringCloudGateway-url]: https://spring.io/projects/spring-cloud-gateway
[SpringSecurity]: https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white
[SpringSecurity-url]: https://spring.io/projects/spring-security
[PostgreSQL]: https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white
[PostgreSQL-url]: https://www.postgresql.org/
[Redis]: https://img.shields.io/badge/Redis-FF4438?style=for-the-badge&logo=redis&logoColor=white
[Redis-url]: https://redis.io/
[Kafka]: https://img.shields.io/badge/Apache_Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white
[Kafka-url]: https://kafka.apache.org/
[gRPC]: https://img.shields.io/badge/gRPC-244C5A?style=for-the-badge&logo=data:image/svg%2bxml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNCAyNCI+PHBhdGggZmlsbD0id2hpdGUiIGQ9Ik0xMiAyIDMgN3YxMGw5IDUgOS01VjdsLTktNVptMCAzLjFMMTguMiA4djhMMTIgMTguOSA1LjggMTZWOEwxMiA1LjFaIi8+PHBhdGggZmlsbD0id2hpdGUiIGQ9Ik05IDguNWg2djJoLTR2MS41aDMuM3YySDExdjEuNWg0djJIOXYtOVoiLz48L3N2Zz4=
[gRPC-url]: https://grpc.io/
[Docker]: https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white
[Docker-url]: https://docs.docker.com/compose/
[Maven]: https://img.shields.io/badge/Apache_Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white
[Maven-url]: https://maven.apache.org/
[Python]: https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=python&logoColor=white
[Python-url]: https://www.python.org/
[JUnit]: https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=junit5&logoColor=white
[JUnit-url]: https://junit.org/junit5/
[RestAssured]: https://img.shields.io/badge/REST_Assured-6A1B9A?style=for-the-badge&logo=testinglibrary&logoColor=white
[RestAssured-url]: https://rest-assured.io/
[Locust]: https://img.shields.io/badge/Locust-2E7D32?style=for-the-badge&logo=data:image/svg%2bxml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNCAyNCI+PHBhdGggZmlsbD0id2hpdGUiIGQ9Ik0yIDE2IDggN2w0IDUgMy00IDcgOGgtNWwtMi0yLjMtMi42IDMuM0w4LjIgMTEgNSAxNkgyWiIvPjxwYXRoIGZpbGw9IndoaXRlIiBkPSJNOC42IDcgNi41IDMuNWgyLjJMMTEgN0g4LjZabTYuOCAwIDIuMy0zLjVoMi4yTDE3LjQgN2gtMloiLz48L3N2Zz4=
[Locust-url]: https://locust.io/

