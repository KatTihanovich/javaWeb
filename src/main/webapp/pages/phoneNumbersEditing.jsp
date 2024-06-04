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
<h1><fmt:message key="update.number.h1"/></h1>
<form action="${pageContext.request.contextPath}/editPhoneNumber" method="post">
    <label for="oldNumber"> <fmt:message key="update.number.oldnumber"/></label><br>
    <input type="text" id="oldNumber" name="oldNumber" ><br>
    <label for="newNumber"> <fmt:message key="update.number.newnumber"/></label><br>
    <input type="text" id="newNumber" name="newNumber" ><br>
    <fmt:message key="addContact.button.submit" var="buttonValue" />
    <input type="submit" name="submit" value="${buttonValue}">
</form>
</body>
</html>