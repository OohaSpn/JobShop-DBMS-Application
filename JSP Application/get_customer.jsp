<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
    <meta charset="UTF-8">
        <title>Customer Table</title>
    </head>
    <body>
        <%@page import="jsp_azure.DataHandler"%>
        <%@page import="java.sql.ResultSet"%>
        <%
            // We instantiate the data handler here, and get all the movies from the database
        final DataHandler handler = new DataHandler();
        int CategoryFrom = Integer.parseInt(request.getParameter("CategoryFrom"));
    	int CategoryTo = Integer.parseInt(request.getParameter("CategoryTo"));
        final ResultSet movies = handler.getbyCategory(CategoryFrom,CategoryTo);
        %>
        <!-- The table for displaying all the movie records -->
        <table cellspacing="2" cellpadding="2" border="1">
            <tr> <!-- The table headers row -->
              <td align="center">
                <h4>CustomerName</h4>
              </td>
              <td align="center">
                <h4>Address</h4>
              </td>
              <td align="center">
                <h4>Category</h4>
              </td>
              
            </tr>
            <%
               while(movies.next()) { // For each movie_night record returned...
                   // Extract the attribute values for every row returned
                   final String CustomerName = movies.getString("CustomerName");
                   final String Address = movies.getString("Address");
                   final int Category = movies.getInt("Category");
                   
                   out.println("<tr>"); // Start printing out the new table row
                   out.println( // Print each attribute value
                        "<td align=\"center\">" + CustomerName +
                        "</td><td align=\"center\"> " + Address +
                        "</td><td align=\"center\"> " + Category + "</td>");
                   out.println("</tr>");
               }
               %>
          </table>
    </body>
</html>
