# [Team Name]
## Team Meeting [3] - Week [10] - [12/05/23] (8pm-10pm)
**Absent: none** 
<br>
**Lead/scribe:Lana**

## Agenda Items
| Number |                                                         Item |
|:-------|-------------------------------------------------------------:|
| [1]    | [Discuss linking the login and register process to Firebase] |
| [2]    |  [Discuss and finalise the logic and structure of the Timer] |
| [3]    |    [Consolidate the flow between UI elements within the app] |
| [4]    |        [Discuss how to structure and upload the Course data] |

## Meeting Minutes
- We discussed the possibility of simulating a data stream by reading off a JSON file of with 2500 instances of user and 
course data. Ahmed and Punit will work together to upload this data to Firebase, and additionally will edit the Login and 
Register classes to upload and retrieve data from Firebase.
- Steven is currently working in the Query class which extends upon the Search and Tokenizer and Parser functionality. 
- We decided to create a hashmap of courses and the corresponding time studied, to easily store and access our user data.
- Yanghe will work on the Notification functionality, which will be implemented in the Timer class.
- Lana will work on the Report and UML, and will write test cases if needed.
- We discussed some of the UI elements, such as the results of a search being presented in an array adapter, and by clicking on 
a course in the search activity will link to a short information summary of that course.
- We discussed the flow between UI elements within the app, and decided that the flow should be as follows:
    - Register -> Login -> Main -> Search -> Add Courses -> Set Timer -> Start Study -> Main -> Set Timer or View Graphical Data.
- We also discussed the possibility of adding a Settings page, which will be implemented after the Report page.

## TODO Items
| Task                               |           Assignee |
|:-----------------------------------|-------------------:|
| [Link Registration to Firebase]    |  [Ahmed and Punit] |
| [Finalise Search Functionality]    |           [Steven] |
| [Create 2500 data instances]       | [Ahmed and Yanghe] |
| [Start Notification Functionality] |           [Yanghe] |
| [Start Report and UML]             |             [Lana] |
