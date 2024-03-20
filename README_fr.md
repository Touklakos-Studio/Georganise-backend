# Documentation de l'API Georganise

Ce document fournit un bref aperçu de l'API Georganise, qui est une API REST construite avec Java et Spring Boot.

## Points d'accès

### Contrôleur Utilisateur

- `GET /api/user`: Renvoie une liste de tous les utilisateurs.
- `GET /api/user/{id}`: Renvoie un utilisateur par son userId.
- `GET /api/user/me`: Renvoie l'utilisateur actuellement authentifié.
- `POST /api/user`: Crée un nouvel utilisateur.
- `PUT /api/user/{id}`: Met à jour un utilisateur par son userId.
- `DELETE /api/user/{id}`: Supprime un utilisateur par son userId.
- `POST /api/user/login`: Authentifie un utilisateur et renvoie un jeton.
- `POST /api/user/logout`: Déconnecte l'utilisateur actuellement authentifié.

### Contrôleur de Jeton

- `GET /api/token`: Renvoie une liste de tous les jetons.
- `GET /api/token/{id}`: Renvoie un jeton par son tokenIg.
- `GET /api/token/user/{id}`: Renvoie une liste de jetons par le userId de l'utilisateur du jeton.
- `GET /api/token/creator/{id}`: Renvoie une liste de jetons par le userId du créateur du jeton.
- `GET /api/token/tag/{id}`: Renvoie une liste de jetons par le tagId de la balise du jeton.
- `POST /api/token/new`: Génère un nouveau jeton.
- `DELETE /api/token/{id}`: Supprime un jeton par son tokenId.
- `PUT /api/token/{id}`: Met à jour un jeton par son tokenId.
- `PATCH /api/token/{token}`: Ajoute un jeton par sa valeur à un utilisateur.

### Contrôleur de Balise

- `GET /api/tag`: Renvoie une liste de toutes les balises.
- `GET /api/tag/keyword/{keyword}`: Renvoie une liste de balises par mot-clé.
- `GET /api/tag/{id}`: Renvoie une balise par son tagId.
- `GET /api/tag/placeTag/{id}`: Renvoie une balise par placeTagId.
- `POST /api/tag`: Crée une nouvelle balise.
- `PUT /api/tag/{id}`: Met à jour une balise par son tagId.
- `DELETE /api/tag/{id}`: Supprime une balise par son tagId.
- `DELETE /api/tag/removeFrom/{id}`: Supprime un lieu d'une balise.

### Contrôleur de Lieu

- `GET /api/place`: Renvoie une liste de tous les lieux.
- `GET /api/place/user/{id}`: Renvoie une liste de lieux par le userId du créateur du lieu.
- `GET /api/place/tag/{id}`: Renvoie une liste de lieux par le tagId d'une balise.
- `GET /api/place/keyword/{keyword}`: Renvoie une liste de lieux par mot-clé.
- `GET /api/place/around`: Renvoie une liste de lieux par proximité.
- `GET /api/place/{id}`: Renvoie un lieu par son placeId.
- `GET /api/place/placeTag/{id}`: Renvoie un lieu par placeTagId.
- `POST /api/place`: Crée un nouveau lieu.
- `PUT /api/place/{id}`: Met à jour un lieu par son placeId.
- `DELETE /api/place/{id}`: Supprime un lieu par son placeId.
- `GET /api/place/realtime`: Renvoie le lieu en temps réel associé à l'utilisateur connecté.

### Contrôleur d'Image

- `GET /api/image`: Renvoie une liste de toutes les images.
- `GET /api/image/{id}`: Renvoie une image par son imageId.
- `GET /api/image/keyword/{keyword}`: Renvoie une liste d'images par mot-clé.
- `POST /api/image`: Crée une nouvelle image.
- `PUT /api/image/{id}`: Met à jour une image par son imageId.
- `DELETE /api/image/{id}`: Supprime une image par son imageId.

## Gestion des Erreurs

L'API Georganise utilise des codes d'état HTTP pour indiquer le succès ou l'échec d'une requête. En cas d'erreurs, les codes d'état suivants sont renvoyés :

- `418 I'm a teapot` : Ce code d'état est renvoyé lorsqu'une erreur inconnue se produit. Il s'agit d'un code d'état HTTP non standard utilisé pour indiquer que le serveur refuse de préparer du café car il s'agit d'une théière. Dans ce contexte, il est utilisé pour indiquer une erreur inattendue.

- `404 Not Found` : Ce code d'état est renvoyé lorsque l'entité demandée est introuvable.

- `400 Bad Request` : Ce code d'état est renvoyé lorsque la requête est mal formée ou que le serveur ne peut pas la comprendre. Il est également renvoyé lorsqu'un mot de passe incorrect est fourni.

- `501 Not Implemented` : Ce code d'état est renvoyé lorsque le serveur ne prend pas en charge la fonctionnalité requise pour répondre à la requête.

- `401 Unauthorized` : Ce code d'état est renvoyé lorsque la requête nécessite une authentification utilisateur. Il est renvoyé dans les cas où l'utilisateur n'est pas connecté ou non autorisé.

- `409 Conflict` : Ce code d'état est renvoyé lorsque la requête n'a pas pu être complétée en raison d'un conflit avec l'état actuel de la ressource.

Veuillez noter que ces codes d'état sont des codes d'état HTTP standard et leur utilisation dans cette API suit les conventions standard.

## Authentification

L'API utilise une authentification basée sur les cookies. Le cookie `authToken` doit être inclus dans chaque requête.

## Pagination

L'API ne prend actuellement pas en charge la pagination.

## Formats de Données

L'API accepte et renvoie des données au format JSON.