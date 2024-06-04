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
<h1><fmt:message key="update.name.h1"/></h1>
<form action="${pageContext.request.contextPath}/editPhoneName" method="post">
    <label for="oldNumber"> <fmt:message key="contact.delete.oldnumber"/></label><br>
    <input type="text" id="oldNumber" name="oldNumber" ><br>
    <label for="newName"> <fmt:message key="update.name.newname"/></label><br>
    <input type="text" id="newName" name="newName" ><br>
    <fmt:message key="addContact.button.submit" var="buttonValue" />
    <input type="submit" name="submit" value="${buttonValue}">
</form>
</body>
</html>