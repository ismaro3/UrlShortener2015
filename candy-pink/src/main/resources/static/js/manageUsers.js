function showUsers() {
  var users = (new UserRepositoryImpl()).getAllUsers();
  var text = "";
  if (users.length) {
    text = "The are no users using your service<br>";
  }
  else {
    text = fruits.join("<br>");
  }
  document.getElementById("users").innerHTML = text;
}
