# Assignment2


## Overview
This assignment will focus on enhancing your understanding of secure coding principles by having you make changes on a given Android app - SimpleNote, to make it sustainable.   


## Goal
You are given an application SimpleNote that allows users to create, update, and delete a note. You can also set up an alarm on certain note and get notification at set time. You can test it either on your own device or on a simulator. However, the app violates secure coding principles discussed in videos. To complete the assignment, you will need to find out those violations and correct them. 


## Instructions
**Assignment Preparation**

Before diving in to coding assignment, please make sure you have done preparations correctly.

* Install Android Studio and SDK.
* Clone project from  <https://github.com/MaintainableAndroidApps/SimpleNote> to your local working file.
* Run your project on your simulator or device. 

**Code to complete**

You now have a working app at this point, please find all   ***//ToDo...*** in the project and follow the instructions below.


* Redundant Permission: In AndroidManifest.xml file, please find redundant permissions and delete unnecessary ones, in case of information leakage.
* Constant Maintenance: It is not good practice to hard code strings into your layout files. It also makes the app harder to maintain. Please find hard coded string in the project and add them to a string resource file and then reference them from your layout.
* Secure Default: The App broadcasts a notification when events are triggered. Edit the code in NoteDetailActivity.java to ensure secure broadcast.
* Information Leak: According to Android documentation, *"By default, files saved to the internal storage are private to your app, and other apps cannot access them. This makes internal storage a good place for internal app data that the user doesn't need to directly access."* Please find the permission misuse in the project and correct it.


## Submitting Your Assignment
Our Grading backend is still in development, once the backend is complete, you will be able to recieve an offical grade.

