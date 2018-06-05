# Android Assignment (draft)

In this assignment, we prepared a security checklist based on our classes and a "messy" Android app. The given app violates different security rules so that it is not secure and maintainable. You need to go through the code structure and fill out the checklist form.


##Economy of mechanism
Economy of mechanism means that, Android developers need to keep codes simple so that errors are less likely occur. 
Complex mechanism usually has following potential problems:

1. Tacit assumption. If a app must be run in a specific system, it will lead to security problems when the environment problem when the system does not meet the prerequisite.

2. Complex testing process. 
3. Suspect Interface to other module. "modules often make implicit assumptions about input or output parameters or the current system state; should any of these assumptions be wrong, the module's actions may produce unexpected, and erroneous, results. Interaction with external entities, such as other programs, systems, or humans, amplified this problem."
4. Unreadable design. "is therefore more likely to include subtle problems that will be missed during analysis. 
".
5. Reusing components if possible. "Why would anyone want to re-implement AES or SHA-1, when there are several widely used libraries available?"
6. Unnecessary security mechanisms. "Such mechanisms could add unneeded complexity to the system and are potential sources of additional vulnerabilities."

\cite{https://www.us-cert.gov/bsi/articles/knowledge/principles/economy-of-mechanism}




##Least privilege 
Least privilege principle requires developers pay extra attention to permission they give to every component in the app so that only minimal number of permissions are declared in your app.

Based on Google official document, we prepare a checklist for this topic.

1. Unnecessary permission. Check your number of permission you declared in the manifest file ( < uses-feature > ) and abandon unnecessary permissions.
2. Appropriate storage methods. Using internal storage rather than external storage if possible.
3. Interprocess communication (IPC) protection. If there is IPC in your app, protect them using <permission> element.
4. Using possible access control. To avoid confusing user confirmed permission, using access control if possible.
5. Permission-protected data leakage. Be careful to data exposed during IPC.
6. Creating permission carefully. Don't create new permission if possible.
7. Check input permission. " Limiting permissions to read-only or write-only can also reduce the potential for harm related to SQL injection."
8. Apply a security policy for IPC. A security policy in <permission> element can protect your IPC.

\cite{https://cups.cs.cmu.edu/soups/2014/posters/soups2014_posters-paper42.pdf}

\cite{https://developer.android.com/training/articles/security-tips}

##Complete mediation
We should check access to sensitive data every time if possible, which requires developers to be aware of sensitive data in the app. We prepare a checklist for this topic.

1. Number of Sensitive data in an app. Evaluate your app and count all sensitive parts.
2. Sensitive access check. 


##Secure default
We want default behavior of the application to be secure.
Other developer may don't understand the best way to interact with our code. simple way is default way. want the default behavior to be secure(such as handle data, unexpected input). new inexperienced users, may not use it incorrectly.  
secure interface to other developers
secure way handle input
secure way to do nothing

The checklist is shown as below:

1. Common benefits. Make sure users don't have to do anything special to get entire security features you provide.
2. Encrypt stored data. “Consider enterprise application servers that have encryption turned off by default.” Users will have assumption that encryption is default.
3. "Realize that users aren't always right. Most users aren't well informed about security. They may not understand many security issues, so try to anticipate their needs. Don't give them security dialogs that they can ignore. Err on the side of security. If your assessment of their needs provides more security than theirs, use yours (actually, try to provide more security than you think they need in either case)."
4. Input validation. Always check input validation to avoid SQL injections.
5. Avoid Unnecessary data storage/transmission. Don't store uses' sensitive data if you don't have to.
6. Limit on-device logs. Logs in Android may leak sensitive data to users since they are shared resources.
7. Use Android system functionality for IPC. Android provides strong security by its functionality for IPC, so don't use the sockets/shared files if necessary.
8. Declare permission in a broadcast. Call broadcast with permission can prevent malicious apps from leaking sensitive data.

##Checklist

| Problem        | Check           |
| ------------- |:-------------:| 
| <b>Economy of mechanism</b> || 
| Redundant Coding      			 | 		|
| Confusing Function call     	 |      |   
| Directly used constant 			 |      |   
|Unnecessary self-created function|		 |
|Unnecessary Security mechanisms	 |		 |
|Unnecessary system requirements  |		 |
|Unsecure external interface      |		 |
| <b>Least privilege </b> || 
|necessary permission only||
|appropriate storage method||
|IPC protection||
|access control||
|permission-protected data leakage||
|self-defined permission||
|Limit input permission||
|Security policy||
| <b>Complete mediation</b> || 
|Number of sensitive data||
|Sensitive access check||
| <b>Secure default</b> || 
|Encrypt stored data||
|Input validation||
|necessary data storage||
|necessary data transmission||
|On-device logs||
|Android functionality for IPC||
|Declare broadcast permission||

