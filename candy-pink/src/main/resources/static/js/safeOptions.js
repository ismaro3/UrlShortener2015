function showSafeOptions() {
	if (document.getElementById("safe").innerHTML == " I want a safe short Url") {
		document.getElementById("safe").innerHTML = " I don't want a safe short Url";
		$("p").show();
	} else {
		document.getElementById("safe").innerHTML = " I want a safe short Url";
		document.getElementById("show").value = "select";
		$("p").hide();
	}
}
