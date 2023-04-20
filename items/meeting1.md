# [G48]
This following is a very simple team meeting template. You should expand it based on the scope and nature of your discussion.

## Team Meeting [1] - Week [7] - [19/04/23] (1:30pm-2:10pm)
**Absent: none**
<br>
**Scribe: Lana & Punit**

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
- 1. Login screen: Allow users to create an account/login using their id/password
- 2. Course selection: Allow users to select courses from a predefined list of courses. Yanghe wanted
- wanted to use the database of ANU courses using python.
- 3. Assessments screen: For each course, display a list of assessments and allow users to set a timer for
- each assessments and allow users to set a timer for each assessment. 
- 4. Focus timer: A screen where users can see the timer countdown for the chosen assessment and pause or 
- reset it if necessary.
- 5. Progress Tracking: A screen to display the overall time spent on each course. 
- 6. Study group: Implement peer messaging to allow users to invite friends, chat and share the progress
- within the study group

- (More Ideas that can be implemented depending on the complexity:
- ** Notification: Implementing push notifications to remind users of upcoming assessments or motivating to study
- ** Rewards: Rewarding users using badges, rewards, leaderboards for a sense of achievement
- ** Focus mode: Implementing focus mode that restrict notifications from certain/all apps)

3. Implementing the features on our idea.
- Basic features:
- 1. Users must log in when starting the app
- 2. load the data/information of different courses and their assessments (possibly list of students in the course as well)
- 3. Changes to students in the course or assessment deadlines should be updated.
- 4. Users can search assessments based on due dates (changes to search can modified if not complex enough)
- 
- General features:
- 1. Search related features: Sorting and filtering todo tasks/assessments based on courses, time remaining, 
- due date, etc. Valid and invalid search queries need to be handled.
- 2. UI design and Testing: Portrait and landscape layout variants. Making UI aesthetically pleasing. Tests
- for search, and all the basic features.
- 3. Greater data usage and handling: Providing detailed analytics to user for every course, in comparison 
- with their members in the study group. Leaderboards could be implemented here.
- 4. User Interactivity: Add to-do lists for every course. Notifications(mentioned above) can be implemented here.
- Needs to be discussed further.
- 5. Creating processes: Rewards(mentioned above) could be implement here. (One idea could be, when you are completing
- your goals you are nurturing a plant and you see it grow while you progress) There could be more ideas about step-by-step
- process to help you achieve the target that you might have set for a particular course.
- 6. Peer-to-peer messaging: Can be implemented like a normal messaging app, where you can invite your friends and 
- form a study group, while at the same time you can also test students from your course.
- 7. Firebase Integration: Needs to be discussed.

## TODO Items
| Task                                                   |   Assignee |
|:-------------------------------------------------------|-----------:|
| [assign and implement base tasks by the end of week 8] | [everyone] |
| [try to finish all tasks of assignment by week 10]     | [everyone] |
