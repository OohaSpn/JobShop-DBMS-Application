package jsp_azure;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DataHandler {

    private Connection conn;

    // Azure SQL connection credentials
    private String server = "your server address";
    private String database = "your database name";
    private String username = "******";
    private String password = "********";

    // Resulting connection string
    final private String url =
            String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
                    server, database, username, password);
    // Initialize and save the database connection
    private void getDBConnection() throws SQLException {
        if (conn != null) {
            return;
        }

        this.conn = DriverManager.getConnection(url);
    }

    // Return the result of selecting everything from the movie_night table 
    public ResultSet getAllMovies() throws SQLException {
        getDBConnection();
        
        final String sqlQuery = "SELECT * FROM Customer;";
        final PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        return stmt.executeQuery();
    }
    public ResultSet getbyCategory(int CategoryFrom ,int CategoryTo) throws SQLException {
        getDBConnection();
        
        final String sqlQuery = "SELECT * FROM Customer where category between ? and ?;";
        final PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        stmt.setInt(1, CategoryFrom);
        stmt.setInt(2, CategoryTo);
        
        return stmt.executeQuery();
    }


    // Inserts a record into the movie_night table with the given attribute values
    public boolean Customer(String CustomerName,String Address,int Category) throws SQLException {

        getDBConnection(); // Prepare the database connection

        // Prepare the SQL statement
 final String sqlQuery =
        		
        		"INSERT INTO Customer " + "(CustomerName, Address , Category)"+
                        "VALUES " + "(?, ?, ?)";
                 /*
                "INSERT INTO movie_night " + 
                    "(start_time, movie_name, duration_min, guest_1, guest_2, guest_3, guest_4, guest_5) " + 
                "VALUES " + 
                "(?, ?, ?, ?, ?, ?, ?, ?)";
                */
        final PreparedStatement stmt = conn.prepareStatement(sqlQuery);

        // Replace the '?' in the above statement with the given attribute values
        stmt.setString(1, CustomerName);
        stmt.setString(2, Address);
        stmt.setInt(3, Category);

        // Execute the query, if only one record is updated, then we indicate success by returning true
        return stmt.executeUpdate() == 1;
    }
}




