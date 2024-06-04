<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.esde.web.model.User" %>
<% User user = (User) request.getSession().getAttribute("user");%>
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

<table >

    <tr>
        <th><fmt:message key="account.th.username"/></th>
        <td>${user.username}</td>
    </tr>
    <tr>
        <th><fmt:message key="account.th.email"/></th>
        <td>${user.email}</td>
    </tr>
</table>
</body>
</html>

