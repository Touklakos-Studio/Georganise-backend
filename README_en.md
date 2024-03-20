# Georganise API Documentation

This document provides a brief overview of the Georganise API, which is a REST API built with Java and Spring Boot.

## Endpoints

### User Controller

- `GET /api/user`: Returns a list of all users.
- `GET /api/user/{id}`: Returns a user by its userId.
- `GET /api/user/me`: Returns the currently authenticated user.
- `POST /api/user`: Creates a new user.
- `PUT /api/user/{id}`: Updates a user by its userId.
- `DELETE /api/user/{id}`: Deletes a user by its userId.
- `POST /api/user/login`: Authenticates a user and returns a token.
- `POST /api/user/logout`: Logs out the currently authenticated user.

### Token Controller

- `GET /api/token`: Returns a list of all tokens.
- `GET /api/token/{id}`: Returns a token by its tokenId.
- `GET /api/token/user/{id}`: Returns a list of tokens by the userId of the token's user.
- `GET /api/token/creator/{id}`: Returns a list of tokens by the userId of the token's creator.
- `GET /api/token/tag/{id}`: Returns a list of tokens by the tagId of the token's tag.
- `POST /api/token/new`: Generates a new token.
- `DELETE /api/token/{id}`: Deletes a token by its tokenId.
- `PUT /api/token/{id}`: Updates a token by its tokenId.
- `PATCH /api/token/{token}`: Adds a token by its value to a user.

### Tag Controller

- `GET /api/tag`: Returns a list of all tags.
- `GET /api/tag/keyword/{keyword}`: Returns a list of tags by keyword.
- `GET /api/tag/{id}`: Returns a tag by its tagId.
- `GET /api/tag/placeTag/{id}`: Returns a tag by placeTagId.
- `POST /api/tag`: Creates a new tag.
- `PUT /api/tag/{id}`: Updates a tag by its tagId.
- `DELETE /api/tag/{id}`: Deletes a tag by its tagId.
- `DELETE /api/tag/removeFrom/{id}`: Removes a place from a tag.

### Place Controller

- `GET /api/place`: Returns a list of all places.
- `GET /api/place/user/{id}`: Returns a list of places by the userId of the place's creator.
- `GET /api/place/tag/{id}`: Returns a list of places by the tagId of a tag.
- `GET /api/place/keyword/{keyword}`: Returns a list of places by keyword.
- `GET /api/place/around`: Returns a list of places by proximity.
- `GET /api/place/{id}`: Returns a place by its placeId.
- `GET /api/place/placeTag/{id}`: Returns a place by placeTagId.
- `POST /api/place`: Creates a new place.
- `PUT /api/place/{id}`: Updates a place by its placeId.
- `DELETE /api/place/{id}`: Deletes a place by its placeId.
- `GET /api/place/realtime`: Returns real-time places associated with the logged-in user.

### Image Controller

- `GET /api/image`: Returns a list of all images.
- `GET /api/image/{id}`: Returns an image by its imageId.
- `GET /api/image/keyword/{keyword}`: Returns a list of images by keyword.
- `POST /api/image`: Creates a new image.
- `PUT /api/image/{id}`: Updates an image by its imageId.
- `DELETE /api/image/{id}`: Deletes an image by its imageId.

## Error Handling

The Georganise API uses HTTP status codes to indicate the success or failure of a request. In case of errors, the following status codes are returned:

- `418 I'm a teapot`: This status code is returned when an unknown error occurs. This is a non-standard HTTP status code used to indicate that the server refuses to brew coffee because it is a teapot. In this context, it's used to indicate an unexpected error.

- `404 Not Found`: This status code is returned when the requested entity is not found.

- `400 Bad Request`: This status code is returned when the request is malformed or the server cannot understand it. This is also returned when an incorrect password is provided.

- `501 Not Implemented`: This status code is returned when the server does not support the functionality required to fulfill the request.

- `401 Unauthorized`: This status code is returned when the request requires user authentication. This is returned in cases where the user is not logged in or unauthorized.

- `409 Conflict`: This status code is returned when the request could not be completed due to a conflict with the current state of the resource.

Please note that these status codes are standard HTTP status codes and their usage in this API follows the standard conventions.

## Authentication

The API uses cookie-based authentication. The `authToken` cookie must be included with each request.

## Pagination

The API currently does not support pagination.

## Data Formats

The API accepts and returns data in JSON format.