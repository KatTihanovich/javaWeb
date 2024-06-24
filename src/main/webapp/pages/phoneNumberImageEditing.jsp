<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://example.com/tags" prefix="custom" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/text" />
<!DOCTYPE html>
<html lang="${language}">
<head>
    <title>Kat Tihanovich demo</title>
</head>
<body>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
    </select>
</form>
<h1><fmt:message key="update.photo.h1"/>
    <custom:greetUser />
</h1>
<form action="${pageContext.request.contextPath}/editPhoneImage" method="post" enctype="multipart/form-data">
    <label for="oldNumber"> <fmt:message key="contact.delete.oldnumber"/></label><br>
    <input type="text" id="oldNumber" name="oldNumber" >
    <label for="image"> <fmt:message key="addContact.label.image"/></label>
    <input type="file" id="image" name="image">
    <fmt:message key="addContact.button.submit" var="buttonValue" />
    <input type="submit" name="submit" value="${buttonValue}">
</form>
</body>
</html>