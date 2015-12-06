$(document).ready(
    function() {
        $("#shortener").submit(
            function(event) {
                event.preventDefault();
                $.ajax({
                    type : "POST",
                    url : "/link",
                    data : $(this).serialize(),
                    success : function(msg) {
                        $("#result").html(
                            "<div class='alert alert-success lead'><a target='_blank' href='"
                            + msg.uri
                            + "'>"
                            + msg.uri
                            + "</a></div>");
                    },
                    error : function() {
                        $("#result").html(
                                "<div class='alert alert-danger lead'>An error has ocurred</div>");
                    }
                });
            });
    }
    function() {
        $("#redirect").submit(
            function(event) {
                event.preventDefault();
                 url = "http://localhost:8080/login.html";
                $(location).attr("href", url);
            });
    }
);
