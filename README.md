# loytokiekot
 Lost and found discs project.  
  
 Currently working on the backend using Java Spring Boot.  
 See also [Endpoint documentation](https://github.com/t1rate01/loytokiekot/blob/main/ENDPOINTS.md)  

### Application purpose:  
 In the sport of Disc Golf, a lot of people lose their discs and the courses often have a lost & found box where  
 players who find these lost discs can return them. The boxes are always managed by a volunteer, but there is no  
 unified system or method for posting these lost & found discs. This app will aim to help those volunteers to post,  
 and help people who have lost their discs to have a good search system to find posted discs, and possibility to register  
 to the service and save their own keywords that they write in their discs, for example name, initials or player number.  
 The app runs a @Scheduled service that compares posted discs to these keywords and will have a notification system to  
 let the user know if a match is made, possibly email system.  
 The application also runs another @Scheduled service that checks the posted discs lifetime/expiration date, which is user  
 configureable but default set to 30. When a disc expires, it's deleted from the database.  
  
 The application uses a MySQL database, all tables and columns are created by the Spring Boot application.  

 The project requires a lot of self learning, searching through internet forums and learning from tutorials.    

  The frontend of the application is planned to start by building a React.js based webapplication that uses the backend server.  
  Possibly have the Spring Boot also serve the frontend.  
    
  Future as my ongoing React Native and Android Native(Kotlin) courses progress, I will also build a mobile application that makes the use even easier.  
