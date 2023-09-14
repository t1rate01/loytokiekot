## Disc Tracker API Documentation

### Registration and Authentication

#### Register a new user

- **Endpoint:** `/api/register`
- **Method:** POST
- **Body:**
  - `username` (string)
  - `password` (string)
  - `email` (string)
  - `phonenumber` (string)
  - `region` (string)
  - `city` (string)
  - `description` (string)

#### Login

- **Endpoint:** `/api/login`
- **Method:** POST
- **Header:**
  - `Authorization` (string) - Basic auth credentials
  

### User Operations

#### Update User

- **Endpoint:** `/api/user`
- **Method:** PATCH
- **Header:**
  - `Authorization` (string) - Token
- **Body:**
  - Refer to `UpdateDto` for fields

#### Delete User

- **Endpoint:** `/api/user`
- **Method:** DELETE
- **Header:**
  - `Authorization` (string) - Token


### Disc Operations

#### Add Disc

- **Endpoint:** `/api/discs`
- **Method:** POST
- **Header:**
  - `Authorization` (string) - Bearer token
- **Body:**
  - Refer to `DiscDto` for fields

#### Get All Discs

- **Endpoint:** `/api/discs`
- **Method:** GET

#### Get Disc by ID

- **Endpoint:** `/api/discs/{id}`
- **Method:** GET
- **Path Variables:**
  - `id` (Long) - ID of the disc

#### Get Discs by Region

- **Endpoint:** `/api/discs/region/{region}`
- **Method:** GET
- **Path Variables:**
  - `region` (string) - Region name

#### Get Discs by City

- **Endpoint:** `/api/discs/city/{city}`
- **Method:** GET
- **Path Variables:**
  - `city` (string) - City name

#### Get Discs by User (by username)

- **Endpoint:** `/api/discs/user/{username}`
- **Method:** GET
- **Path Variables:**
  - `username` (string) - Username

#### Get Discs by User (by token)

- **Endpoint:** `/api/discs/user`
- **Method:** GET
- **Header:**
  - `Authorization` (string) - Token

#### Update Disc

- **Endpoint:** `/api/discs/{id}`
- **Method:** PATCH
- **Header:**
  - `Authorization` (string) - Token
- **Path Variables:**
  - `id` (Long) - ID of the disc
- **Body:**
  - Refer to `UpdateDiscDto` for fields

#### Delete Disc

- **Endpoint:** `/api/discs/{id}`
- **Method:** DELETE
- **Header:**
  - `Authorization` (string) - Token
- **Path Variables:**
  - `id` (Long) - ID of the disc


### User Keywords Operations

#### Get All User Keywords

- **Endpoint:** `/api/user/keywords`
- **Method:** GET
- **Header:**
  - `Authorization` (string) - Bearer token

#### Add User Keyword

- **Endpoint:** `/api/user/keywords`
- **Method:** POST
- **Header:**
  - `Authorization` (string) - Bearer token
- **Body:**
  - Refer to `UserKeyWordDto` for fields

#### Update User Keyword

- **Endpoint:** `/api/user/keywords`
- **Method:** PATCH
- **Header:**
  - `Authorization` (string) - Bearer token
- **Body:**
  - Refer to `UserKeyWordDto` for fields

#### Delete User Keyword

- **Endpoint:** `/api/user/keywords/{id}`
- **Method:** DELETE
- **Header:**
  - `Authorization` (string) - Bearer token
- **Path Variables:**
  - `id` (Long) - ID of the user keyword

