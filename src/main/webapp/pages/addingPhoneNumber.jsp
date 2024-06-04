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
<h2><fmt:message key="addContact.h2" /></h2>
<form action="${pageContext.request.contextPath}/addNumber" method="post" enctype="multipart/form-data">
    <div>
        <label for="image"> <fmt:message key="addContact.label.image" /></label>
        <input type="file" id="image" name="image">
    </div>
    <div>
        <label for="name"> <fmt:message key="addContact.label.name" /></label>
        <input type="text" id="name" name="name" required pattern="\w+" title="name must only contain letters, numbers and underscores.">
    </div>
    <div>
        <label for="number"> <fmt:message key="addContact.label.number" /></label>
        <input type="text" id="number" name="number" required>
    </div>
    <div>
        <fmt:message key="addContact.button.submit" var="buttonValue" />
        <input type="submit" name="submit" value="${buttonValue}">
    </div>
</form>
</body>
</html>