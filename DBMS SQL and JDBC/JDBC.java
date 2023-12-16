import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.FileReader;
import java.util.InputMismatchException;
public class project {

    // Database credentials
    final static String HOSTNAME = "your server address";
    final static String DBNAME = "your database name";
    final static String USERNAME = "****";
    final static String PASSWORD = "*****";

    // Database connection string
    final static String URL = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;",
            HOSTNAME, DBNAME, USERNAME, PASSWORD);

    // Query templates
    final static String QUERY_TEMPLATE_1 = "INSERT INTO Customer " + 
                                           "VALUES (?, ?, ?);";

    final static String QUERY_TEMPLATE_2 = "INSERT INTO Department " + 
                                           "VALUES (?, ?);";
    final static String QUERY_TEMPLATE_3 = "EXEC THIRDQUERY @ProcessId=?, @ProcessData=?, @DepartmentNumber=?, @InsertFit=?,\r\n"
    		+ "               @FitData=?, @FitType=?, @InsertCut=?, @CutData=?, @CuttingType=?, @MachineType=?, \r\n"
    		+ "               @InsertPaint=?, @PaintData=?, @PaintType=?, @PaintingMethod=?;";
    final static String QUERY_TEMPLATE_4 = "EXEC FOURTHQUERY @AssemblyId=?,@DateOrdered=?,@AssemblyDetails=?,@CustomerName=? , @ProcessId=?;";
    final static String QUERY_TEMPLATE = "INSERT INTO Manufactured (AssemblyId, ProcessId) VALUES (?, ?);";
    final static String QUERY_TEMPLATE_5 = "EXEC FIFTHQUERY @AccountNumber = ?, @StartDate = ?,@InsertAssemblies=?,@InsertProcesses=?,@InsertDepartment=?,@ProcessId=?,@AssemblyId=?,@DepartmentNumber=?;";
    final static String QUERY_TEMPLATE_6 = "EXEC SIXTHQUERY @AssemblyId=?,@ProcessId=?,@JobNo=?,@JobStartDate=?;";
    final static String QUERY_TEMPLATE_7 = "EXEC SEVENTHQUERY @JobNo=?, @JobEndDate=?, @JobInformation=?, @InsertFitJob=?,@LaborTime=?, @InsertCutJob=?, @TypeOfMachine=?, @AmountOfTime=?, @MaterialUsed=?,@LaborTime1=? , @InsertPaintJob=?, @Color=?, @Volume=?, @LaborTime2=?;";
    final static String QUERY_TEMPLATE_8 =  "EXEC EIGHTQUERY @TranscationNo=?,@SupCost=?,@UpdateProcessAccount=?,@AccountNumber1=?,@UpdateDepartmentAccount=?,@AccountNumber2=?,@UpdateAssembliesAccount=?,@AccountNumber3=?;";
    final static String QUERY_TEMPLATE_9 = "EXEC NINETHQUERY @AssemblyId = ?;";
    final static String QUERY_TEMPLATE_10 = "EXEC TENTHQUERY @DepartmentNumber=? , @JobEndDate=?;";
    final static String QUERY_TEMPLATE_11 = "EXEC ELEVENTHQUERY @AssemblyId = ?;";
    final static String QUERY_TEMPLATE_12 = "EXEC TWELVETHQUERY @CategoryFrom=? , @CategoryTo=?;";
    final static String QUERY_TEMPLATE_13 = "EXEC THIRTEENTHQUERY @JobNoFrom=? , @JobNoTo=?;";
    final static String QUERY_TEMPLATE_14 = "EXEC FOURTHTEENQUERY @JobNo=? , @NewColor=?;";
    private static final String OUTPUT_FILE_PATH = "C:/Users/nandi/Downloads/outputfile.txt";
    // User input prompt//
    final static String PROMPT = 
            "\nPlease select one of the options below: \n" +
            "1) Insert new Customer; \n" + 
            "2) Insert new Department; \n" + 
            "3) Insert process-id and its department together with its type; \n" +
            "4) Enter a new assembly with its customer-name, assembly-details, assembly-id, and dateordered and associate it with one or more processes; \n" +
            "5)Create a new account and associate it with the process, assembly, or department;\n"+
            "6)Enter a new job, given its job-no, assembly-id, process-id, and date the job commenced;\n"+
             "7)At the completion of a job, enter the date it completed and the information relevant to the type\r\n"
             + "of job ;\n"+
             "8)Enter a transaction-no and its sup-cost and update all the costs (details) of the affected\r\n"
             + "accounts by adding sup-cost to their current values of details;\n"+
             "9)Retrieve the total cost incurred on an assembly-id;\n"+
             "10)Retrieve the total labor time within a department for jobs completed in the department during a\r\n"
             + "given date;\n"+
             "11) Retrieve the processes through which a given assembly-id has passed so far (in datecommenced order) \r\n"
             + "and the department responsible for each process;\n"+
             "12)Retrieve the customers (in name order) whose category is in a given range;\n"+
             "13)Delete all cut-jobs whose job-no is in a given range;\n"+
             "14)Change the color of a given paint job;\n"+
              "15)Import: enter new customers from a data file until the file is empty.;\n"+
              "16)  Export: Retrieve the customers (in name order) whose category is in a given range and\r\n"
              + "output them to a data file instead of screen ;\n"+
              "17)Exit";
            

    public static void main(String[] args) throws SQLException {

        System.out.println("Welcome to the sample application!");

        final Scanner sc = new Scanner(System.in); // Scanner is used to collect the user input
        String option = ""; // Initialize user option selection as nothing
        while (!option.equals("14")) { // As user for options until option 3 is selected
            System.out.println(PROMPT); // Print the available options
            option = sc.next();// Read in the user option selection
            sc.nextLine(); // Consume the newline character left by next()
 

            switch (option) { // Switch between different options
                case "1": // Insert a new student option
                    // Collect the new student data from the user
                    System.out.println("Please enter Customer Name:");
                    final String name = sc.nextLine(); // Read in the user input of student ID

                    System.out.println("Please enter Customer Address:");
                   
                    final String address = sc.nextLine(); // Read in user input of student First Name (white-spaces allowed).

                    System.out.println("Please enter Category:");
                    // No need to call nextLine extra time here, because the preceding nextLine consumed the newline character.
                    final int category = sc.nextInt(); // Read in user input of student Last Name (white-spaces allowed).
                  
                   
                    System.out.println("Connecting to the database...");
                    // Get a database connection and prepare a query statement
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_1)) {
                            // Populate the query template with the data collected from the user
                            statement.setString(1, name);
                            statement.setString(2, address);
                            statement.setInt(3, category);
                           
                            System.out.println("Dispatching the query...");
                            // Actually execute the populated query
                            final int rows_inserted = statement.executeUpdate();
                            System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                        } catch(SQLException e) {
                        	System.err.println("Error Occured: "  + e.getMessage());
                        	
                        }
                    }

                    break;
                case "2":
                	
                	System.out.println("Please enter Department Number:");
                	int departmentNo = 0;
                    try {
                          departmentNo = sc.nextInt();
                    } catch (InputMismatchException e) {
                        System.err.println("Error: Please enter a valid integer for the Department Number.");
                        break;
                    }// Read in the user input of student ID
                    sc.nextLine();
                    System.out.println("Please enter Department Data:");
                    // Preceding nextInt, nextFloar, etc. do not consume new line characters from the user input.
                    // We call nextLine to consume that newline character, so that subsequent nextLine doesn't return nothing.
                    final String deptData = sc.nextLine(); // Read in user input of student First Name (white-spaces allowed).

                    System.out.println("Connecting to the database...");
                    // Get a database connection and prepare a query statement
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (
                            final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_2)) {
                            // Populate the query template with the data collected from the user
                            statement.setInt(1, departmentNo);
                            statement.setString(2, deptData);
                            
                            System.out.println("Dispatching the query...");
                            // Actually execute the populated query
                            final int rows_inserted = statement.executeUpdate();
                            System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                        }
                        catch(SQLException e) {
                        	System.err.println("Error Occured: "  + e.getMessage());
                        	
                        }
                        
                    }

                    break;
                case "3": 
                    System.out.println("Please enter ProcessId:");
                    final int ProcessId = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Please enter ProcessData:");
                    final String ProcessData = sc.nextLine();
                    System.out.println("Please enter the DepartmentNumber which you have previously entered :");
                    final int DepartmentNumber = sc.nextInt();
                    sc.nextLine();
                    /*
                    System.out.println("Please enter the DepartmentData :");
                    final String DepartmentData = sc.nextLine();
                    */
                    System.out.println("Please type 0 or 1 to continue with the fit type insert:");
                    final int InsertFit = sc.nextInt();
                    sc.nextLine();
                    String FitData = null;
                    String FitType = null;
                    if (InsertFit == 1) {
                        System.out.println("Please enter FitData:");
                        FitData = sc.nextLine();
                        System.out.println("Please enter FitType:");
                        FitType = sc.nextLine();
                    }

                    System.out.println("Please type 0 or 1 to continue with the Cut type insert:");
                    final int InsertCut = sc.nextInt();
                    sc.nextLine();
                    String CutData = null;
                    String CuttingType = null;
                    String MachineType = null;
                    if (InsertCut == 1) {
                        System.out.println("Please enter CutData");
                        CutData = sc.nextLine();
                        System.out.println("Please enter CuttingType:");
                        CuttingType = sc.nextLine();
                        System.out.println("Please enter MachineType:");
                        MachineType = sc.nextLine();
                    }

                    System.out.println("Please type 0 or 1 to continue with the Paint type insert:");
                    final int InsertPaint = sc.nextInt();
                    sc.nextLine();
                    String PaintData = null;
                    String PaintType = null;
                    String PaintingMethod = null;
                    if (InsertPaint == 1) {
                        System.out.println("Please enter PaintData");
                        PaintData = sc.nextLine();
                        System.out.println("Please enter PaintType:");
                        PaintType = sc.nextLine();
                        System.out.println("Please enter PaintingMethod:");
                        PaintingMethod = sc.nextLine();
                    }

                    System.out.println("Connecting to the database...");
                    // Get a database connection and prepare a query statement
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_3)) {
                            // Populate the query template with the data collected from the user
                            statement.setInt(1, ProcessId);
                            statement.setString(2, ProcessData);
                            statement.setInt(3, DepartmentNumber);
                           //statement.setString(4, DepartmentData);
                            statement.setInt(4, InsertFit);
                            statement.setString(5, FitData); // These variables are now in scope
                            statement.setString(6, FitType); // These variables are now in scope
                            statement.setInt(7, InsertCut);
                            statement.setString(8, CutData); // These variables are now in scope
                            statement.setString(9, CuttingType); // These variables are now in scope
                            statement.setString(10, MachineType); // These variables are now in scope
                            statement.setInt(11, InsertPaint);
                            statement.setString(12, PaintData); // These variables are now in scope
                            statement.setString(13, PaintType); // These variables are now in scope
                            statement.setString(14, PaintingMethod); // These variables are now in scope

                            System.out.println("Dispatching the query...");
                            // Actually execute the populated query
                            final int rows_inserted = statement.executeUpdate();
                            System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                        }
                        catch(SQLException e) {
                        	System.err.println("Error Occured: "  + e.getMessage());
                        	
                        }
                    }
                    break;


                case "4": 
                	System.out.println("Please enter AssemblyId:");
                	final int AssemblyId = sc.nextInt();
                	sc.nextLine();
                	System.out.println("Please enter DateOrdered:");
                	final String DateOrdered = sc.nextLine();
                	System.out.println("Please enter AssemblyDetails:");
                	final String AssemblyDetails = sc.nextLine();
                	System.out.println("Please enter the CustomerName which you have previously entered:");
                	final String CustomerName = sc.nextLine();
                	System.out.println("How many processes do you want to enter?");
                	int numProcesses = sc.nextInt();
                	sc.nextLine(); // Consume the newline character
                	 System.out.println("Please enter the ProcessIds Which you have previously entered(separated by spaces):");
                	 String processIdsInput = sc.nextLine();
                	 String[] processIdsArray = processIdsInput.split(" ");
                	

                	

                	System.out.println("Connecting to the database...");

                	try (final Connection connection = DriverManager.getConnection(URL)) {
                	    try (final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_4 )){
                	    	 statement.setInt(1, AssemblyId);
                	            statement.setString(2, DateOrdered);
                	            statement.setString(3, AssemblyDetails);
                	            statement.setString(4, CustomerName);
                	            statement.setInt(5, Integer.parseInt(processIdsArray[0])); // Insert the first process
                	            final int rows_inserted = statement.executeUpdate();
                	        }
                	    catch(SQLException e) {
                        	System.err.println("Error Occured: "  + e.getMessage());
                        	
                        }
                	    
                	    if (numProcesses > 1) {
                            for (int i = 1; i < numProcesses; i++) {
                                try (final PreparedStatement manufacturedStatement = connection.prepareStatement(QUERY_TEMPLATE)) {
                                    manufacturedStatement.setInt(1, AssemblyId);
                                    manufacturedStatement.setInt(2, Integer.parseInt(processIdsArray[i]));
                                    final int rows_inserted = manufacturedStatement.executeUpdate();
                                }
                                catch(SQLException e) {
                                	System.err.println("Error Occured: "  + e.getMessage());
                                	
                                }
                            }
                        }
                	    

                	   
                	       

                	        
                	}
                    break;
                	

                case "5": 
                    System.out.println("Please enter AccountNumber");
                    final int AccountNumber = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Please enter  Start Date:");
                    final String AccountStartDate = sc.nextLine();
                    
                    System.out.println("Please type 0 or 1 to continue with Assemblies Account:");
                 
                    final int InsertAssemblies = sc.nextInt();
                    
                    sc.nextLine();
                    int AssemblyId11 = 0;
                    
                    if (InsertAssemblies == 1) {
                    	/*
                        System.out.println("Please enter Details3");
                        Details3 = sc.nextLine();
                        */
                    	System.out.println("Please Enter the existing AssemblyId:");
                        
                    	 AssemblyId11 = sc.nextInt();
                        
                    }
                    
                    
                    System.out.println("Please type 0 or 1 to continue with Processes Account:");
                  
                    final int InsertProcesses = sc.nextInt();
                    
                    sc.nextLine();
                    int PROCESSID = 0;
                    
                    if (InsertProcesses == 1) {
                    	/*
                        System.out.println("Please enter Details1");
                        Details1 = sc.nextLine();
                        */
                    	 System.out.println("Please Enter EXISTING Process Id:");
                         
                         PROCESSID = sc.nextInt();
                    }
                    
                   
                    System.out.println("Please type 0 or 1 to continue with the Department Account:");
                   
                    final int InsertDepartment = sc.nextInt();
                    
                    sc.nextLine();
                    int DEPARTMENTNUMBER = 0;
                   
                    if (InsertDepartment == 1) {
                    	/*
                        System.out.println("Please enter Details 2:");
                        Details2 = sc.nextLine();
                        */
                    	System.out.println("Please Enter existing Department Number:");
                        
                         DEPARTMENTNUMBER = sc.nextInt();
                    }
                   
                    
                   

                    System.out.println("Connecting to the database...");
                    // Get a database connection and prepare a query statement
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_5)) {
                            // Populate the query template with the data collected from the user
                            statement.setInt(1, AccountNumber);
                            statement.setString(2, AccountStartDate);
                            statement.setInt(3, InsertAssemblies);
                            //statement.setString(4, Details3);
                            statement.setInt(4, InsertProcesses);
                            //statement.setString(6, Details1); 
                            statement.setInt(5, InsertDepartment);
                            //statement.setString(8, Details2);
                            statement.setInt(6, PROCESSID);
                            statement.setInt(7, AssemblyId11);
                            statement.setInt(8, DEPARTMENTNUMBER);
                            
                            
                            System.out.println("Dispatching the query...");
                            // Actually execute the populated query
                            final int rows_inserted = statement.executeUpdate();
                            System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                        }
                        catch(SQLException e) {
                        	System.err.println("Error Occured: "  + e.getMessage());
                        	
                        }
                    }
                    break;
                case "6": 
                	System.out.println("Please enter AssemblyId:");
                	final int AssemblyId1 = sc.nextInt();
                	sc.nextLine();
                	/*
                	System.out.println("Please enter DateOrdered:");
                	final String DateOrdered1 = sc.nextLine();
                	System.out.println("Please enter AssemblyDetails:");
                	final String AssemblyDetails1 = sc.nextLine();
                	*/
                	 System.out.println("Please enter ProcessId:");
                     final int ProcessId1 = sc.nextInt();
                     sc.nextLine();
                     /*
                     System.out.println("Please enter ProcessData:");
                     final String ProcessData1 = sc.nextLine();
                     */
                     System.out.println("Please enter JobNo:");
                     final int JobNo = sc.nextInt();
                     sc.nextLine();
                     System.out.println("Please enter Job Start Date:");
                     final String JobStartDate = sc.nextLine();
                     

                    System.out.println("Connecting to the database...");
                    // Get a database connection and prepare a query statement
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (
                            final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_6)) {
                            // Populate the query template with the data collected from the user
                            statement.setInt(1, AssemblyId1);
                            //statement.setString(2, DateOrdered1);
                            //statement.setString(3, AssemblyDetails1);
                            statement.setInt(2, ProcessId1);
                           // statement.setString(5, ProcessData1);
                            statement.setInt(3, JobNo);
                            statement.setString(4, JobStartDate);
                            
                            System.out.println("Dispatching the query...");
                            // Actually execute the populated query
                            final int rows_inserted = statement.executeUpdate();
                            System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                        }
                        catch(SQLException e) {
                        	System.err.println("Error Occured: "  + e.getMessage());
                        	
                        }
                    }
                    break;
                case "7": 
                	System.out.println("Please enter JobNo:");
                    final int JobNo1 = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Please enter JobEndDate:");
                    final String JobEndDate = sc.nextLine();
                    
                    System.out.println("Please enter JobInformation:");
                    final String JobInformation = sc.nextLine();

                    System.out.println("Please type 0 or 1 to continue with the fit-job type insert:");
                    final int InsertFitType = sc.nextInt();
                    sc.nextLine();
                    float LaborTime = 0.0f;
                    if (InsertFitType == 1) {
                        System.out.println("Please enter LaborTime:");
                        LaborTime = sc.nextFloat();
                        
                    }

                    System.out.println("Please type 0 or 1 to continue with the Cut-job type insert:");
                    final int InsertCutjob = sc.nextInt();
                    sc.nextLine();
                    String TypeOfMachine = null;
                    float AmountOfTime = 0.0f;
                    String MaterialUsed = null;
                    float LaborTime1 = 0.0f;
                    if (InsertCutjob == 1) {
                        System.out.println("Please enter Type of Machine");
                        TypeOfMachine = sc.nextLine();
                        System.out.println("Please enter AmountOfTime:");
                        AmountOfTime = sc.nextFloat();
                        System.out.println("Please enter Material Used:");
                        MaterialUsed = sc.nextLine();
                        sc.nextLine();
                        System.out.println("Please enter Labor Time:");
                        LaborTime1 = sc.nextFloat();
                    }

                    System.out.println("Please type 0 or 1 to continue with the Paint-job type insert:");
                    final int InsertPaintJob = sc.nextInt();
                    sc.nextLine();
                    String Color = null;
                    float Volume = 0.0f;;
                    float LaborTime2 = 0.0f;
                    if (InsertPaintJob == 1) {
                        System.out.println("Please enter Color");
                        Color = sc.nextLine();
                        System.out.println("Please enter Volume:");
                        Volume = sc.nextFloat();
                        System.out.println("Please enter LaborTime:");
                        LaborTime2 = sc.nextFloat();
                    }

                    System.out.println("Connecting to the database...");
                    // Get a database connection and prepare a query statement
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_7)) {
                            // Populate the query template with the data collected from the user
                            statement.setInt(1, JobNo1);
                            statement.setString(2, JobEndDate);
                            statement.setString(3, JobInformation);
                            statement.setInt(4, InsertFitType);
                            statement.setFloat(5, LaborTime);
                            statement.setInt(6, InsertCutjob); // These variables are now in scope
                            statement.setString(7, TypeOfMachine); // These variables are now in scope
                            statement.setFloat(8, AmountOfTime);
                            statement.setString(9, MaterialUsed);
                            statement.setFloat(10, LaborTime1);// These variables are now in scope
                            statement.setInt(11, InsertPaintJob); // These variables are now in scope
                            statement.setString(12, Color); // These variables are now in scope
                            statement.setFloat(13, Volume);
                            statement.setFloat(14, LaborTime2); // These variables are now in scope
                          
                            System.out.println("Dispatching the query...");
                            // Actually execute the populated query
                            final int rows_inserted = statement.executeUpdate();
                            System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                        }
                        catch(SQLException e) {
                        	System.err.println("Error Occured: "  + e.getMessage());
                        	
                        }
                    }
                    break;
                case "8":
                    System.out.println("Please enter TranscationNo:");
                    final int TranscationNo1 = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Please enter SupCost:");
                    final int SupCost1 = sc.nextInt();
                    System.out.println("Please enter JobNo:");
                    final int JobNo2 = sc.nextInt();
                    
                    System.out.println("Connecting to the database...");
                    // Get a database connection and prepare a query statement
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (final PreparedStatement statement = connection.prepareStatement("{CALL EIGHTQUERY(?,?,?)}")) {
                            // Populate the query template with the data collected from the user
                            statement.setInt(1, TranscationNo1);
                            statement.setInt(2, SupCost1);
                            statement.setInt(3, JobNo2);
                            System.out.println("Dispatching the query...");
                            // Actually execute the populated query
                            final int rows_inserted = statement.executeUpdate();
                            System.out.println(String.format("Done. %d rows inserted.", rows_inserted));
                        }
                        catch(SQLException e) {
                        	System.err.println("Error Occured: "  + e.getMessage());
                        	
                        }
                    }
                    
                	break;
                case "9":
                    System.out.println("Please enter AssemblyId:");
                    final int AssemblyID = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Connecting to the database...");
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_9)) {
                            // Populate the query template with the data collected from the user
                            statement.setInt(1, AssemblyID);
                            System.out.println("Dispatching the query...");
                            
                            // Execute the query and store the result in a ResultSet
                            try (final ResultSet resultSet = statement.executeQuery()) {
                                System.out.println("Details 3:");

                                // Unpack the tuples returned by the database and print them out to the user
                                while (resultSet.next()) {
                                    System.out.println(resultSet.getString(1));
                                }
                            }
                        }
                        catch(SQLException e) {
                        	System.err.println("Error Occured: "  + e.getMessage());
                        	
                        }
                    }
                    break;
                case "10":
                    System.out.println("Please enter Department Number:");
                    final int DptNo = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Please enter JobEndDate:");
                    final String JobEndingDate = sc.nextLine();

                    System.out.println("Connecting to the database...");
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_10)) {
                            // Populate the query template with the data collected from the user
                            statement.setInt(1, DptNo);
                           
                            statement.setString(2, JobEndingDate);
                            System.out.println("Dispatching the query...");
                            
                            // Execute the query and store the result in a ResultSet
                            try (final ResultSet resultSet = statement.executeQuery()) {
                                System.out.println("Total Labor Time :");

                                // Unpack the tuples returned by the database and print them out to the user
                                while (resultSet.next()) {
                                	System.out.println(String.format("%s   ",
                                            resultSet.getString(1)
                                            ));
                                }
                            }
                        }
                        catch(SQLException e) {
                        	System.err.println("Error Occured: "  + e.getMessage());
                        	
                        }
                    }
                case "11":
                    System.out.println("Please enter AssemblyId:");
                    final int ASSEMBLYID = sc.nextInt();
                    

                    System.out.println("Connecting to the database...");
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_11)) {
                            // Populate the query template with the data collected from the user
                            statement.setInt(1, ASSEMBLYID);
                            
                            System.out.println("Dispatching the query...");
                            
                            // Execute the query and store the result in a ResultSet
                            try (final ResultSet resultSet = statement.executeQuery()) {
                                System.out.println("ProcessId | Department Number :");

                                // Unpack the tuples returned by the database and print them out to the user
                                while (resultSet.next()) {
                                	System.out.println(String.format("%s | %s |  ",
                                            resultSet.getString(1),
                                            resultSet.getString(2)));
                                }
                            }
                        }
                        catch(SQLException e) {
                        	System.err.println("Error Occured: "  + e.getMessage());
                        	
                        }
                    }
                    break;
                case "12":
                    System.out.println("Please enter CategoryFrom:");
                    final int CategoryFrom = sc.nextInt();
                    System.out.println("Please enter CategoryTo:");
                    final int CategoryTo = sc.nextInt();
                    

                    System.out.println("Connecting to the database...");
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_12)) {
                            // Populate the query template with the data collected from the user
                            statement.setInt(1, CategoryFrom);
                            statement.setInt(2, CategoryTo);
                            System.out.println("Dispatching the query...");
                            
                            // Execute the query and store the result in a ResultSet
                            try (final ResultSet resultSet = statement.executeQuery()) {
                            	System.out.println("CustomerName| Address");
                                

                                // Unpack the tuples returned by the database and print them out to the user
                                while (resultSet.next()) {
                                    System.out.println(String.format("%s | %s | ",
                                        resultSet.getString(1),
                                        resultSet.getString(2)));
                                }
                            }
                        }
                        catch(SQLException e) {
                        	System.err.println("Error Occured: "  + e.getMessage());
                        	
                        }
                    }
                    break;
                case "13":
                    System.out.println("Please enter JobNoFrom:");
                    final int JobNoFrom = sc.nextInt();
                    System.out.println("Please enter JobNoTo:");
                    final int JobNoTo = sc.nextInt();
                    

                    System.out.println("Connecting to the database...");
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_13)) {
                            // Populate the query template with the data collected from the user
                            statement.setInt(1, JobNoFrom);
                            statement.setInt(2, JobNoTo);
                            System.out.println("Dispatching the query...");
                            
                            // Execute the query and store the result in a ResultSet
                            final int rows_deleted = statement.executeUpdate();
                            System.out.println(String.format("Done. %d rows deleted.", rows_deleted));
                                }
                        catch(SQLException e) {
                        	System.err.println("Error Occured: "  + e.getMessage());
                        	
                        }
                            }
                    break;
                case "14":
                    System.out.println("Please enter Job Number:");
                    final int JobNO = sc.nextInt();
                    sc.nextLine();
                    System.out.println("Please enter New Color:");
                    final String NewColor = sc.nextLine();
                    

                    System.out.println("Connecting to the database...");
                    try (final Connection connection = DriverManager.getConnection(URL)) {
                        try (final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_14)) {
                            // Populate the query template with the data collected from the user
                            statement.setInt(1, JobNO);
                            statement.setString(2, NewColor);
                            System.out.println("Dispatching the query...");
                            
                            // Execute the query and store the result in a ResultSet
                            final int Updated = statement.executeUpdate();
                            System.out.println(String.format("Done. %d rows Updated.", Updated));
                                }
                        catch(SQLException e) {
                        	System.err.println("Error Occured: "  + e.getMessage());
                        	
                        }
                            }
                        break;
                case "15":
                    System.out.println("Please enter the input text file");
                    String fileName = sc.nextLine();
               
                    System.out.println("Please enter CategoryFrom:");
                    final int CategoryFrom1 = sc.nextInt();
                    System.out.println("Please enter CategoryTo:");
                    final int CategoryTo1 = sc.nextInt();
                    System.out.println("Connecting to the database...");
                    try (final Connection connection = DriverManager.getConnection(URL);
                         final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_12);
                         PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {

                        // Populate the query template with the data collected from the user
                        statement.setInt(1, CategoryFrom1);
                        statement.setInt(2, CategoryTo1);
                        System.out.println("Dispatching the query...");

                        // Execute the query and store the result in a ResultSet
                        try (final ResultSet resultSet = statement.executeQuery()) {
                            System.out.println("CustomerNames:");
                            while (resultSet.next()) {
                                String customerName = resultSet.getString(1);
                                String Address = resultSet.getString(2);
                                
                                writer.println(customerName);
                                writer.println(Address);
                                
                                System.out.println(customerName);
                                System.out.println(Address);
                                
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "16":
                    System.out.println("Please enter the input text file");
                     String inputFileName = sc.nextLine();
                    try (Scanner fileScanner = new Scanner(new FileReader(inputFileName))) {
                        while (fileScanner.hasNext()) {
                            // Assuming each line in the file contains data for a new record
                            String[] data = fileScanner.nextLine().split(",");
                            if (data.length == 3) {
                                // Populate the query template with data from the file
                                try (final Connection connection = DriverManager.getConnection(URL);
                                     final PreparedStatement statement = connection.prepareStatement(QUERY_TEMPLATE_1)) {
                                    statement.setString(1, data[0].trim());
                                    statement.setString(2, data[1].trim());
                                    statement.setInt(3, Integer.parseInt(data[2].trim()));
                                    final int rowsInserted = statement.executeUpdate();
                                    System.out.println(String.format("Done. %d rows inserted.", rowsInserted));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                System.out.println("Invalid data format in the input file.");
                            }
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("Input file not found.");
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "17": // Do nothing, the while loop will terminate upon the next iteration
                    System.out.println("Exiting! Good-buy!");
                    break;
                default: // Unrecognized option, re-prompt the user for the correct one
                    System.out.println(String.format(
                        "Unrecognized option: %s\n" + 
                        "Please try again!", 
                        option));
                    break;
                
            
                                    







                        
                    

                    
                    



                    
                    

                    
                    
            }
        }

        sc.close(); // Close the scanner before exiting the application
    }
}
