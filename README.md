
## Springboot Demo

This is a sample application aiming to demonstrate multilayer interactions in Java programming. It maintains a list of dummy messages subsequent to every external request. Each message is categorized by a unique ID as a reference for users to retrieve one or multiple messages.

### System Architecture
![system_diagram.jpg](system_diagram.jpg)

### Prerequisites
- JDK 1.8
- Maven 3.6.x or above

### Build and Run

- #### Build Project
```
mvn clean install
```

- #### Build Project and skip tests
```
mvn clean install -Dmaven.test.skip
```

- #### Start Application
```
mvn spring-boot:run
```

- #### Run Unit Test
```
mvn test
```

### API Endpoints
**Exposed port** `8085`

- **GET** `/msg`

  Returns a full list of messages

  **Sample Response**
  ```json
  [
      {
          "id": 0,
          "data": [
              "object0",
              "object1",
              "object2"
          ],
          "info": "sample0"
      },
      {
          "id": 1,
          "data": [
              "object3",
              "object4"
          ],
          "info": "sample1"
      }
  ]
  ```
  **Response Code**
  - 200 OK
  - 500 Internal Server Error
  

- **GET** `/msg/:id`

  Fetches a single message by ID

  **Sample Response**
  ```json
  {
      "id": 0,
      "data": [
          "object0",
          "object1",
          "object2"
      ],
      "info": "sample0"
  }
  ```
  **Response Code**
  - 200 OK
  - 400 Bad Request
  - 500 Internal Server Error


- **POST** `/msg`
  
  Creates a new message

  **Sample Request**
  ```json
  {
      "id": 0,
      "data": [
          "object0",
          "object1",
          "object2"
      ],
      "info": "sample0"
  }
  ```

  **Response Code**
  - 201 Created
  - 500 Internal Server Error


- **PUT** `/msg/:id`

  Updates provided fields of an existing message

  **Sample Request**
  ```json
  {
      "id": 0,
      "data": [
          "object0",
          "object1",
          "object2",
          "object5"
      ],
      "info": "sample0-modified"
  }
  ```
  **Response Code**
  - 200 OK
  - 400 Bad Request
  - 500 Internal Server Error


- **DELETE** `/msg/:id`

  Delete an existing message

  **Response Code**
  - 200 OK
  - 400 Bad Request
  - 500 Internal Server Error


- **DELETE** `/msg`

  Clears all messages

  **Response Code**
  - 200 OK
  - 500 Internal Server Error




