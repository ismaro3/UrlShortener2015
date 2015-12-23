function showUsers(var users) {
  var text = "";
  if (users.length == 0) {
    text = "The are no users using your service<br>";
  }
  else {
    text = users.join("<br>");
  }
  document.getElementById("users").innerHTML = text;
}
