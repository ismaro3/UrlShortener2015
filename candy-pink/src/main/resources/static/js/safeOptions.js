function showSafeOptions() {
	if (document.getElementById("safe").innerHTML == " I want a safe short Url") {
		document.getElementById("safe").innerHTML = " I don't want a safe short Url";
		$("p").show();
	} else {
		document.getElementById("safe").innerHTML = " I want a safe short Url";
		document.getElementById("users").selectedIndex = 0;
		document.getElementById("time").selectedIndex = 0;
		$("p").hide();
	}
}
