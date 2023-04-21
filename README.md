# Job Data Repository

## REST Endpoint
### GET /job_data 
Request Parameter
- field - specify the fields to return. e.g., `/job_data/fields=title`
- condition - apply query condition. e.g., `/job_data/condition=salary%3E=10000000`
- sort - field to sort e.g., `/job_data/sort=salary`
- sort_type - Sort order. ASC (default) or DESC `/job_data/sort=salary&sort_type=DESC`

## Documentation (Swagger)
http://localhost:8080/swagger-ui/index.html
