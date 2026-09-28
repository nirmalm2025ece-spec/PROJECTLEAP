# DonateHub - Old Clothes and Book Donation Drive Tracker

## 1. Objective

Campus donation drives collect old clothes and books from students, but there is no proper record of
what was collected and where it was given. DonateHub is a backend REST API (no frontend) that:

1. creates donation drives
2. records donors
3. records donated clothes / books
4. records recipient organizations
5. records distribution of an item to a recipient
6. shows collected, distributed and remaining quantity for every drive
7. lists the items that are still in stock (not distributed)

## 2. Technologies Used

- Java 17
- Spring Boot 3.2.5 (Spring Web, Spring Data JPA, Bean Validation)
- MySQL and MySQL Connector/J
- Maven
- IntelliJ IDEA, Postman (for testing)

## 3. Project Structure

```
src/main/java/com/example/donatehub
    controller   - REST endpoints (DriveController, DonorController, DonatedItemController, ...)
    service      - business rules are written here
    repository   - Spring Data JPA interfaces
    entity       - Drive, Donor, DonatedItem, Recipient, Distribution + enums Category, Condition
    dto          - request / response classes
    exception    - custom exceptions and GlobalExceptionHandler
```

Why a fifth entity (`Distribution`)? The item only stores `distributed = true/false`. To know
*which recipient* got the item and *on which date*, a separate table is needed.

Table relationships:

```
Drive 1 ---- * DonatedItem * ---- 1 Donor
Recipient 1 ---- * Distribution 1 ---- 1 DonatedItem
```

## 4. Database Setup

1. Install MySQL and make sure it is running on port 3306.
2. Open MySQL Workbench / MySQL command line and run:

```sql
CREATE DATABASE donatehub;
```

3. Tables are created automatically by JPA (`spring.jpa.hibernate.ddl-auto=update`), so no
   CREATE TABLE queries are needed.
4. Open `src/main/resources/application.properties` and **change the password**:

```properties
spring.datasource.password=YOUR_PASSWORD
```

Replace `YOUR_PASSWORD` with your own MySQL password. The username is `root` - change it too if yours is different.

## 5. How to Run in IntelliJ IDEA

1. Extract the zip.
2. In IntelliJ: File > Open > select the `donatehub` folder (the one containing `pom.xml`).
3. Wait for Maven to download the dependencies (bottom right progress bar).
4. Check File > Project Structure > Project SDK is Java 17 or higher.
5. Open `DonateHubApplication.java` and click the green Run button next to `main`.
6. When the console shows `Started DonateHubApplication`, the API is running at `http://localhost:8080`.

## 6. API Endpoints

| Method | URL | Description | Success |
|--------|-----|-------------|---------|
| POST | /api/drives | Create drive | 201 |
| GET | /api/drives | Get all drives | 200 |
| GET | /api/drives/{id} | Get drive by id | 200 |
| PUT | /api/drives/{id} | Update drive | 200 |
| DELETE | /api/drives/{id} | Delete drive (only if it has no items) | 204 |
| GET | /api/drives/{id}/summary | Collected / distributed / remaining totals | 200 |
| POST | /api/donors | Create donor | 201 |
| GET | /api/donors | Get all donors | 200 |
| GET | /api/donors/{id} | Get donor by id | 200 |
| POST | /api/recipients | Create recipient | 201 |
| GET | /api/recipients | Get all recipients | 200 |
| GET | /api/recipients/{id} | Get recipient by id | 200 |
| POST | /api/items | Add donated item | 201 |
| GET | /api/items | Get all items | 200 |
| GET | /api/items/{id} | Get item by id | 200 |
| GET | /api/items/undistributed | Items not yet distributed | 200 |
| POST | /api/distributions | Distribute an item to a recipient | 201 |
| GET | /api/distributions | Get all distributions | 200 |

## 7. Testing in Postman

For every POST/PUT request set the header `Content-Type: application/json` and choose Body > raw > JSON.
Run the requests in this order (ids assume an empty database).

### Step 1 - Create donor
`POST http://localhost:8080/api/donors`
```json
{
  "name": "Nirmal",
  "phone": "9876543210",
  "email": "nirmal@example.com"
}
```
Response `201`:
```json
{ "id": 1, "name": "Nirmal", "phone": "9876543210", "email": "nirmal@example.com" }
```

### Step 2 - Create drive
`POST http://localhost:8080/api/drives`
```json
{
  "driveName": "Campus Clothes and Books Drive",
  "description": "Annual donation drive conducted by ECE department",
  "startDate": "2026-09-01",
  "endDate": "2026-09-15"
}
```

### Step 3 - Create recipient
`POST http://localhost:8080/api/recipients`
```json
{
  "organizationName": "Hope Foundation",
  "contactPerson": "Ramesh",
  "phone": "9876543210",
  "address": "Coimbatore"
}
```

### Step 4 - Add donated items
`POST http://localhost:8080/api/items`
```json
{
  "category": "CLOTHES",
  "condition": "GOOD",
  "quantity": 5,
  "donatedDate": "2026-09-10",
  "driveId": 1,
  "donorId": 1
}
```
Response `201` - note `distributed` is `false` automatically:
```json
{
  "id": 1,
  "category": "CLOTHES",
  "condition": "GOOD",
  "quantity": 5,
  "donatedDate": "2026-09-10",
  "distributed": false,
  "driveId": 1,
  "driveName": "Campus Clothes and Books Drive",
  "donorId": 1,
  "donorName": "Nirmal"
}
```
Add a second item so the summary is more interesting:
```json
{
  "category": "BOOKS",
  "condition": "NEW",
  "quantity": 10,
  "donatedDate": "2026-09-11",
  "driveId": 1,
  "donorId": 1
}
```

### Step 5 - View items
`GET http://localhost:8080/api/items` - returns both items.

### Step 6 - View undistributed items
`GET http://localhost:8080/api/items/undistributed` - returns items 1 and 2 (both have `distributed: false`).

### Step 7 - Distribute item 1
`POST http://localhost:8080/api/distributions`
```json
{
  "itemId": 1,
  "recipientId": 1,
  "distributedDate": "2026-09-12"
}
```
Response `201`:
```json
{
  "id": 1,
  "itemId": 1,
  "recipientId": 1,
  "recipientName": "Hope Foundation",
  "distributedDate": "2026-09-12"
}
```
Now call `GET /api/items/undistributed` again - only item 2 is left.

### Step 8 - Drive summary
`GET http://localhost:8080/api/drives/1/summary`
```json
{
  "driveId": 1,
  "driveName": "Campus Clothes and Books Drive",
  "totalItemsCollected": 15,
  "totalItemsDistributed": 5,
  "totalItemsRemaining": 10
}
```
The totals use the **quantity** (5 + 10 = 15), not the number of rows (2).

### Step 9 - Distribute the same item again
Send the same request from Step 7 once more.

### Step 10 - Expected error
Status `400 Bad Request`:
```json
{
  "message": "This item has already been distributed",
  "status": 400
}
```

## 8. Business Rules

All of these are checked in the service layer (not only by database constraints).

1. An item can be distributed only if it exists as a donated item (else "Donated item not found").
2. The recipient must exist too.
3. An item that is already distributed cannot be distributed again. This is checked before anything is saved.
4. The distribution date cannot be before the item's donated date.
5. A new donated item is always saved with `distributed = false`; the client cannot set it.
6. Category can only be `CLOTHES` or `BOOKS`.
7. Condition can only be `NEW`, `GOOD` or `WORN`.
8. Quantity must be greater than zero.
9. Drive start date and end date are required and the end date cannot be before the start date.
10. A drive that already has donated items cannot be deleted.

A donated item record is treated as one batch, e.g. "5 clothes". The whole batch is distributed to one recipient at once.
Distributing part of a batch is not supported.

## 9. Expected Error Cases

Errors are always returned as simple JSON: `{ "message": "...", "status": 400 }`.

| Case | Request | Status | Message |
|------|---------|--------|---------|
| Item id does not exist | POST /api/distributions with `"itemId": 999` | 404 | Donated item not found |
| Recipient id does not exist | POST /api/distributions with `"recipientId": 999` | 404 | Recipient not found with id 999 |
| Item already distributed | Same distribution twice | 400 | This item has already been distributed |
| Quantity is 0 | POST /api/items with `"quantity": 0` | 400 | quantity: Quantity must be greater than zero |
| Invalid condition | POST /api/items with `"condition": "DAMAGED"` | 400 | Invalid value 'DAMAGED' for condition. Allowed values: [NEW, GOOD, WORN] |
| Invalid category | POST /api/items with `"category": "TOYS"` | 400 | Invalid value 'TOYS' for category. Allowed values: [CLOTHES, BOOKS] |
| End date before start date | POST /api/drives with start `2026-09-15`, end `2026-09-01` | 400 | End date cannot be before start date |
| Missing required field | POST /api/donors without `name` | 400 | name: Name is required |
| Drive / donor id does not exist | GET /api/drives/999 | 404 | Drive not found with id 999 |

### Example - invalid condition
`POST /api/items`
```json
{
  "category": "CLOTHES",
  "condition": "DAMAGED",
  "quantity": 3,
  "donatedDate": "2026-09-10",
  "driveId": 1,
  "donorId": 1
}
```
Response `400`:
```json
{
  "message": "Invalid value 'DAMAGED' for condition. Allowed values: [NEW, GOOD, WORN]",
  "status": 400
}
```

### Example - distribute an item that does not exist
`POST /api/distributions`
```json
{
  "itemId": 999,
  "recipientId": 1,
  "distributedDate": "2026-09-12"
}
```
Response `404`:
```json
{
  "message": "Donated item not found",
  "status": 404
}
```

## 10. Possible Problems

- `Access denied for user 'root'` - the password in `application.properties` is wrong.
- `Unknown database 'donatehub'` - run `CREATE DATABASE donatehub;` first.
- `Communications link failure` - MySQL is not running.
- Port 8080 already in use - change `server.port` in `application.properties`.
