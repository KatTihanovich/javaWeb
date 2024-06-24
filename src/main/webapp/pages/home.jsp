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
    <style>
        .centered-image {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .centered-image img {
            max-width: 50%;
            height: 50%;
        }
    </style>
</head>
<body>
<form>
    <select id="language" name="language" onchange="submit()">
        <option value="en" ${language == 'en' ? 'selected' : ''}>English</option>
        <option value="ru" ${language == 'ru' ? 'selected' : ''}>Russian</option>
    </select>
</form>
<h1><fmt:message key="home.h1"/>
    <custom:greetUser />
</h1>

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

<div class="centered-image">
    <img src="${pageContext.request.contextPath}/images/homeImage.jpg" alt="Home Image">
</div>
</body>
</html>