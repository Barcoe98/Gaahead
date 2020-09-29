# GAAhead App

As Part of a Third year assignment we were tasked with creating an app using android studio and developed using kotlin. 
I chose to design and develope a club management app, 
where you can manage the club u are managing or as a player yopu can view you stats and results fixtures etc

# App Walkthrough
## Login Fragments
When you launch the app, you are greeted with a login screen. There are 3 options here, sign in, Create Account and Sign in with Google.

https://res.cloudinary.com/dmikx06rt/image/upload/v1588542212/login1_uaqaj9.png
![Image of Yaktocat](https://https://res.cloudinary.com/dmikx06rt/image/upload/v1588542212/login1_uaqaj9.png
)

### Sign In
To Sign in you must enter the email and password of the account and the type of user you are either Admin, Manager or Player. Depending on which user type you enter you with be brought to the corresponding fragments. 
### Create Account
When Creating Account, you must enter your email, password and user type. This will create an account on Firebase Authenticate with just the email and password, will also create a user in the user databases with a third field for user type. This field will be used to determine which home screen to load when signing in.
### Google Sign In
When using Google sign, the app prompts you to sign in with you google account, once signed in you are brought to the supporter home page


## Fixture Fragments
Fixture Fragments are used for a user to create a fixture of a upcoming match , which is displayed to all users. Fixture Fragments have crud functionality, They are displayed on a recycler view, if you swipe left or right it will delete or edit the fixture. Fixture All Fragments display all users fixtures. Favourites fixtures shows the Fixture the user has Bookmarked on a separate view

<img src="https://res.cloudinary.com/dmikx06rt/image/upload/v1588542212/fixture1_ryaavo.png">
<img src="https://res.cloudinary.com/dmikx06rt/image/upload/v1588542212/fixture2_orn7qj.png">
<img src="https://res.cloudinary.com/dmikx06rt/image/upload/v1588542212/fixture3_qumlax.png">
<img src="https://res.cloudinary.com/dmikx06rt/image/upload/v1588542212/fixture4_gmptee.png">

## Players Fragments
Player Fragments are used for a manger to store details about each player in his team. Again, like Fixtures and results, Player has crud functionality. These are displayed on a recycler view and can we delete or edited by  a swipe left or right. Player All Fragment displays all users Players on a recycler view.

<img src="https://res.cloudinary.com/dmikx06rt/image/upload/v1588542212/player4_h9sfsg.png">
<img src="https://res.cloudinary.com/dmikx06rt/image/upload/v1588542212/player1_ongx1s.png">
<img src="https://res.cloudinary.com/dmikx06rt/image/upload/v1588542212/player2_vxpeau.png">
<img src="https://res.cloudinary.com/dmikx06rt/image/upload/v1588542212/player3_cr6bq3.png">

## Results Fragments
Results are used to record a result of a fixture. Result Fragments also have crud Functionality. These are displayed on a recycler view and can we delete or edited by  a swipe left or right. Result All Fragments Display all users result on a view.

<img src="https://res.cloudinary.com/dmikx06rt/image/upload/v1588541935/addResult_plglra.png">
<img src="https://res.cloudinary.com/dmikx06rt/image/upload/v1588541935/resultList_kk961f.png">
<img src="https://res.cloudinary.com/dmikx06rt/image/upload/v1588541935/allResultList_kerzet.png">
<img src="https://res.cloudinary.com/dmikx06rt/image/upload/v1588541935/editResult_nfcm4l.png">

## Clubs Fragments
Club Fragments are used to show details of a club .Admin is the only user to be able to add a club or remove one. Club has Basic feature and are displayed o a recycler view. When a club is clicked on a user can View details about the club such as history, year Founded etc. You can also Bookmark a club which is displayed on a separate view where all your bookmarked clubs are.

<img src="https://res.cloudinary.com/dmikx06rt/image/upload/v1588542212/club2_bbv53s.png">
<img src="https://res.cloudinary.com/dmikx06rt/image/upload/v1588542212/club1_okrk7t.png">


## Pins Fragments
The idea behind Pins, was for users to be able to pin their current location so show other users where a GAA pitch is. They enter the title and comment about the pitch, then their pin is saved with their current location. Pin Fragments are displayed on a map, User can click on a pin icon to get the pins title.

<img src="https://res.cloudinary.com/dmikx06rt/image/upload/v1588542212/pin1_thgfr1.png">
<img src="https://res.cloudinary.com/dmikx06rt/image/upload/v1588542212/pin2_rg8utm.png">




## Built With
* Android Studio
* Kotlin
* Firebase Authenticate
* Firebase Database
* Firebase Storage
* Firebase UI
* Maps Api

## Prerequisites
* Kotlin

## Contributing

* https://tutors-design.netlify.com/course/mobile-app-dev-2-2020.netlify.com
* Mobile App 1

## Authors
* @Michael Barcoe - barcoe98@gmail.com - https://github.com/barcoe98

## Acknowledgments
* https://firebase.google.com/docs 
* dDrohan
