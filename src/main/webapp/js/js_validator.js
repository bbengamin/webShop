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

function validate() {
	var f1 = document.getElementsByClassName('error')
	for (var i = 0; i < f1.length; i++)
		f1[i].innerHTML = '';

	var form = document.getElementById('register-form');
	for (var i = 0; i < form.elements.length; i++) {
		var element = form.elements[i];
		if (element.hasAttribute("valideted")) {

			var error = document.getElementById(element.getAttribute("name")
					+ "Error");

			validateWithRegex(element,
					rules[element.getAttribute("valideted")].regex,
					rules[element.getAttribute("valideted")].error, error);
		}
	}
	
	var pass = document.getElementById('password_register');
	var pass_confirm = document.getElementsByName('password_confirm')[0];
	console.log(pass);
	console.log(pass_confirm);
	if(pass.value != pass_confirm.value){
		document.getElementById('password_confirmError').innerHTML = "Passwords not equals";
	}

}
function validateWithRegex(element, regex, message, errorBox) {
	if (regex) {
		if (!element.value.match(regex)) {
			errorBox.innerHTML = message;
		}
	} else {
		if (!element.value) {
			errorBox.innerHTML = message;
		}
	}
}