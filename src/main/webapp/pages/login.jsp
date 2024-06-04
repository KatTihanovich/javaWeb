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
<form action="${pageContext.request.contextPath}/login" method="post">
    <label for="email"><fmt:message key="login.label.email" />:</label>
    <input type="text" id="email" name="email" required>
    <br>
    <label for="password"><fmt:message key="login.label.password" />:</label>
    <input type="password" id="password" name="password" required>
    <br>
    <fmt:message key="login.button.submit" var="buttonValue" />
    <input type="submit" name="submit" value="${buttonValue}">
</form>

<p><a href="register.jsp"><fmt:message key="base.register"/></a></p>
</body>
</html>