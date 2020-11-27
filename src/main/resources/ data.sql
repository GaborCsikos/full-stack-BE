create TABLE TODO (
    ID IDENTITY NOT NULL AUTO_INCREMENT,
    NAME VARCHAR(64) NOT NULL,
    DESCRIPTION VARCHAR(255),
    PRIORITY VARCHAR(16),
    CREATED_DATE DATE NOT NULL,
    UPDATED_DATE DATE NOT NULL,
    CONSTRAINT TODO_PK PRIMARY KEY (ID)
);