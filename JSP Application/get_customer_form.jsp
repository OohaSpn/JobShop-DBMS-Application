<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Retrieving Customer Details</title>
    </head>
    <body>
        <h2>Retrieving Customer Details</h2>
        <!--
            Form for collecting user input for the new movie_night record.
            Upon form submission, add_movie.jsp file will be invoked.
        -->
        <form action="get_all_movies.jsp">
            <!-- The form organized in an HTML table for better clarity. -->
             <!-- The form organized in an HTML table for better clarity. -->
            <table border=1>
                <tr>
                    <th colspan="2">Enter the starting range of category:</th>
                </tr>
                <tr>
                    <td>Category from:</td>
                    <td><div style="text-align: center;">
                    <input type=text name=CategoryFrom>
                    </div></td>
                </tr>
                <tr>
                    <td>Category to:</td>
                    <td><div style="text-align: center;">
                    <input type=text name=CategoryTo>
                    </div></td>
                </tr>
                
                <tr>
                    <td><div style="text-align: center;">
                    <input type=reset value=Clear>
                    </div></td>
                    <td><div style="text-align: center;">
                    <input type=submit value=Search>
                    </div></td>
                </tr>
            </table>
        </form>
    </body>
</html>
            