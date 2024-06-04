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
<h1><fmt:message key="home.h1"/></h1>

<h2><fmt:message key="home.h2"/></h2>

<p><a href="${pageContext.request.contextPath}/user"><fmt:message key="home.account"/></a></p>
<p><a href="updateAccount.jsp"><fmt:message key="home.update.username"/></a></p>
<p><a href="${pageContext.request.contextPath}/numbers"><fmt:message key="home.contacts"/></a></p>
<p><a href="addingPhoneNumber.jsp"><fmt:message key="home.contacts.add"/></a></p>
<p><a href="phoneNumberNameEditing.jsp"><fmt:message key="home.contacts.update.name"/></a></p>
<p><a href="phoneNumbersEditing.jsp"><fmt:message key="home.contacts.update.number"/></a></p>
<p><a href="phoneNumberImageEditing.jsp"><fmt:message key="home.contacts.update.image"/></a></p>
<p><a href="phoneNumberDeleting.jsp"><fmt:message key="home.contacts.delete"/></a></p>
<p><a href="${pageContext.request.contextPath}/logout"><fmt:message key="home.signout"/></a></p>
<p><a href="${pageContext.request.contextPath}/delete"><fmt:message key="home.account.delete"/></a></p>
</body>
</html>