<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="/text" />
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>500 - Internal Server Error</title>
</head>
<body>
<div class="container">
    <h1><fmt:message key="error_500.name"/></h1>
    <p><fmt:message key="error_500.explanation"/></p>
    <a href="pages/register.jsp"><fmt:message key="error_505.exit"/></a>
</div>
</body>
</html>
