<p align="center">
  <p align="center"><img width="258" height="55" src="https://i.imgur.com/ksRkRFo.png" alt="logo"><p>

   <h2 align="center">Pizzify</h2>
   <h3 align="center">The back-end application</h2>
</p>


## Summary

1. [Introduction](#introduction)
2. [Prerequisites](#prerequisites)
3. [Installation](#installation)
4. [How to use](#how-to-use)
6. [Contributing](#contributing)
7. [License](#license--copyright)

## External Links

- 📄 [Pizzify Docs: https://docs.pizzify.emanuelcorrea.dev/docs/api](https://docs.pizzify.emanuelcorrea.dev/docs/api)
- 🚀 [Pizzify (API Endpoint) Backend: https://api.pizzify.emanuelcorrea.dev ](https://api.pizzify.emanuelcorrea.dev)
- 💎 [Postman Collection: https://documenter.getpostman.com/view/14677746/2sA2xh3t1M](https://documenter.getpostman.com/view/14677746/2sA2xh3t1M)
- 💻 [Pizzify Frontend: https://pizzify.emanuelcorrea.dev](https://pizzify.emanuelcorrea.dev)

## Introduction

**Pizzify Backend Technical Overview**

Pizzify's backend, built with Java and Spring Boot, facilitates online pizza orders and manages crucial entities like Customers, Orders, Pizzas, Categories, and the Cart. Key features include:

- **👧 Customers:** User profiles for login, order tracking, and preferences.

- **📃 Orders:** Central entity recording order details, customer information, and order history.

- **🍕 Pizzas:** Independent entities with attributes like flavor, size, ingredients, and price.

- **📦 Categories:** Organizes pizzas logically for a user-friendly online menu.

- **🛒 Cart:** Allows customers to add pizzas, providing an intuitive shopping experience.

The backend employs a relational database with Hibernate for efficient data management. **RESTful** endpoints enable interaction with the frontend, and security measures include authentication and authorization. The modular, scalable architecture supports easy expansion and integration, laying a robust foundation for the entire Pizzify ecosystem.


## Prerequisites

Make sure you have the following prerequisites installed before starting the project:

- [**Java:**](https://www.oracle.com/java/technologies/javase-downloads.html) Version 17 or higher.
- [**Spring Boot:**](https://spring.io/projects/spring-boot) Version 3.1.5, with JPA (Java Persistence API) support.
- [**PostgreSQL:**](https://www.postgresql.org/download/) Version 16, installed and configured PostgreSQL relational database.

Check the official documentation for each technology for detailed instructions on installation and setup.

## Installation

Follow these steps to install and set up the project:

1. **Clone the repository:**
   ```bash
   git clone https://github.com/emanuelcorrea/pizzify-backend.git
   cd pizzify-backend

2. **Build the project:**
   ```bash
   ./mvnw clean install

3. **Run the application:**
   ```bash
   ./mvnw spring-boot:run

## How to Use

Before starting use, you need to configure the `application.properties`:

Edit the `application.properties` file with your database connection details. Here's an example:

      spring.datasource.url=jdbc:postgresql://localhost:5432/pizza
      spring.datasource.username=postgres
      spring.datasource.password=root


## Contributing

We are happy to receive issues describing bug reports and feature
requests! If your bug report relates to a security vulnerability,
please do not file a public issue, and please instead reach out to us
at emanueldascorrea@gmail.com.

We do not accept (and do not wish to receive) contributions of
user-created or third-party code, including patches, pull requests, or
code snippets incorporated into submitted issues. Please do not send
us any such code! Bug reports and feature requests should not include
any source code.

If you nonetheless submit any user-created or third-party code to us,
(1) you assign to us all rights and title in or relating to the code;
and (2) to the extent any such assignment is not fully effective, you
hereby grant to us a royalty-free, perpetual, irrevocable, worldwide,
unlimited, sublicensable, transferrable license under all intellectual
property rights embodied therein or relating thereto, to exploit the
code in any manner we choose, including to incorporate the code to redistribute it under any terms at our discretion.

## License & Copyright

This project is licensed under the [MIT License](LICENSE) - see the [LICENSE](LICENSE) file for details.
