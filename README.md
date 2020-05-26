# Challenge app

## Running the app

To run the application: `gradle bootRun`.

Open web browser at `http://localhost:8080/persons`.

###
 Performing requests


Test REST endpoint with: 

    `curl -X GET http://localhost:8080/persons/1`
    
    `curl -X POST http://localhost:8080/persons -d "@src/test/resources/person.json" -H "Content-Type: application/json"`
    
    `curl -X DELETE http://localhost:8080/persons/1`
    
    `curl -X GET http://localhost:8080/persons/1/friends`
    
Delete all friends by user id:
    `curl -X DELETE http://localhost:8080/persons/1/friends`
    
Delete a friend from user by friend id:
    `curl -X DELETE http://localhost:8080/persons/1/friends/2`
    
    
Add a new friend:
    `curl -X POST http://localhost:8080/persons/1/friends -d "@src/test/resources/person.json" -H "Content-Type: application/json"`

Or add existed user as friend:
    `curl -X PUT http://localhost:8080/persons/1/friends/2`

    


