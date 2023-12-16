DROP TABLE Customer , Assemblies , Orders , Processes , Manufactured , Fit , Paint ,  Cut , Department , Supervised , Job ,Assign,CutJob,PaintJob,FitJob
,FitJob , Transactions , Records , Account , Updates , ProcessAccount , DepartmentAccount , AssembliesAccount , ProcessMaintain , DepartmentMaintain , AssemblyMaintain


CREATE TABLE Customer (
    CustomerName VARCHAR(255) PRIMARY KEY,
    Address VARCHAR(255),
    Category INT
);

CREATE INDEX CustomerIndex ON Customer(Category);

CREATE TABLE Assemblies (
    AssemblyId INT PRIMARY KEY,
    DateOrdered DATE,
    AssemblyDetails TEXT
);
CREATE INDEX AssembliesIndex ON Assemblies(DateOrdered);

CREATE TABLE Orders (
    AssemblyId INT,
    CustomerName VARCHAR(255),
    PRIMARY KEY (AssemblyId),
    FOREIGN KEY (AssemblyId) REFERENCES Assemblies(AssemblyId),
    FOREIGN KEY (CustomerName) REFERENCES Customer(CustomerName)
);


CREATE TABLE Processes (
    ProcessId INT PRIMARY KEY,
    ProcessData TEXT
);

CREATE TABLE Manufactured (
    AssemblyId INT,
    ProcessId INT,
    PRIMARY KEY (AssemblyId, ProcessId),
    FOREIGN KEY (AssemblyId) REFERENCES Assemblies(AssemblyId),
    FOREIGN KEY (ProcessId) REFERENCES Processes(ProcessId)
);

CREATE INDEX Manufactured_hashing_index
ON Manufactured (AssemblyId)

CREATE TABLE Fit (
    ProcessId INT,
    FitData TEXT,
    FitType VARCHAR(100),
    PRIMARY KEY (ProcessId),
    FOREIGN KEY (ProcessId) REFERENCES Processes(ProcessId)
);

CREATE TABLE Paint (
    ProcessId INT,
    PaintData TEXT,
    PaintType VARCHAR(100),
    PaintingMethod VARCHAR(100),
    PRIMARY KEY (ProcessId),
    FOREIGN KEY (ProcessId) REFERENCES Processes(ProcessId)
);

CREATE TABLE Cut (
    ProcessId INT,
    CutData TEXT,
    CuttingType VARCHAR(255),
    MachineType VARCHAR(255),
    PRIMARY KEY (ProcessId),
    FOREIGN KEY (ProcessId) REFERENCES Processes(ProcessId)
);

CREATE TABLE Department (
    DepartmentNumber INT PRIMARY KEY,
    DepartmentData TEXT
);

CREATE TABLE Supervised (
    ProcessId INT,
    DepartmentNumber INT,
    PRIMARY KEY (ProcessId),
    FOREIGN KEY (ProcessId) REFERENCES Processes(ProcessId),
    FOREIGN KEY (DepartmentNumber) REFERENCES Department(DepartmentNumber)
);

CREATE INDEX Supervised_hashing_index
ON Supervised (ProcessId);


CREATE TABLE Job (
    JobNo INT PRIMARY KEY,
    JobStartDate DATE,
    JobEndDate DATE,
    JobInformation TEXT
);

CREATE INDEX Job_BTree
ON Job (JobNo);


CREATE TABLE Assign (
    JobNo INT,
    AssemblyId INT,
    ProcessId INT,
    PRIMARY KEY (JobNo),
    FOREIGN KEY (JobNo) REFERENCES Job(JobNo),
    FOREIGN KEY (AssemblyId) REFERENCES Assemblies(AssemblyId),
    FOREIGN KEY (ProcessId) REFERENCES Processes(ProcessId)
);

DROP TABLE CutJob;

CREATE TABLE CutJob (
    JobNo INT PRIMARY KEY,
    MachineType VARCHAR(255),
    AmountOfTime FLOAT,
    MaterialUsed VARCHAR(255),
    LaborTime FLOAT,
    FOREIGN KEY (JobNo) REFERENCES Job(JobNo)
);

CREATE INDEX CutJob_BTreeindex
ON CutJob (JobNo);


DROP TABLE PaintJob
CREATE TABLE PaintJob (
    JobNo INT PRIMARY KEY,
    LaborTime FLOAT,
    Color VARCHAR(255),
    Volume FLOAT,
    FOREIGN KEY (JobNo) REFERENCES Job(JobNo)
);

CREATE INDEX PaintJob_BTreeindex
ON PaintJob (JobNo);


DROP TABLE FitJob;

CREATE TABLE FitJob (
    JobNo INT PRIMARY KEY,
    LaborTime FLOAT,
    FOREIGN KEY (JobNo) REFERENCES Job(JobNo)
);

CREATE TABLE Transactions (
    TransactionNo INT PRIMARY KEY,
    SupCost DECIMAL(10, 2)
);

CREATE TABLE Records (
    TransactionNo INT,
    JobNo INT,
    PRIMARY KEY (TransactionNo),
    FOREIGN KEY (TransactionNo) REFERENCES Transactions(TransactionNo),
    FOREIGN KEY (JobNo) REFERENCES Job(JobNo)
);

CREATE TABLE Account (
    AccountNumber INT PRIMARY KEY,
    StartDate DATE
);

CREATE INDEX Account_hashing
ON Account(AccountNumber)

CREATE TABLE Updates (
    AccountNumber INT,
    TransactionNo INT,
    PRIMARY KEY (AccountNumber, TransactionNo),
    FOREIGN KEY (AccountNumber) REFERENCES Account(AccountNumber),
    FOREIGN KEY (TransactionNo) REFERENCES Transactions(TransactionNo)
);

CREATE TABLE ProcessAccount (
    AccountNumber INT,
    Details1 TEXT,
    PRIMARY KEY (AccountNumber),
    FOREIGN KEY (AccountNumber) REFERENCES Account(AccountNumber)
);

CREATE TABLE DepartmentAccount (
    AccountNumber INT,
    Details2 TEXT,
    PRIMARY KEY (AccountNumber),
    FOREIGN KEY (AccountNumber) REFERENCES Account(AccountNumber)
);

CREATE TABLE AssembliesAccount (
    AccountNumber INT,
    Details3 TEXT,
    PRIMARY KEY (AccountNumber),
    FOREIGN KEY (AccountNumber) REFERENCES Account(AccountNumber)
);

CREATE INDEX AssembliesAccount_hashing
ON AssembliesAccount(AccountNumber);


CREATE TABLE ProcessMaintain (
    ProcessId INT PRIMARY KEY,
    AccountNumber INT,
    FOREIGN KEY (ProcessId) REFERENCES Processes(ProcessId),
    FOREIGN KEY (AccountNumber) REFERENCES ProcessAccount(AccountNumber)
);

CREATE INDEX ProcessMantain_hashing
ON ProcessMaintain (ProcessId)

CREATE TABLE DepartmentMaintain (
    DepartmentNumber INT PRIMARY KEY,
    AccountNumber INT,
    FOREIGN KEY (DepartmentNumber) REFERENCES Department(DepartmentNumber),
    FOREIGN KEY (AccountNumber) REFERENCES DepartmentAccount(AccountNumber)
);

CREATE INDEX DepartmentMaintain_hashing
ON DepartmentMaintain ( DepartmentNumber) 

CREATE TABLE AssemblyMaintain (
    AssemblyId INT PRIMARY KEY,
    AccountNumber INT,
    FOREIGN KEY (AssemblyId) REFERENCES Assemblies(AssemblyId),
    FOREIGN KEY (AccountNumber) REFERENCES AssembliesAccount(AccountNumber)
);

CREATE INDEX AssembliesMaintains_hashing
ON AssemblyMaintain (AssemblyId)


