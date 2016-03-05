<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<jsp:include page="/WEB-INF/jspf/header.jsp" />
<%@ taglib tagdir="/WEB-INF/tags" prefix="cp"%>
<!-- Main -->
<div id="main" class="clearfix">
	<!-- Content -->
	<div id="content">
		<span id="register-title">Register</span>
		<div id="register">
			<form id="register-form" action="register" method="post" enctype="multipart/form-data">
				
				<label>Personal Information</label> <input type="text"
					valideted="firstName" placeholder="Enter firts name"
					name="firstName" value="${sessionScope.registerForm.firstName }" />
				<label class="error" id="firstNameError">${sessionScope.errors.firstName }</label>
				<input type="text" placeholder="Enter last name" name="lastName"
					value="${sessionScope.registerForm.lastName }" /> <input
					type="email" valideted="email" placeholder="Enter your e-mail"
					name="email" value="${sessionScope.registerForm.email }" /> <label
					class="error" id="emailError">${sessionScope.errors.email }</label>
				<input type="tel" valideted="phone"
					placeholder="Enter contact phone" name="phone"
					value="${sessionScope.registerForm.phone }" /> <label
					class="error" id="phoneError">${sessionScope.errors.phone }</label>
				<label>Account information</label> <input type="text"
					valideted="login" placeholder="Enter login" name="login"
					value="${sessionScope.registerForm.login }" /> <label
					class="error" id="loginError">${sessionScope.errors.login }</label>
				<input type="password" placeholder="Enter password"
					valideted="password" name="password" id="password_register" /> <label
					class="error" id="passwordError">${sessionScope.errors.password }</label>
				<input type="password" placeholder="Confirm password"
					valideted="password_confirm" name="password_confirm" /> <label
					class="error" id="password_confirmError">${sessionScope.errors.password_confirm }</label>
				<label>Chose faculty</label> <select name="faculty">

					<option
						${sessionScope.registerForm.faculty == 0 ? 'selected="selected"' : ''}
						value="0">Gryphindor</option>
					<option
						${sessionScope.registerForm.faculty == 1 ? 'selected="selected"' : ''}
						value="1">Slytherin</option>
					<option
						${sessionScope.registerForm.faculty == 2 ? 'selected="selected"' : ''}
						value="2">Ravenclaw</option>
					<option
						${sessionScope.registerForm.faculty == 3 ? 'selected="selected"' : ''}
						value="3">Hufflepuff</option>
				</select> <span id="delivery"><input class="delivery-box"
					type="checkbox" name="delivery" checked="checked" value="true" />
					<p>Get notified on new products</p></span>
				<cp:captcha captKey="${captchaKey}" />
				<div id="img-block">
				<img id='avatar-temp' src="images/noavatar.png" width='200' height='200'/>
				<input id="i_file" type="file" name="avatar" accept="image/jpeg,image/png,image/gif" style="display: none;">
				<button id='btn-upload'>Load image</button>
				</div>
				<input type="submit" id="submit" value="Register" />
				<!-- <a type="submit" id="submit" onclick="validate()"  >Register</a> -->
			</form>
		</div>
	</div>
	<!-- End Content -->
	<!-- Sidebar -->
	<div class="cl">&nbsp;</div>
</div>
<!-- End Main -->
</div>
<script>
$('#i_file').change( function(event) {
    var tmppath = URL.createObjectURL(event.target.files[0]);
    $("#avatar-temp").fadeIn("fast").attr('src',URL.createObjectURL(event.target.files[0]));
});

$('#btn-upload, #avatar-temp').click(function(e){
    e.preventDefault();
    $('#i_file').click();}
);

</script>
<!-- End Shell -->
<!-- 	<script src="js/jquery_validator.js" ></script> -->
<!-- 	<script src="js/js_validator.js" ></script>  -->
<jsp:include page="/WEB-INF/jspf/footer.jspf" />
