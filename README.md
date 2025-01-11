# Library Management System

A Java application with GUI for managing library books. Load books from a text file, sort by price, search by year, and calculate average prices.

## Setup

1. Create project folders and files:
```
library-management/
├── src/
│   ├── Book.java
│   └── LibraryManager.java
└── books.txt
```

2. Format for books.txt:
```
ID;Title;Author;Year;Price
Example:
1;L'Alchimiste;Paulo Coelho;1988;15.0
```

## Compilation & Running

```bash
javac src/*.java
java -cp src LibraryManager
```

## Features

- Load books from text file ("Charger Fichier")
- Sort books by price ("Trier par Prix")
- Search by publication year ("Rechercher après année")
- View average book price
- Table display of all books

## Requirements

- Java JDK 8 or higher
- Text editor or IDE
![image](https://github.com/user-attachments/assets/7e259a24-64fc-4a33-b5c0-84ec6d102b7d)
