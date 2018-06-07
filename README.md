# Assignment 2 -- Test Version

In this assignment, we will prepare you a security checklist based on our classes and a "messy" Android app. The given app violates different security rules so that it is not secure and maintainable. As an example, application, some problems are exaggerated for learning purposes in this application.
You need to go through given codes and finish our checklist. Check all found problems. <b>Note that, this is not a detailed enough checklist in real Android development. </b>

## Security Principle Review
### Economy of mechanism
Economy of mechanism means that, Android developers need to keep codes simple so that errors are less likely occur.
Complex mechanism usually has following potential problems:

1. Tacit assumption. If a app must be run in a specific system, it will lead to security problems when the environment problem when the system does not meet the prerequisite.
2. Complex testing process. Complex app development often causes complex error-prone testing process.
3. High system assumption. If a security mechanism is based a high Android system version, unexpected problems could happen when the assumption goes wrong.
4. Unreadable design. It's more like for developer to miss problems in testing process if the code structure is not clear.
5. Reusing components if possible. If there is a well-established and reliable library for a function, you should use them instead of creating yours if possible. For example, you don't have to re-implement SHA-1 in your project.



### Least privilege
Least privilege principle requires developers pay extra attention to permission they give to every component in the app so that only minimal number of permissions are declared in your app.

Some topics are discussed in Google official document.

1. Unnecessary permission. Check your number of permission you declared in the manifest file ( < uses-feature > ) and abandon unnecessary permissions.
2. Appropriate storage methods. Using internal storage rather than external storage if possible.
3. Interprocess communication (IPC) protection. If there is IPC in your app, protect them using <permission> element.
4. Using possible access control. To avoid confusing user confirmed permission, using access control if possible.
5. Permission-protected data leakage. Be careful to data exposed during IPC.
6. Creating permission carefully. Don't create new permission if possible.
7. Check input permission. "Limiting permissions to read-only or write-only can also reduce the potential for harm related to SQL injection."
8. Apply a security policy for IPC. A security policy in <permission> element can protect your IPC.



### Complete mediation
We should check access to sensitive data every time if possible, which requires developers to be aware of sensitive data in the app.


### Secure default
We want default behavior of the application to be secure since users usually are not aware of security problems.
Users tend to ignore messages, such as security warning, that they don't really understand.
Other developer may don't understand the best way to interact with our code. simple way is default way. want the default behavior to be secure(such as handle data, unexpected input). On the other hand, new inexperienced users, may not operate your app incorrectly, which could also cause potential problems. You should ensure following benefits for your app.

1. Common benefits. Make sure users don't have to do anything special to get entire security features you provide.
2. Encrypt stored data. Users will have assumption that encryption is default.
3. Input validation. Always check input validation to avoid SQL injections.
4. Avoid Unnecessary data storage/transmission. Don't store uses' sensitive data if you don't have to.
5. Limit on-device logs. Logs in Android may leak sensitive data to users since they are shared resources.
6. Use Android system functionality for IPC. Android provides strong security by its functionality for IPC, so don't use the sockets/shared files if necessary.
7. Declare permission in a broadcast. Call broadcast with permission can prevent malicious apps from leaking sensitive data.

## Grading Criteria
27 points are extracted from 4 security principles above.
Students need to choose Yes/No to describe the given app for each problem. You should find at least 10 problems in the app.


## Checklist (For grading)

| Problem        | Check           | Problem Location |
| ------------- |:-------------:|:-------------:|
| <b>Economy of mechanism</b> ||
| There are not verbose function calls      | No | LoginActivity |
| Follow same naming convention in a program | No | cancelButton & cancel_button in the file|
| No redundant codes      	 |  No  |  NoteDetailActivity (remove unused codes) |
| No redundant comments      	 |  No  | NoteDetailActivity |
| Use constant variables rather than type constants  | No     |   NoteDetailActivity |
| Use well-established library if possible  | Yes | \ |
| There are not unnecessary Security mechanisms | Yes | \ |
| Choose low system requirements if an app does not require new features   | No | Android 7 is enough |
| <b>Least privilege </b> ||
|All declared permission should be used | No| AndroidManifest.xml |
|Appropriate storage method | Yes | \ |
| Limit permission for protecting IPC to avoid data leakage| Yes | \ |
| Use access control rather than use confirmed permission if possibile | ? |?|
| Every IPC are protected by <permission> element| Yes | \ |
| Self-defined permissions are applied only necessary and apply security policy for them | No|AndroidManifest.xml|
| Input permissions are limited to < read-only > or < write-only >| No| ? |
| <b>Complete mediation</b> ||
| All sensitive parts have access check|  No| ProfileActivity |
| <b>Secure default</b> ||
| Stored sensitive data are encryted | No | \ |
| Perform input validation | No |ProfileActivity|
| All data storages are necessary | No | ProfileActivity |
| All data transmission is necessary| Yes | \ |
| All on-device logs are necessary |No|LoginActivity|
| Ensure you choose correct Log type | No |LoginActivity|
| All IPC are performed using Android functionality, such as Intent| Yes| \ |
| Broadcasts are called with permission | Yes | \ |
| Don't use ignorable security dialog if you can perform a security protection in a better way | Yes | \ |


## Reference

[Economy of Mechanism](https://www.us-cert.gov/bsi/articles/knowledge/principles/economy-of-mechanism)

[Security Tips](https://developer.android.com/training/articles/security-tips)

[Enforcing Least Privilege with Android Permissions in Mobile App Development] (https://cups.cs.cmu.edu/soups/2014/posters/soups2014_posters-paper42.pdf)

[Android log difference] (https://stackoverflow.com/questions/7959263/android-log-v-log-d-log-i-log-w-log-e-when-to-use-each-one)

