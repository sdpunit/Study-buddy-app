# [G48] Report

## Table of Contents

1. [Team Members and Roles](#team-members-and-roles)
2. [Summary of Individual Contributions](#summary-of-individual-contributions)
3. [Conflict Resolution Protocol](#conflict-resolution-protocol)
4. [Application Description](#application-description)
5. [Application UML](#application-uml)
6. [Application Design and Decisions](#application-design-and-decisions)
7. [Summary of Known Errors and Bugs](#summary-of-known-errors-and-bugs)
8. [Testing Summary](#testing-summary)
9. [Implemented Features](#implemented-features)
10. [Team Meetings](#team-meetings)

## Team Members and Roles

| UID        |           Name           |                                                           Role |
|:-----------|:------------------------:|---------------------------------------------------------------:|
| [u7490701] |      [Ahmed Qaisar]      |                                      [Firebase,Data-Graphical] |
| [u7103031] |      [Lana Fraser]       |                                     [Login, Search and Report] |
| [u7432723] |     [Punit Deshwal]      | [Database Structures, UI Themes, Firebase, Initialising Users] |
| [u7108792] |      [Quoc Nguyen]       |                                 [Search, Tokenizer and Parser] |
| [u7533843] |      [Yanghe Dong]       |     [Tree Structure, Timer, Notifications, Creating User Data] |

## Summary of Individual Contributions

*u7490701, Ahmed, I contribute 20% of the code. Here are my contributions:*
* Firebase   https://console.firebase.google.com/u/0/project/comp2100-group-assignmen-e7d8d/overview

*Report Writing: N/A*

*Slide Preparation: N/A*
<br><br>

*u7103031, Lana (u7103031) , I contribute 20% of the code. Here are my contributions:*
* Login.java: onCreate(), authenticateUser(), showLoginMessage()   
* loginDetails.csv   
* activity_login.xml   
* activity_register.xml   

*UI Design: Proposed and designed the UI for the Login and Register class*

*Report Writing: N/A*

*Slide Preparation: N/A*
<br><br>

*u7432723, Punit, I contribute 20% of the code. Here are my contributions:*
* AssessmentsActivity.java  
* Login.java: authenticateUser()   
* MainActivity.java: 
* Register.java   
* StudyActivity.java: onCreate()
* User.java  
* UserTimeState.java   
* activity_main.xml   
* item_course.xml  
* activity_assessments.xml 

*UI Design: Proposed and designed the UI for the Main class. Created a new colour theme for the app. Developed many of the conceptual ideas for the designs of each UI screen and how they interact together*

*Report Writing: N/A*

*Slide Preparation: N/A*
<br><br>

*u7108792, Quoc, I contribute 20% of the code. Here are my contributions:*
* Query.java   
* SearchActivity.java  
* SearchParser.java  
* Token.java  
* Tokenizer.java  
* UserAdapter.java
* activity_search.xml 
* user_cell.xml  

*UI Design: Proposed and designed the UI for the SearchActivity class*

*Report Writing: N/A*

*Slide Preparation: N/A*
<br><br>

*u7432723, Yanghe, I contribute 20% of the code. Here are my contributions:*
* Course   
* idleState.java   
* Login.java: createNotificationChannel()   
* MainActivity.java: sendNotification()  
* myTimer.java  
* NotificationFactory.java  
* pauseState.java   
* RBTree.java   
* SetTimeActivity.java  
* State.java   
* StudyActivity.java: onCreate(), clickPauseOrResume(), clickStop(), timeUp() 
* StudyCourseNotification.java 
* StudyNotification.java   
* StudyNumberNotification.java   
* studyState.java   
* StudyTimeNotification   
* User.java   
* post_courses_data.json   
* under_courses_data.json   
* activity_set_time.xml   
* activity_study.xml   

*Code Design: Proposed and implemented a tree structure for storing our data. Proposed and implemented a state design pattern for the timer functionality.*

*UI Design: Proposed and designed the UI for the myTimer class.*

*Report Writing: N/A*

*Slide Preparation: N/A*


## Conflict Resolution Protocol

*Disputes in contributions will be resolved by a team meeting and voting.*


## Application Description

*StudyBuddy is a productivity and educational app that allows its users to keep track of the time that they have spent studying for each subject that they are enrolled in. There is a leaderboard in the app which displays the top 5 Users in the app, letting the User see how the are preforming comparatively and motivate them to study. This app includes a login interface, the ability to search for and enroll in courses, a timer functionality, and a graphics summary / leaderboard page.*

**Application Use Cases and or Examples**

*Targets Users: Students*

* *Users can search and enroll in courses. The app will provide a brief overview of the course and its assessments.*
* *Once enrolled users can select one of their courses and set a timer for the time they would like to study*
* *A user can view a summary of their total time studied.*

![UseCaseDiagram](./images/use_case_diagram.png) <br>


## Application UML
![UMLDiagram](./images/UML.png) <br>

## Application Design and Decisions

**Data Structures**

1. *RedBlack Tree*

   * *Objective: It is used for storing Courses with the course code as a key which can then be found using the Search feature.*

   * *Locations: RBTree.java*

   * *Reasons:*

     * *This tree is self-balancing, meaning that our data will be organised and structured even after multiple insertions*
     
     * *Has an O(log n) time intricacy for searching*

     * *We don't need to access the item by index for this feature*

2. *Map*

   * *Objective: A map is used to link the course and the amount of time studied.*

   * *Locations: User.java*

   * *Reasons:*

     * *Allows for quick data retrieval*
     
     * *Flexible key pairing which directly links two attributes to each other*

     * *The nature of this data structure mean there will be no duplicate keys*
     
     * *Maps are scalable and can handle large amounts of data*

3. *Set*

   * *Objective: A set is used to store a list of courses that a user is enrolled in*

   * *Locations: User.java*

   * *Reasons:*

     * *Eliminates duplicate elements*

     * *Searching operation takes O(logN) time complexity*

     * *Is dynamic and will not overflow, compared to other similar stat structure such as an array.*

**Design Patterns**

1. *State Design Pattern*

   * *Objective: This design pattern uses the startStudy, pause, resume, and stopStudy states to keep track of the current state of the timer implemented in the myTimer class.*

   * *Locations: State.java, StudyActivity.java*

   * *Reasons:*

     * *This will affect the functionality of the pause, resume and stop buttons according to the current state*
     
     * *The design is flexible enough to add or remove states if needed*

2. *Factory Design Pattern*

   * *Objective: The Factory design pattern provides an interface for creating new notification objects in a superclass, and allows for these superclasses to alter the type of object that will be created. This will allow us to notify users after certain actions have been done by a user or their friends.*

   * *Locations: StudyCourseNotification.java, StudyNotification.java, StudyNumberNotification.java, StudyTimeNotification.java, NotificationFactory.java*

   * *Reasons:*

     * *Factory design pattern is flexible and extendable and allows us to add and remove classes without making major changes to the app.*
     
     * *The design is flexible enough to add or remove new notifications or observers if needed.*

     * *Uses loose coupling and eliminates hard binding.*

3. *Singleton Design Pattern*

   * *Objective: The *

   * *Locations:*

   * *Reasons:*

     * **
     
     * **


**Grammar(s)**

Production Rules:

        <exp>       ::= "college:" <college> | "college:" <college> "," <term>
        <term>      ::= <factor> | <factor> "," <factor> | <factor> "," <factor> "," <factor>
        <factor>    ::= <code> | <name> | <convener>
        <code>      ::= "code:" four-digit Integer
        <name>      ::= "name:" String
        <convener>  ::= "convener:" String
        <college>   ::= "COMP" | "MATH" | "PHYS" | "STATS" | ...
*Example: "college: COMP, code: 1110, name: Structured Programming, convener: Patrik Haslum"*

*The grammar classifies the convener, name and code of the course as factors, and allows these to be separated by commas. The college a course belongs to is classified as an exp, and must be prepended by the string 'college:', and optionally appended by a series of factors seperated by commas. This grammar design is flexible as it allows for the user to search simply by the college, or with more detail by adding the code, name, and/or convener in any order. Our grammar contains code, name, convener, and college as these are the most relevant keywords associated with a course.*

**Tokenizer and Parsers**

*[We utilise tokenizers and parsers in our Search implementation. Once a user searches a term, this String is tokenized into `COLLEGE`, `CODE`, `COURSE`, and `CONVENER` tokens. These tokens are then parsed to determine if they follow the structure of the grammar that we have defined for our app. If the search terms follows the correct grammar, these tokens will be converted to Strings and passed into a Query object, to complete the search process. 
By using tokenizer and parsers, we are able to ensure that the search terms are valid, and that the search process is completed correctly. This standardizes the search process, and ensures that the user is able to search for courses in a consistent manner.]*

**Surprise Item**

*[TODO If you implement the surprise item, explain how your solution addresses the surprise task. What decisions do your team make in addressing the problem?]*

**Other**

*[TODO What other design decisions have you made which you feel are relevant? Feel free to separate these into their own subheadings.]*

## Summary of Known Errors and Bugs

1. *Bug 1:*

- TODO

*List all the known errors and bugs here. If we find bugs/errors that your team does not know of, it shows that your testing is not thorough.*

## Testing Summary

*[What features have you tested? What is your testing coverage?]*

*Here is an example:TODO*

- *Number of test cases: ...*

- *Code coverage: ...*

- *Types of tests created: ...*

*Please provide some screenshots of your testing summary, showing the achieved testing coverage. Feel free to provide further details on your tests.*

## Implemented Features

### Basic App
1. [Login]. Users must be able to log in (easy)
    * Class: Login.java
    * Users are able to register, which will add them as a user to Firebase. Users that are contained in Firebase will be able to log in using their username and password, which will be validated by the app before authenticating the user. 
      <br>
2. [Data Instances]. There must be 2500 data instances on Firebase (easy)
    * Class: xx.java, methods Z, Y, Lines of code: xx
    * User information will be uploaded to Firebase once the app has launched.
    * not fully implemented 
      <br>
3. [Firebase Data Visualization]. The user must be able to load data/information from Firebase and visualise it (medium)
    * Class: xx.java, methods Z, Y, Lines of code: xx
    * A list of courses that a user is enrolled in, a list of friends, and a leaderboard ranking can be displayed using the user information stored on Firebase. 
    * not fully implemented 
      <br>
4. [Search]. Users must be able to search for information on the app. (medium)
    * Classes: SearchActivity.java, SearchParser.java, SearchActivity.java, Tokenizer.java, Token.java
    * A user is able to search for a course by using keywords related to the subject, code, course, and/or convener.
    * not fully implemented 
      <br>
<br><br>

### General Features
Feature Category: User Activity <br>
1. [Interact Follow]. The ability to ‘follow’ a course or any specific items. There must be a section specifically dedicated to 'things' followed. (medium)
   * Classes: activity_assessments.xml, activity_main.xml
   * A user can search for a course and follow it, to add this course to their main screen.
   * unimplemented
      <br>
2. [Interact-Noti]. The ability to send notifications based on different types of interactions. A notification must be sent only after a predetermined number of interactions are set. (medium)
   * Classes: NotificationFactory.java, StudyNotification.java, StudyCourseNotification.java, StudyNumberNotification.java, StudyTimeNotification.java
   * A user will be notified if they have studied more than two courses, completed two or more study sessions, or if they have studied for longer than an hour. 
<br>

Feature Category: Firebase Integration <br>
3. [FB-Persist] Use Firebase to persist all data used in your app. (medium)
   * Class A: methods A, B, C, lines of code: whole file
   * unimplemented
      <br>
4. [FB-Syn] Using Firebase or another remote database to store user information and having the app
updated as the remote database is updated without restarting the application. (hard)
   * Class A: methods A, B, C, lines of code: whole file
   * unimplemented
      <br>

Feature Category: Greater Data Usage, Handling and Sophistication <br>
5. [Data-Graphical] Graphical report viewer. Provide users with the ability to see a report of interactions with your app in a graphical manner. (medium)
   * Class A: methods A, B, C, lines of code: whole file
   * unimplemented
      <br>


## Team Meetings

- *[Team Meeting 1](./meeting1.md)*
- *[Team Meeting 2](./meeting2.md)*
- *[Team Meeting 3](./meeting3.md)*
- *[Team Meeting 4](./meeting4.md)*
 

