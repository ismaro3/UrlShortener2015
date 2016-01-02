function showSafeOptions() {
	if (document.getElementById("safe").innerHTML == " I want a safe short Url") {
		document.getElementById("safe").innerHTML = " I don't want a safe short Url";
		$("p").show();
	} else {
		document.getElementById("safe").innerHTML = " I want a safe short Url";
		document.getElementById("users").innerHTML = "Which users can access the url?";
		document.getElementById("time").innerHTML = "How long do you want the url to be safe?";
		$("p").hide();
	}
}
