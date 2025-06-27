
# Task Manager Application

## Overview

A Java-based Task Manager that allows users to:

- Create unique user accounts  
- Manage each user's task list  
- Add tasks with descriptions, due dates, and priorities  
- Mark tasks as completed  
- Delete tasks  
- View tasks through a Command Line Interface (CLI) or a Graphical User Interface (GUI)  


## How to Run

Run the following in the project root:

```bash
mvn compile exec:java
```

Follow the on-screen prompts to interact with the CLI or GUI version.

## Technologies Used

- Java 17  
- Maven for build and project management  
- Gson for JSON storage  
- Swing for GUI  
- JUnit 5 for automated testing  

## Notes

- All user data is saved to `data.json` in the project root  
- Maximum of 100 users supported   
- GUI features user-friendly layout, and dark mode toggle  
- Priority tasks are color coded (Red for High, Orange for Medium)  
