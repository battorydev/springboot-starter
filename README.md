# Job Data Repository

## Requirements
- Java 17

## REST Endpoint
### GET /job_data 
Returns all job data.

__Request Parameter__
- __field__ - specify the fields to return.      
    Filterable column: job title, salary, gender

- __condition__ - apply query condition. Filterable field and operation:
    - `title` operand: =
    - `salary` operand: =, >=, >, <=, <
    - `gender` operand: =
- __sort__ - field to sort. Sortable fields: title, salary
- __sort_type__ - Sort order. ASC (default) or DESC

## Sample URL request
- GET http://localhost:8080/job_data
- GET http://localhost:8080/job_data?fields=title,gender  
- GET http://localhost:8080/job_data?fields=title,salary&sort=salary  
- GET http://localhost:8080/job_data?fields=title,salary&sort=salary&sort_type=DESC
- GET http://localhost:8080/job_data?fields=title,gender,salary&sort=title&condition=salary%3E=10000000

## Documentation (Swagger)
http://localhost:8080/swagger-ui/index.html

## Test Cases
- JobDataControllerTests#givenTitleAndSalaryField_whenGetJobData_showTitleAndSalaryOnly
- JobDataControllerTests#givenConditionSalaryGraterThan_whenGetJobData_returnJobSalaryGreaterThan
- JobDataControllerTests#givenSortAndSortType_whenGetJobData_returnSortedData
