<h1 align='center'><strong>Restful Rent A Car Application</strong></h1>

<p align='center'> 
   This project is a comprehensive, monolithic-nLayered Spring Boot application working with rest services. This project. This project was written purely defensively. It has been protected for all possible glitches. OOP rules and SOLID-DRY principles were adhered to throughout the project. You can see the technologies used in the project in the tech stack section.
</p>

<hr></hr>

<p align='center'> You can test my API by clicking the Heroku <a href="https://rent-a-car-application.herokuapp.com/swagger-ui/index.html">link</a>.</p>

## Tech Stack

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

---

<h1 align='center'>N-Layered Architecture File Structure</h1>  
<li>
   <ul>
      <li><h2><a href="https://github.com/hzyazilimci/rentACar-App/tree/master/src/main/java/com/turkcell/rentACar/entities">Entity Layer</a> contains source entitie, dto, request classes.</h2></li>
      <li><h2><a href="https://github.com/hzyazilimci/rentACar-App/tree/master/src/main/java/com/turkcell/rentACar/dataAccess">Repository Layer</a> containing database related operations.</h2></li>
      <li><h2><a href="https://github.com/hzyazilimci/rentACar-App/tree/master/src/main/java/com/turkcell/rentACar/business">Business Layer</a> contains business logic codes.</h2></li>
       <li><h2><a href="https://github.com/hzyazilimci/rentACar-App/tree/master/src/main/java/com/turkcell/rentACar/api">Controller Layer</a> contains Rest API's.</h2></li>
       <li><h2><a href="https://github.com/hzyazilimci/rentACar-App/tree/master/src/main/java/com/turkcell/rentACar/core">Core Layer</a> contains cross-cutting concerns and common requirements.</h2></li>
   </ul>  
</li>


<h2 align='center'> ER DIAGRAM </h2>

![ERD](https://user-images.githubusercontent.com/83385573/171025062-e2f9d4b9-5bc4-431c-894e-bda1e417ea9d.JPG)

