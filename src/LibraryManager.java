import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.util.*;

class Book {
    private int id;
    private String title;
    private String author;
    private int year;
    private double price;

    public Book(int id, String title, String author, int year, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.price = price;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public double getPrice() { return price; }

    @Override
    public String toString() {
        return String.format("%d;%s;%s;%d;%.1f", id, title, author, year, price);
    }

    public Object[] toTableRow() {
        return new Object[]{id, title, author, year, price};
    }
}

public class LibraryManager extends JFrame {
    private ArrayList<Book> Books;
    private JTable BookTable;
    private DefaultTableModel tableModel;
    private JTextField yearField;
    private JLabel averagePriceLabel;

    public LibraryManager() {
        this.Books = new ArrayList<>();
        setupGUI();
    }

    private void setupGUI() {
        setTitle("Gestion de Bibliothèque");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Create table model with columns
        String[] columns = {"ID", "Titre", "Auteur", "Année", "Prix"};
        tableModel = new DefaultTableModel(columns, 0);
        BookTable = new JTable(tableModel);

        // Control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        // Load file button
        JButton loadButton = new JButton("Charger Fichier");
        loadButton.addActionListener(e -> loadBooksFromFile());

        // Sort by price button
        JButton sortButton = new JButton("Trier par Prix");
        sortButton.addActionListener(e -> TrierParPrix());

        // Year search panel
        JPanel yearPanel = new JPanel();
        yearField = new JTextField(4);
        JButton searchButton = new JButton("Rechercher après année");
        searchButton.addActionListener(e -> ChercherParDate());
        yearPanel.add(new JLabel("Année:"));
        yearPanel.add(yearField);
        yearPanel.add(searchButton);

        // Average price label
        averagePriceLabel = new JLabel("Prix moyen: 0.00€");

        // Add components to control panel
        controlPanel.add(loadButton);
        controlPanel.add(sortButton);
        controlPanel.add(yearPanel);
        controlPanel.add(averagePriceLabel);

        // Add components to frame
        add(new JScrollPane(BookTable), BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        // Set frame properties
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void loadBooksFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (BufferedReader br = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                Books.clear();
                tableModel.setRowCount(0);

                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(";");
                    if (parts.length == 5) {
                        Book Book = new Book(
                                Integer.parseInt(parts[0]),
                                parts[1],
                                parts[2],
                                Integer.parseInt(parts[3]),
                                Double.parseDouble(parts[4])
                        );
                        System.out.println((Book));
                        Books.add(Book);
                        tableModel.addRow(Book.toTableRow());
                    }
                }
                updateAveragePrice();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this,
                        "Erreur de lecture du fichier: " + e.getMessage(),
                        "Erreur",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void TrierParPrix() {
        ArrayList<Book> sortedBooks = new ArrayList<>(Books);
        sortedBooks.sort((b1, b2) -> Double.compare(b1.getPrice(), b2.getPrice()));

        tableModel.setRowCount(0);
        for (Book Book : sortedBooks) {
            tableModel.addRow(Book.toTableRow());
        }
    }

    private void ChercherParDate() {
        try {
            int year = Integer.parseInt(yearField.getText());
            tableModel.setRowCount(0);
            for (Book Book : Books) {
                if (Book.getYear() > year) {
                    tableModel.addRow(Book.toTableRow());
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez entrer une année valide",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateAveragePrice() {
        if (!Books.isEmpty()) {
            double average = Books.stream()
                    .mapToDouble(Book::getPrice)
                    .average()
                    .orElse(0.0);
            averagePriceLabel.setText(String.format("Prix moyen: %.2f DH", average));
        } else {
            averagePriceLabel.setText("Prix moyen: 0.00 DH");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibraryManager manager = new LibraryManager();
            manager.setVisible(true);
        });
    }
}