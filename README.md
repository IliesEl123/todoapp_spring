# ToDoApp - Partie Spring

Ce projet contient la partie backend de l'application de gestion de tâches. Il est développé en utilisant le framework Spring.

## Installation

1. Cloner le dépôt Git sur votre machine locale.
2. Assurez-vous que vous avez une instance de MySQL en cours d'exécution.
3. Configurez les informations de connexion à votre base de données dans le fichier application.properties.
4. Compilez et exécutez l'application en utilisant votre IDE ou en utilisant la commande mvn spring-boot:run dans le terminal.

## API REST

Ce projet expose une API REST pour gérer les tâches. Voici la liste des endpoints disponibles :

- GET /tasks : récupère toutes les tâches.
- GET /tasks/{id} : récupère une tâche spécifique.
- POST /tasks : crée une nouvelle tâche.
- PUT /tasks/{id} : met à jour une tâche existante.
- DELETE /tasks/{id} : supprime une tâche spécifique.

## Technologies utilisées
- Spring Boot
- Spring Data JPA
- MySQL
- Maven
