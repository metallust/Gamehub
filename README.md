## Getting Started - Java Mini Project

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

-   `src`: the folder to maintain sources
-   `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).

## jdbc Connection Setup

-   Database userdatabase
-   Table users
-   column are name, username, password, score1, score2, score3 

-   CREATE database userdatabase;
-   use userdatabase;
-   CREATE TABLE users (
    name VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    score1 INT DEFAULT 100,
    score2 INT DEFAULT 0,
    score3 INT DEFAULT 0
    );

## make password for database game
