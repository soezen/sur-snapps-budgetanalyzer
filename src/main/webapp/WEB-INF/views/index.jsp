<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Tommcat Spring JPA ClickStart</title>
</head>
<body>
    <h1>Tommcat Spring JPA ClickStart</h1>

    <h1>Products</h1>
    <table border="1">
        <thead>
            <tr>
                <th>Name</th>
                <th>Description</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${productlist}" var="product">
                <tr>
                    <td>${product.name}</td>
                    <td>${product.description}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>