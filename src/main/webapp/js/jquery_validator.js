var rules = {};
rules["firstName"] = {
	regex : /^[A-Za-z]+$/,
	error : "Enter correct first name"
};
rules["email"] = {
	regex : /^(([a-zA-Z]|[0-9])|([-]|[_]|[.]))+[@](([a-zA-Z0-9])|([-])){2,63}[.](([a-zA-Z0-9]){2,63})+$/,
	error : "Enter correct email"
};
rules["phone"] = {
	regex : /^[0-9]{10}$/,
	error : "Enter correct phone"
};
rules["login"] = {
	regex : /^[0-9a-zA-Z]+$/,
	error : "Enter correct login"
};
rules["password"] = {
	regex : "",
	error : "Enter correct password"
};
rules["password_confirm"] = {
	regex : "",
	error : "Confirm password"
};

$("#submit").click(
		function() {
			$(".error").text("");
			var elements = $("#register-form input[valideted]").each(
					function() {
						validateWithRegex(this,
								rules[$(this).attr("valideted")].regex,
								rules[$(this).attr("valideted")].error);
					});
			if($("#register-form input[name='password']").val() != $("#register-form input[name='password_confirm']").val()){
				$("#password_confirmError").text("Passwords not equals");
			}

		});
function validateWithRegex(element, regex, message) {
	if (regex) {
		if (!$(element).val().match(regex)) {
			var errorBox = $("#" + $(element).attr("name") + "Error");
			$(errorBox).text(message);
		}
	}else{
		if(!$(element).val()){
			var errorBox = $("#" + $(element).attr("name") + "Error");
			$(errorBox).text(message);
		}
	}
}