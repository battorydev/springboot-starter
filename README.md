# Job Data Repository

## Requirements
- Java 17

## REST Endpoint
### GET /job_data 
Returns all job data.

__Request Parameter__
- field - specify the fields to return. e.g., `/job_data/fields=title`
- condition - apply query condition. e.g., `/job_data/condition=salary%3E=10000000`
- sort - field to sort e.g., `/job_data/sort=salary`
- sort_type - Sort order. ASC (default) or DESC `/job_data/sort=salary&sort_type=DESC`

__Sample URL request__
GET http://localhost:8080/job_data?fields=title,gender&sort=title&condition=gender=Male

## Documentation (Swagger)
http://localhost:8080/swagger-ui/index.html

## Test Cases
- JobDataControllerTests#givenTitleAndSalaryField_whenGetJobData_showTitleAndSalaryOnly
- JobDataControllerTests#givenConditionSalaryGraterThan_whenGetJobData_returnJobSalaryGreaterThan
- JobDataControllerTests#givenSortAndSortType_whenGetJobData_returnSortedData
