**Ever changing Hierarchy System**

This Project is an imlementation of Spring Boot containarized using Docker to solve ever changing Hierarchy of the organization. 

It takes into consideration the changes made in hierarchy and incorporates the changes in the hierarchy, enforcing below constraints :

1. In hierarchy always exist only one root
2. There are No loops or cyclic hierarchies




**Security : **

To invoke any endpoint, please pass Basic Authentication credentials:

username: admin
password: admin

We have used Basic Authentication using Spring Security.




**Endpoints : **

1. POST request on endpoint http://localhost:8080/updateHierarchy with below example body. (Please also refer to images)

{
    "Pete": "Nick",
    "Barbara": "Nick",
    "Nick": "Sophie",
    "Sophie": "Jonas"
}


2. Get supervisors for Nick


GET request on endpoint http://localhost:8080/getHierarchy/Nick


**Exceuting the project : **

1. To execute, please download the zip of the project or clone the repository.

2. Import the unziped file or cloned repo into IDE of your choice.

3. Run mvn clean install to build and resolve dependencies needed for the application.

4. After build is successful, you can use above endpoints with respective authorization credentials and request payload on Postman.



