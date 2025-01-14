# [G48]

## Team Meeting [1] - Week [7] - [19/04/23] (1:30pm-2:10pm)
**Absent: none**
<br>
**Scribe: Lana (u7103031)  & Punit**

## Agreed Procedure
Meetings: Weekly based on our availability
Contribution: Equal contributions as a preliminarily and will be readjusted based on total time spent, 
and difficulty of tasks. Disputes in contributions will be resolved by a team meeting and voting.  

## Agenda Items
| Number   |                    Item |
|:---------|------------------------:|
| [1]      |             [App ideas] |
| [2]      | [Distribution of tasks] |

## Meeting Minutes
Discussed the main idea for our project. We decided on a app that tracks the amount of time you study 
a certain course/assignment, and gives graphical summary data of your progress. We also decided to include
search functionality, peer to peer messaging, and firebase. We have decided to distribute and and work on
the base tasks.
Agreed upon detailed app specifications:

1. App's purpose:
Helping users manage their study time effectively and enabling peer-to-peer communication for study 
groups. Target users are students, professionals and learners.

2. App's interface:
- Login screen: Allow users to create an account/login using their id/password
- Course selection: Allow users to select courses from a predefined list of courses. Yanghe wanted
wanted to use the database of ANU courses using python.
- Assessments screen: For each course, display a list of assessments and allow users to set a timer for
each assessments and allow users to set a timer for each assessment. 
- Focus timer: A screen where users can see the timer countdown for the chosen assessment and pause or 
reset it if necessary.
- Progress Tracking: A screen to display the overall time spent on each course. 
- Study group: Implement peer messaging to allow users to invite friends, chat and share the progress
within the study group

(More Ideas that can be implemented depending on the complexity: <br>
** Notification: Implementing push notifications to remind users of upcoming assessments or motivating to study <br>
** Rewards: Rewarding users using badges, rewards, leaderboards for a sense of achievement<br>
** Focus mode: Implementing focus mode that restrict notifications from certain/all apps)<br>

3. Implementing the features on our idea.<br>
Basic features:
- Users must log in when starting the app
- load the data/information of different courses and their assessments (possibly list of students in the course as well)
- Changes to students in the course or assessment deadlines should be updated.
- Users can search assessments based on due dates (changes to search can modified if not complex enough)
<br>
General features:
- Search related features: Sorting and filtering todo tasks/assessments based on courses, time remaining, 
due date, etc. Valid and invalid search queries need to be handled.
- UI design and Testing: Portrait and landscape layout variants. Making UI aesthetically pleasing. Tests
for search, and all the basic features.
- Greater data usage and handling: Providing detailed analytics to user for every course, in comparison 
with their members in the study group. Leaderboards could be implemented here.
- User Interactivity: Add to-do lists for every course. Notifications(mentioned above) can be implemented here.
Needs to be discussed further.
- Creating processes: Rewards(mentioned above) could be implement here. (One idea could be, when you are completing
your goals you are nurturing a plant and you see it grow while you progress) There could be more ideas about step-by-step
process to help you achieve the target that you might have set for a particular course.
- Peer-to-peer messaging: Can be implemented like a normal messaging app, where you can invite your friends and 
form a study group, while at the same time you can also test students from your course.
- Firebase Integration: Needs to be discussed.

## TODO Items
| Task                                                   |   Assignee |
|:-------------------------------------------------------|-----------:|
| [assign and implement base tasks by the end of week 8] | [everyone] |
| [try to finish all tasks of assignment by week 10]     | [everyone] |
