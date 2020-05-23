# Challenge app

## Running the app

To run the application: `gradle bootRun`.

Open web browser at `http://localhost:8080/persons`.

## Performing requests


Test REST endpoint with: 

    `curl -X GET http://localhost:8080/persons/1`
    
    `curl -d "@src/test/resources/person.json" -H "Content-Type: application/json" -X POST http://localhost:8080/persons
`
