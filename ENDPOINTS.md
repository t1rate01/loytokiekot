# API Documentation

## Add a New Disc
- **Endpoint:** `/api/discs`
- **Method:** `POST`
- **Body:**
  - `Authorization` (Header): Token for user authentication
  - `DiscDto` (Request Body): JSON object containing disc information

## Get All Discs
- **Endpoint:** `/api/discs`
- **Method:** `GET`
- **Body:** None

## Get Discs by Region
- **Endpoint:** `/api/discs/region/{region}`
- **Method:** `GET`
- **Body:** None

## Get Discs by City
- **Endpoint:** `/api/discs/city/{city}`
- **Method:** `GET`
- **Body:** None

## Get Disc by ID
- **Endpoint:** `/api/discs/{id}`
- **Method:** `GET`
- **Body:** None

## Get Discs by User
- **Endpoint:** `/api/discs/user/{username}`
- **Method:** `GET`
- **Body:** None

## Get Discs by Token
- **Endpoint:** `/api/discs/user`
- **Method:** `GET`
- **Body:**
  - `Authorization` (Header): Token for user authentication

## Update Disc
- **Endpoint:** `/api/discs/{id}`
- **Method:** `PATCH`
- **Body:**
  - `Authorization` (Header): Token for user authentication
  - `UpdateDiscDto` (Request Body): JSON object containing updated disc information

## Delete Disc
- **Endpoint:** `/api/discs/{id}`
- **Method:** `DELETE`
- **Body:**
  - `Authorization` (Header): Token for user authentication

## Get All User Keywords
- **Endpoint:** `/api/user/keywords`
- **Method:** `GET`
- **Body:**
  - `Authorization` (Header): Token for user authentication

## Add User Keyword
- **Endpoint:** `/api/user/keywords`
- **Method:** `POST`
- **Body:**
  - `Authorization` (Header): Token for user authentication
  - `UserKeyWordDto` (Request Body): JSON object containing the keyword to be added

## Delete User Keyword
- **Endpoint:** `/api/user/keywords/{id}`
- **Method:** `DELETE`
- **Body:**
  - `Authorization` (Header): Token for user authentication

## Update User Keyword
- **Endpoint:** `/api/user/keywords`
- **Method:** `PATCH`
- **Body:**
  - `Authorization` (Header): Token for user authentication
  - `UserKeyWordDto` (Request Body): JSON object containing updated keyword information

## User Registration
- **Endpoint:** `/api/register`
- **Method:** `POST`
- **Body:**
  - `RegisterDto` (Request Body): JSON object containing user registration information

## User Login
- **Endpoint:** `/api/login`
- **Method:** `POST`
- **Body:**
  - `Authorization` (Header): Basic Authentication with username and password

## Delete User
- **Endpoint:** `/api/user`
- **Method:** `DELETE`
- **Body:**
  - `Authorization` (Header): Token for user authentication

## Update User
- **Endpoint:** `/api/user`
- **Method:** `PATCH`
- **Body:**
  - `Authorization` (Header): Token for user authentication
  - `UpdateDto` (Request Body): JSON object containing updated user information
