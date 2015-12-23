function showUsers() {
  List<User> users = (new UserRepositoryImpl()).getAllUsers();
  String text = "";
  if (users.isEmpty()) {
    text = "The are no users using your service" + "<br>";
  }
  else {
    var i = 0;
    for (; i < users.size(); i++) {
      text += "Username: " + users[i].getUsername() +
              " Password: " + users[i].getPassword() + "<br>";
    }
  }
  document.getElementById("users").innerHTML = text;
}
