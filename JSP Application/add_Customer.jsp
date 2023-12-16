<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Query Result</title>
</head>
    <body>
    <%@page import="jsp_azure.DataHandler"%>
    <%@page import="java.sql.ResultSet"%>
    <%@page import="java.sql.Array"%>
    <%
    // The handler is the one in charge of establishing the connection.
    DataHandler handler = new DataHandler();

    // Get the attribute values passed from the input form.
    String CustomerName = request.getParameter("CustomerName");
    String Address = request.getParameter("Address");
    int Category = Integer.parseInt(request.getParameter("Category"));
    /*
    String g1 = request.getParameter("guest_1");
    String g2 = request.getParameter("guest_2");
    String g3 = request.getParameter("guest_3");
    String g4 = request.getParameter("guest_4");
    String g5 = request.getParameter("guest_5");
    */

    /*
     * If the user hasn't filled out all the time, movie name and duration. This is very simple checking.
     */
   
        
        // Now perform the query with the data from the form.
        boolean success = handler.Customer(CustomerName, Address, Category);
        if (!success) { // Something went wrong
            %>
                <h2>There was a problem inserting the course</h2>
            <%
        } else { // Confirm success to the user
            %>
            <h2>Customer table:</h2>

            <ul>
                <li>CustomerName: <%= CustomerName%></li>
                <li>Address: <%= Address%></li>
                <li>Category: <%=Category%></li>
                
                
                
            </ul>

            <h2>Was successfully inserted.</h2>
            
            <a href="get_all_movies.jsp">See all Customer Names.</a>
            <%
        }
    %>
    </body>
</html>
