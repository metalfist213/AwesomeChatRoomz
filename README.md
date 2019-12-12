# AwesomeChatRoomz
 The solution to the problem provided by House of Code.

## Architecture
This project aims at using the MVVM architecture. MVVM promotes data binding, which in this case is a strong advantage. The recycler view for instance, scales very well with the amount of data. Presenting chat rooms is done with ease.


## Dependencies

### Authentication
Facebook- and Google login. Required for login method.

### Dependency Injection
Dagger and Android Dagger. Used to avoid boilerplate code.

### Picasso
For displaying and handling images with ease. A great thing about Picasso is that it caches images automatically. Makes for a smoother experience.

### Room
Avoid boilerplate code when writing and reading to the local database.

### Firebase
Different dependencies added for both Firebase Storage (for images/avatars), and also Firebase Database (for lighter data).
