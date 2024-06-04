<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/text" />
<!DOCTYPE html>
<html lang="${language}">
<head>
    <title>JSP/JSTL i18n demo</title>
</head>
<body>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
    </select>
</form>
<h1><fmt:message key="register.h1" /> </h1>
<form action="${pageContext.request.contextPath}/register" method="post">
    <div>
        <label for="username"> <fmt:message key="register.label.username" /></label>
        <input type="text" id="username" name="username" required pattern="\w+" title="Username must only contain letters, numbers and underscores.">
    </div>
    <div>
        <label for="email"> <fmt:message key="login.label.email" /></label>
        <input type="email" id="email" name="email" required>
    </div>
    <label for="password"> <fmt:message key="login.label.password" /></label>
    <input type="password" id="password" name="password" required pattern=".{8,}" title="Password must be at least 8 characters long.">
    <div>
        <fmt:message key="register.button.submit" var="buttonValue" />
        <input type="submit" name="submit" value="${buttonValue}">
    </div>
</form>
<p><a href="login.jsp"><fmt:message key="base.login"/></a></p>
</body>
</html>