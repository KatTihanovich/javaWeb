<%@ page pageEncoding="UTF-8" %>
<%@ page import="com.esde.web.model.PhoneNumber" %>
<%@ page import="java.util.List" %>
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
<h1><fmt:message key="contacts.h1" /></h1>

<c:choose>
    <c:when test="${empty contactsList}">
        <p><fmt:message key="contacts.p" /></p>
    </c:when>
    <c:otherwise>
        <table border="2">
            <tr>
                <th><fmt:message key="contacts.th.image" /></th>
                <th><fmt:message key="contacts.th.name" /></th>
                <th><fmt:message key="contacts.th.number" /></th>
            </tr>
            <c:forEach items="${contactsList}" var="contact">
                <tr>
                    <td>
                        <c:choose>
                            <c:when test="${not empty contact.base64Image}">
                                <img class="user-image" src="data:image/png;base64,${contact.base64Image}" alt="User Image" width="100" height="100">
                            </c:when>
                            <c:otherwise>
                                <img class="user-image" src="${pageContext.request.contextPath}/images/baseImage.jpg" alt="No Image Available" width="100" height="100">
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>${contact.lastname}</td>
                    <td>${contact.phone}</td>
                </tr>
            </c:forEach>
        </table>
    </c:otherwise>
</c:choose>

</body>
