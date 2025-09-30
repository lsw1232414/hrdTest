proj1CREATE DATABASE proj1;

CREATE TABLE Book (
    BookID     INT PRIMARY KEY AUTO_INCREMENT,
    Title      VARCHAR(200) NOT NULL,
    Author     VARCHAR(100),
    Publisher  VARCHAR(100),
    Price      INT UNSIGNED,             
    PubYear    YEAR                  
);

CREATE TABLE Rental (
    RentalID   INT PRIMARY KEY AUTO_INCREMENT,
    MemberID   INT,
    BookID     INT,
    RentDate   DATE,
    ReturnDate DATE NULL,
    FOREIGN KEY (MemberID) REFERENCES Member(MemberID),
    FOREIGN KEY (BookID) REFERENCES Book(BookID)
);

CREATE TABLE Member (
    MemberID   INT PRIMARY KEY AUTO_INCREMENT,
    Name       VARCHAR(100) NOT NULL,
    Phone      VARCHAR(20),
    Address    VARCHAR(200)
);

SELECT *FROM book where PubYear >= 2020;

SELECT Book.Title
FROM Rental
JOIN Member ON Rental.MemberID = Member.MemberID
JOIN Book   ON Rental.BookID = Book.BookID
WHERE Member.Name = '홍길동';

SELECT book.Title
FROM rental
JOIN book ON rental.BookID = book.BookID
WHERE rental.ReturnDate IS NULL;

SELECT book.title,COUNT(rental.RentDate) AS RentalCount
FROM book   
JOIN rental ON book.Bookid = rental.Bookid
GROUP BY Book.BookID, Book.title;

SELECT *
FROM Book
ORDER BY Price DESC
LIMIT 1;



