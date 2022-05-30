<h1 align='center'>🚗<strong>Restful Rent A Car Application</strong>🚗</h1>

<p align='center'> 
   This project is a comprehensive, monolithic-nLayered Spring Boot application working with rest services. This project. This project was written purely defensively. It has been protected for all possible glitches. OOP rules and SOLID-DRY principles were adhered to throughout the project. You can see the technologies used in the project in the tech stack section.
</p>

<hr></hr>

<h2 align='center'>🌏 You can test my API by clicking the Heroku <a href="https://rent-a-car-application.herokuapp.com/swagger-ui/index.html">link</a>🔗.</h2>

## Tech Stack
```
- Java 8 (for deploy on Heroku)
- Maven
- Lombok
- Spring Boot
- Spring Web
- Spring Data Jpa
- Spring Validation
- OpenApi (Swagger)
- PostgreSQL & PostgreSQL Driver
- Hibernate
- Modelmapper
- JUnit 5
- Mockito
- Git Bash
- Heroku
```
---

<h1 align='center'>📂N-Layered Architecture File Structure🧱</h1>
<h3 align = 'center'><strong>You can access the codes by clicking the layer links below.</strong></h3>

<ul><li><a href="https://github.com/hzyazilimci/rentACar-App/tree/master/src/main/java/com/turkcell/rentACar/entities">Entity Layer</a> contains source entitie, dto, request classes.</li>
      <li><a href="https://github.com/hzyazilimci/rentACar-App/tree/master/src/main/java/com/turkcell/rentACar/dataAccess">Repository Layer</a> containing database related operations.</li>
      <li><a href="https://github.com/hzyazilimci/rentACar-App/tree/master/src/main/java/com/turkcell/rentACar/business">Business Layer</a> contains business logic codes</li>
       <li><a href="https://github.com/hzyazilimci/rentACar-App/tree/master/src/main/java/com/turkcell/rentACar/api">Controller Layer</a> contains Rest APIs.</li>
       <li><a href="https://github.com/hzyazilimci/rentACar-App/tree/master/src/main/java/com/turkcell/rentACar/core">Core Layer</a> contains cross-cutting concerns and common requirements.</li>
</ul>

---

<h1 align='center'>ER DIAGRAM</h1>

![ERD](https://user-images.githubusercontent.com/83385573/171025062-e2f9d4b9-5bc4-431c-894e-bda1e417ea9d.JPG)

---

<h1 align='center'>💻ENDPOINTS📱</h1>
<h3 align = 'center'><strong>You can access endpoints by clicking screenshot that you want below.</strong></h3>

<a href="https://github.com/hzyazilimci/rentACar-App/blob/master/src/main/java/com/turkcell/rentACar/api/controllers/CarsController.java">![cars](https://user-images.githubusercontent.com/83385573/171030427-6bce88f2-afcf-404c-8c56-f2dc4b2cbe72.JPG)</a>

<a href="https://github.com/hzyazilimci/rentACar-App/blob/master/src/main/java/com/turkcell/rentACar/api/controllers/RentalsController.java">![rental](https://user-images.githubusercontent.com/83385573/171030578-3421803a-2a7f-4f17-91dc-1aca3de7b33f.JPG)</a>

<a href="https://github.com/hzyazilimci/rentACar-App/blob/master/src/main/java/com/turkcell/rentACar/api/controllers/PaymentsController.java">![payment](https://user-images.githubusercontent.com/83385573/171030588-794ae5b2-76e6-459d-aa35-450a8a6bc3df.JPG)</a>

<a href="https://github.com/hzyazilimci/rentACar-App/blob/master/src/main/java/com/turkcell/rentACar/api/controllers/InvoicesController.java">![invoice](https://user-images.githubusercontent.com/83385573/171030599-4db4627e-aae7-4f79-8c1c-c2ba51656117.JPG)</a>

<a href="https://github.com/hzyazilimci/rentACar-App/blob/master/src/main/java/com/turkcell/rentACar/api/controllers/CarMaintenancesController.java">![maintenance](https://user-images.githubusercontent.com/83385573/171030619-af322fc1-12cb-49dd-85da-ee674e0903ed.JPG)</a>

<a href="https://github.com/hzyazilimci/rentACar-App/blob/master/src/main/java/com/turkcell/rentACar/api/controllers/CarDamagesController.java">![damage](https://user-images.githubusercontent.com/83385573/171030628-f8445828-d394-4ccd-90e0-d3c665239760.JPG)</a>

<a href="https://github.com/hzyazilimci/rentACar-App/blob/master/src/main/java/com/turkcell/rentACar/api/controllers/BrandsController.java">![brands](https://user-images.githubusercontent.com/83385573/171030476-067426e1-942d-4812-9dba-d59e6fff92dd.JPG)</a>

<a href="https://github.com/hzyazilimci/rentACar-App/blob/master/src/main/java/com/turkcell/rentACar/api/controllers/ColorsController.java">![colors](https://user-images.githubusercontent.com/83385573/171030496-be605cb2-d4d6-41bb-9a9a-0ab32b789419.JPG)</a>

<a href = "https://github.com/hzyazilimci/rentACar-App/blob/master/src/main/java/com/turkcell/rentACar/api/controllers/CitiesController.java">![cities](https://user-images.githubusercontent.com/83385573/171030512-dd4f42a3-c52d-4cf9-9ffd-8e3f902ff9ab.JPG)</a>

<a href = "https://github.com/hzyazilimci/rentACar-App/blob/master/src/main/java/com/turkcell/rentACar/api/controllers/AdditionsController.java"> ![additions](https://user-images.githubusercontent.com/83385573/171030545-535ed5e1-a304-4157-afde-a0aae2be1a25.JPG)</a>

<a href="https://github.com/hzyazilimci/rentACar-App/blob/master/src/main/java/com/turkcell/rentACar/api/controllers/CustomersController.java">![customers](https://user-images.githubusercontent.com/83385573/171030819-168af1d0-73cf-44d0-8af1-0d7c657db82c.JPG)</a>

<a href="https://github.com/hzyazilimci/rentACar-App/blob/master/src/main/java/com/turkcell/rentACar/api/controllers/CorporateCustomersController.java">![corporateCus](https://user-images.githubusercontent.com/83385573/171030852-56f91204-3162-44e2-9ddf-36e47b5af1ad.JPG)</a>

<a href="https://github.com/hzyazilimci/rentACar-App/blob/master/src/main/java/com/turkcell/rentACar/api/controllers/IndividualCustomersController.java">![indviduual](https://user-images.githubusercontent.com/83385573/171030871-2dce8c77-d24c-442a-89df-976d3928c0cb.JPG)</a>

<a href="https://github.com/hzyazilimci/rentACar-App/blob/master/src/main/java/com/turkcell/rentACar/api/controllers/CardInfosController.java">![cardInfo](https://user-images.githubusercontent.com/83385573/171030883-4ebb9383-253b-47c1-ab48-234f9289e959.JPG)</a>

---
<h2 align='center'>UNIT TEST COVERAGE RESULT WILL BE ADDED</h2>

