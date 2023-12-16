--1st query
CREATE PROCEDURE FIRSTQUERY
    @CustomerName VARCHAR(50),
    @Address VARCHAR(100),
    @Category INT 
AS
BEGIN
    INSERT INTO Customers (CustomerName, Address, Category)
    VALUES (@CustomerName, @Address, @Category);
END;

--2nd query
CREATE PROCEDURE SECONDQUERY
    @DepartmentNumber INT,
    @DepartmentData VARCHAR(200)
    
AS
BEGIN
    INSERT INTO Customers (DepartmentNumber,  DepartmentData)
    VALUES (@DepartmentNumber, @DepartmentData);
END;


--3rd query
DROP PROCEDURE THIRDQUERY
CREATE PROCEDURE THIRDQUERY
    @ProcessId INT,
    @ProcessData NVARCHAR(MAX),
    @DepartmentNumber INT,
   -- @DepartmentData NVARCHAR(MAX),
    @InsertFit BIT,
    @FitData NVARCHAR(MAX), -- Corrected: Added data type
    @FitType NVARCHAR(100),
    @InsertCut BIT,
    @CutData NVARCHAR(MAX),
    @CuttingType NVARCHAR(MAX),
    @MachineType NVARCHAR(MAX),
    @InsertPaint BIT,
    @PaintData NVARCHAR(MAX),
    @PaintType NVARCHAR(100),
    @PaintingMethod NVARCHAR(300)
  
AS
BEGIN
SET NOCOUNT ON;
    BEGIN TRY
        BEGIN TRANSACTION;
    -- Insert into Processes table
    INSERT INTO Processes (ProcessId, ProcessData)
    VALUES (@ProcessId, @ProcessData);


    -- Insert into Department table
    --INSERT INTO Department (DepartmentNumber, DepartmentData)
    --VALUES (@DepartmentNumber, @DepartmentData);
    

    -- Insert into Supervised table
    INSERT INTO Supervised (ProcessId, DepartmentNumber)
    VALUES (@ProcessId, @DepartmentNumber);

    -- Conditionally insert into Fit table
    IF @InsertFit = 1
    BEGIN
        INSERT INTO Fit (ProcessId, FitData, FitType)
        VALUES (@ProcessId, @FitData, @FitType);
    END

    -- Conditionally insert into Cut table
    IF @InsertCut = 1
    BEGIN
        INSERT INTO Cut (ProcessId , CutData, CuttingType, MachineType)
        VALUES (@ProcessId , @CutData, @CuttingType, @MachineType);
    END

    -- Conditionally insert into Paint table
    IF @InsertPaint = 1
    BEGIN
        INSERT INTO Paint (ProcessId , PaintData, PaintType, PaintingMethod)
        VALUES (@ProcessId ,@PaintData, @PaintType, @PaintingMethod);
    END
    COMMIT;
    END TRY
    BEGIN CATCH
        -- An error occurred, roll back the transaction
        IF @@TRANCOUNT > 0
            ROLLBACK;

        -- Raise the error
        THROW;
    END CATCH;
END;

--4th query
 DROP PROCEDURE FOURTHQUERY
 CREATE PROCEDURE FOURTHQUERY 
    @AssemblyId INT,
    @DateOrdered DATE,
    @AssemblyDetails NVARCHAR(MAX),
    @CustomerName NVARCHAR(MAX),
    @ProcessId INT 
AS
BEGIN
SET NOCOUNT ON;
    BEGIN TRY
        BEGIN TRANSACTION;
    -- Insert into Assemblies table
    INSERT INTO Assemblies (AssemblyId, DateOrdered, AssemblyDetails)
    VALUES (@AssemblyId, @DateOrdered, @AssemblyDetails);
     
     
    -- Insert into Customer table
    --INSERT INTO Customer (CustomerName)
    --VALUES (@CustomerName);
    

    -- Insert into Orders table
    INSERT INTO Orders (CustomerName, AssemblyId)
    VALUES (@CustomerName, @AssemblyId);

    -- Insert into Manufactured table
    INSERT INTO Manufactured (AssemblyId, ProcessId)
    VALUES (@AssemblyId, @ProcessId);
    COMMIT;
    END TRY
    BEGIN CATCH
        -- An error occurred, roll back the transaction
        IF @@TRANCOUNT > 0
            ROLLBACK;

        -- Raise the error
        THROW;
    END CATCH;
END;

--5 th query
DROP PROCEDURE FIFTHQUERY
CREATE PROCEDURE FIFTHQUERY
    @AccountNumber INT,
    @StartDate DATE,
    --@Details3 NVARCHAR(MAX),
    --@Details1 NVARCHAR(MAX),
    --@Details2 NVARCHAR(MAX),
    @InsertDepartment INT = 0,
    @InsertProcesses INT = 0,
    @InsertAssemblies INT = 0,
    @AssemblyId INT,
    @ProcessId INT,
    @DepartmentNumber INT
AS
BEGIN
SET NOCOUNT ON;
    BEGIN TRY
        BEGIN TRANSACTION;
    -- Insert into Account table
    INSERT INTO Account (AccountNumber, StartDate)
    VALUES (@AccountNumber, @StartDate);

    IF @InsertAssemblies = 1
    BEGIN
        INSERT INTO AssembliesAccount (AccountNumber, Details3)
        VALUES (@AccountNumber, '0');
        INSERT INTO AssemblyMaintain(AssemblyId , AccountNumber)
        VALUES (@AssemblyId , @AccountNumber)
    END

    IF @InsertProcesses = 1
    BEGIN
        INSERT INTO ProcessAccount (AccountNumber, Details1)
        VALUES (@AccountNumber, '0');
        INSERT INTO ProcessMaintain(ProcessId , AccountNumber)
        VALUES (@ProcessId , @AccountNumber)
    END

    IF @InsertDepartment = 1
    BEGIN
        INSERT INTO DepartmentAccount (AccountNumber, Details2)
        VALUES (@AccountNumber, '0');
        INSERT INTO DepartmentMaintain(DepartmentNumber , AccountNumber)
        VALUES (@DepartmentNumber , @AccountNumber)
    END
    COMMIT;
    END TRY
    BEGIN CATCH
        -- An error occurred, roll back the transaction
        IF @@TRANCOUNT > 0
            ROLLBACK;

        -- Raise the error
        THROW;
    END CATCH;
END;

--6 TH QUERY
DROP PROCEDURE SIXTHQUERY
CREATE PROCEDURE SIXTHQUERY
    @AssemblyId INT,
    --@DateOrdered DATE,
    --@AssemblyDetails NVARCHAR(MAX),
    @ProcessId INT,
    --@ProcessData NVARCHAR(MAX),
    @JobNo INT,
    @JobStartDate DATE
AS
BEGIN
SET NOCOUNT ON;
    BEGIN TRY
        BEGIN TRANSACTION;
    -- Insert into Job table
    INSERT INTO Job (JobNo, JobStartDate)
    VALUES (@JobNo, @JobStartDate);

    -- Insert into Assign table
    INSERT INTO Assign (JobNo, AssemblyId, ProcessId)
    VALUES (@JobNo, @AssemblyId, @ProcessId);
    COMMIT;
    END TRY
    BEGIN CATCH
        -- An error occurred, roll back the transaction
        IF @@TRANCOUNT > 0
            ROLLBACK;

        -- Raise the error
        THROW;
    END CATCH;
END;

EXEC SEVENTHQUERY @JobNo=?, @JobEndDate=?, @JobInformation=?, @InsertFitJob=?,@LaborTime=?, @InsertCutJob=?, @TypeOfMachine=?, @AmountOfTime=?, @MaterialUsed=?,@LaborTime1=? , @InsertPaintJob=?, @Color=?, @Volume=?, @LaborTime2=?;
--7th query
DROP PROCEDURE SEVENTHQUERY
CREATE PROCEDURE SEVENTHQUERY
    @JobNo INT,
    @JobEndDate NVARCHAR(10),
    @JobInformation NVARCHAR(300),
    @InsertFitJob BIT,
    @LaborTime FLOAT,
    @InsertCutJob BIT,
    @TypeOfMachine NVARCHAR(100),
    @AmountOfTime FLOAT,
    @MaterialUsed NVARCHAR(100),
    @LaborTime1 FLOAT,
    @InsertPaintJob BIT,
    @Color NVARCHAR(255),
    @Volume FLOAT,
    @LaborTime2 FLOAT
AS
BEGIN
SET NOCOUNT ON;
    BEGIN TRY
        BEGIN TRANSACTION;
    -- Check if JobNo exists
    IF NOT EXISTS (SELECT 1 FROM Job WHERE JobNo = @JobNo)
    BEGIN
        -- Raise an error because JobNo doesn't exist
        THROW 51000, 'The specified JobNo does not exist.', 1;
        RETURN; -- Terminate the procedure
    END

    -- Update Job
    UPDATE Job
    SET JobEndDate = @JobEndDate,
    JobInformation = @JobInformation
    WHERE JobNo = @JobNo;

    IF @InsertCutJob = 1
    BEGIN
        INSERT INTO CutJob (JobNo,MachineType, AmountOfTime, MaterialUsed, LaborTime)
        VALUES (@JobNo,@TypeOfMachine, @AmountOfTime, @MaterialUsed, @LaborTime1);
    END

    IF @InsertPaintJob = 1
    BEGIN
        INSERT INTO PaintJob (JobNo,Color, Volume, LaborTime)
        VALUES (@JobNo,@Color, @Volume, @LaborTime2);
    END

    IF @InsertFitJob = 1
    BEGIN
        INSERT INTO FitJob (JobNo,LaborTime)
        VALUES (@JobNo,@LaborTime);
    END
    COMMIT;
    END TRY
    BEGIN CATCH
        -- An error occurred, roll back the transaction
        IF @@TRANCOUNT > 0
            ROLLBACK;

        -- Raise the error
        THROW;
    END CATCH;
END;


CREATE PROCEDURE EIGHTQUERY
    @TransactionNo INT,
    @SupCost INT,
    @JobNo INT
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        BEGIN TRANSACTION
            DECLARE @AssignedProcessID INT;
            DECLARE @AssignedAssemblyID INT;
            DECLARE @AssignedDepartmentID INT;
            DECLARE @PAccountID INT;
            DECLARE @AAccountID INT;
            DECLARE @DAccountID INT;

            SELECT 
                @AssignedProcessID = A.ProcessId, 
                @AssignedAssemblyID = A.AssemblyId, 
                @AssignedDepartmentID = S.DepartmentNumber
            FROM 
                Assign A
            INNER JOIN 
                Supervised S ON A.ProcessId = S.ProcessId
            WHERE 
                A.JobNo = @JobNo;

            SELECT @PAccountID = AccountNumber FROM ProcessMaintain WHERE ProcessId = @AssignedProcessID;
            SELECT @AAccountID = AccountNumber FROM AssemblyMaintain WHERE AssemblyId = @AssignedAssemblyID;
            SELECT @DAccountID = AccountNumber FROM DepartmentMaintain WHERE DepartmentNumber = @AssignedDepartmentID;

            
            INSERT INTO Transactions (TransactionNo, SupCost)
            VALUES (@TransactionNo, @SupCost);

            INSERT INTO Records (JobNo, TransactionNo)
            VALUES (@TransactionNo, @JobNo)
            
            INSERT INTO Updates (AccountNumber, TransactionNo)
            VALUES
            (@PAccountID, @TransactionNo),
            (@AAccountID, @TransactionNo),
            (@DAccountID, @TransactionNo);

            UPDATE AssembliesAccount SET Details3 += @SupCost WHERE AccountNumber = @AAccountID;
            UPDATE DepartmentAccount SET Details2 += @SupCost WHERE AccountNumber = @DAccountID;
            UPDATE ProcessAccount SET Details1 += @SupCost WHERE AccountNumber = @PAccountID;
        COMMIT;
    END TRY
    BEGIN CATCH
        IF @@TRANCOUNT > 0
            ROLLBACK;
        THROW;
    END CATCH;
END;

--9 th query
CREATE PROCEDURE NINETHQUERY
AS
BEGIN
   SELECT aa.Details3 FROM AssembliesAccount AS aa , Assemblies AS a , AssemblyMaintain AS m 
   WHERE m.AssemblyId = a.AssemblyId AND m.AccountNumber = aa.AccountNumber
END;

--9th query with assembly id as parameter
DROP PROCEDURE NINETHQUERY
CREATE PROCEDURE NINETHQUERY
    @AssemblyId INT
AS
BEGIN
    DECLARE @AccountNumber INT;

    -- Retrieve AccountNumber based on the provided AssemblyId
    SELECT @AccountNumber = m.AccountNumber
    FROM Assemblies AS a
    INNER JOIN AssemblyMaintain AS m ON m.AssemblyId = a.AssemblyId
    WHERE a.AssemblyId = @AssemblyId;

    -- If @AccountNumber is NULL, it means there was no matching record
    IF @AccountNumber IS NOT NULL
    BEGIN
        -- Now you have the AccountNumber, and you can use it to retrieve Details3
        SELECT aa.Details3
        FROM AssembliesAccount AS aa
        WHERE aa.AccountNumber = @AccountNumber;
    END
    ELSE
    BEGIN
        -- Handle the case when there is no matching record
        PRINT 'No matching record found for the provided AssemblyId.';
    END
END;

--10 th query
DROP PROCEDURE TENTHQUERY
CREATE PROCEDURE TENTHQUERY
    @DepartmentNumber INT,
    @JobEndDate DATE
AS
BEGIN
SELECT SUM(jt.LaborTime) as 'Total Labor Time' FROM Assign AS a , Job AS j, Processes AS p , Supervised AS s , Department AS d,
(
    SELECT JobNo, LaborTime FROM CutJob
    UNION
    SELECT JobNo, LaborTime FROM FitJob
    UNION
    SELECT JobNo, LaborTime FROM PaintJob
) as jt

WHERE p.ProcessId = a.ProcessId AND a.JobNo = jt.JobNo AND s.ProcessId = p.ProcessId AND 
s.DepartmentNumber = d.DepartmentNumber and d.DepartmentNumber = @Departmentnumber and j.JobEndDate = @JobEndDate
    
END;

EXEC TENTHQUERY @DepartmentNumber=2020,@JobEndDate='2023-11-13'

DROP Procedure Query10;
--11 th query
CREATE PROCEDURE ELEVENTHQUERY
    @AssemblyId INT
AS
BEGIN
    IF EXISTS (SELECT 1 FROM Assemblies WHERE AssemblyId = @AssemblyId)
    BEGIN
        SELECT p.ProcessId , d.DepartmentNumber  FROM Processes AS p , Assemblies AS a ,
         Department AS d , Manufactured AS m , Supervised AS s
        WHERE m.AssemblyId = a.AssemblyId and m.ProcessId = p.ProcessId AND s.ProcessId = p.ProcessId and
         s.DepartmentNumber = d.DepartmentNumber AND a.AssemblyId = @AssemblyId
        ORDER BY a.DateOrdered
    END
    ELSE
    BEGIN
        THROW 51000, 'The AssemblyId does not exist.', 1;
    END
END;

--12 TH TH QUERY
DROP PROCEDURE TWELVETHQUERY
CREATE PROCEDURE TWELVETHQUERY
    @CategoryFrom INT,
    @CategoryTo INT
AS
BEGIN
    SELECT CustomerName , Address 
    FROM Customer
    WHERE Category >= @CategoryFrom AND Category <= @CategoryTo
    ORDER BY CustomerName;
END;

--13 th query
DROP PROCEDURE THIRTEENTHQUERY
CREATE PROCEDURE THIRTEENTHQUERY
    @JobNoFrom INT,
    @JobNoTo INT
AS
BEGIN
    IF EXISTS (SELECT 1 FROM CutJob WHERE JobNo BETWEEN @JobNoFrom AND @JobNoTo)
    BEGIN
    
    DELETE FROM CutJob
    WHERE JobNo BETWEEN @JobNoFrom AND @JobNoTo;
    END
    ELSE
    BEGIN
    THROW 51000, 'No matching CutJobs found for the specified JobNo range.', 1;
    END
END;

--14 th query
CREATE PROCEDURE FOURTHTEENQUERY
@JobNo INT,
@NewColor VARCHAR(20)
AS
BEGIN 
    IF EXISTS (SELECT 1 FROM PaintJob WHERE JobNo = @JobNo)
    BEGIN
     UPDATE PaintJob
     SET Color = @NewColor
     WHERE JobNo = @JobNo
    END
    ELSE
    BEGIN
    THROW 51000, 'The JobNo does not exist in the paint-table.', 1;
    END
END;


SELECT * FROM Customer
SELECT * FROM processes
SELECT * FROM Cut 
SELECT * FROM Paint 
SELECT * FROM Fit
SELECT * FROM Supervised
SELECT * FROM Assemblies
SELECT * FROM Manufactured
SELECT * FROM Orders
SELECT * FROM Account
SELECT * FROM ProcessAccount
SELECT * FROM ProcessMaintain
SELECT * FROM AssembliesAccount
SELECT * FROM AssemblyMaintain
SELECT * FROM DepartmentAccount
SELECT * FROM DepartmentMaintain
SELECT * FROM Assign
SELECT * FROM job
SELECT * FROM CutJob
SELECT * FROM PaintJob
SELECT * FROM FitJob
SELECT * FROM Transactions
SELECT * FROM AssembliesAccount
SELECT * FROM ProcessAccount
SELECT * FROM DepartmentAccount
SELECT * FROM Updates
