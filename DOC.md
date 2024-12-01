

# Application Setup and Usage Guide

## Prerequisites

- Java 17
- Gradle
- Docker

---

## Setup Instructions

1. Start the PostgreSQL Database

   Navigate to the project's root directory and run:

   ```bash
   docker compose up
   ```

2. Build and Run the Application

   Use Gradle to build and start the application:

   ```bash
   ./gradlew bootRun
   ```

   The application will start on:  
   http://localhost:8081

---

## API Endpoints

### 1) Get All Persons

Endpoint:  
`http://localhost:8081/api/person`  

Method:  
`GET`

---

### 2) Create a Person

Endpoint:  
`http://localhost:8081/api/person`  

Method:  
`POST`

#### Sample Body for Customer:
```json
{
    "name": "salmancustomer1",
    "email": "customer1@gmail.com",
    "registrationNumber": "DDABCD1234",
    "role": "CUSTOMER"
}
```

#### Sample Body for Delivery Man:
```json
{
    "name": "salmandeliveryman1",
    "email": "deliveryman1@gmail.com",
    "registrationNumber": "DDABCD1234",
    "role": "DELIVERY_MAN"
}
```

Notes:  
- The `role` field differentiates between `CUSTOMER` and `DELIVERY_MAN`.  
- The `email` field must be unique.

---

### 3) Create a Delivery

Endpoint:  
`http://localhost:8081/api/delivery`  

Method:  
`POST`

#### Sample Body:
```json
{
  "startTime": "2024-12-01T09:15:30Z",
  "endTime": "2024-12-01T10:15:30Z",
  "distance": "980",
  "price": "3930",
  "deliveryMan": {
      "id": "103"
  },
  "customer": {
      "id": "104"
  }
}
```

Note: Ensure `deliveryMan` and `customer` IDs exist in the database; otherwise, the delivery will not be created.

---

### 4) Fetch Delivery by ID

Endpoint:  
`http://localhost:8081/api/delivery/{id}`  

Method:  
`GET`

Replace `{id}` with an existing delivery ID.

---

### 5) Prevent Same Delivery Man from Handling Multiple Orders Simultaneously

If a delivery is in progress (i.e., `endTime` is `null`), creating another delivery with the same delivery man will throw the error:  
"Delivery man is already assigned to an ongoing delivery."

#### Example:

First Request:  
```json
{
  "startTime": "2024-12-01T09:15:30Z",
  "endTime": null,
  "distance": "980",
  "price": "3930",
  "deliveryMan": {
      "id": "103"
  },
  "customer": {
      "id": "104"
  }
}
```

Second Request (with the same deliveryMan ID):  
```json
{
  "startTime": "2024-12-01T10:30:00Z",
  "endTime": null,
  "distance": "1200",
  "price": "4500",
  "deliveryMan": {
      "id": "103"
  },
  "customer": {
      "id": "105"
  }
}
```

This second request will fail since the delivery man is already assigned to an ongoing delivery.

---

### 6) Fetch Top 3 Delivery Men by Commission (with Average Commission)

Endpoint:  
`http://localhost:8081/api/delivery/top-delivery-men`  

Parameters:  
- `startTime`: Start of the time range (e.g., `2024-10-01T00:00:00Z`)  
- `endTime`: End of the time range (e.g., `2025-11-30T23:59:59Z`)  

Method:  
`GET`

Response:  
Returns the top 3 delivery men with their total commissions and the average commission of all deliveries.  

Commission Formula:  
`Commission = (Order Price * 0.05) + (Distance * 0.5)`

---

### 7) Notify Customer Support for Delayed Deliveries

If a delivery exceeds 45 minutes and has no `endTime` (i.e., it is still ongoing), a notification is sent to Customer Support.

How It Works:  
- The system checks for delayed deliveries every 30 seconds.  
- If the `startTime` is more than 45 minutes in the past and the `endTime` is still `null`, a notification is sent.  

Check Logs:  
Server logs will show notifications sent to the support team.

---

## Optimization Ideas for Notifications

1. Run Only During Peak Hours:  
   Reduce server overhead by scheduling the notification checks only during peak hours.

2. Delivery-Specific Scheduling:  
   Instead of checking all deliveries every 30 seconds, schedule a task for 45 minutes after each delivery starts.  
   This avoids redundant checks and optimizes server performance by only processing relevant jobs.
