<%-- 
    Document   : template
    Created on : 29/04/2016, 03:53:12 PM
    Author     : gerson
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><spring:message code="app.nombre" /></title>
<!--title>SICOES</title-->
</head>
 <body>
 <jsp:include page="includes/cabecera.jsp" />
 <jsp:include page="${partial}" /> 
 <jsp:include page="includes/piepagina.jsp" />
 </body>
</html>