	import javax.swing.*;
	import javax.swing.border.*;
	import java.awt.*;
	import java.awt.event.*;
	import java.sql.*;
	
	public class DynamicStudentReportCardGenerator extends JFrame {
	    private static final long serialVersionUID = 1L;
	
	    private JPanel mainPanel, inputPanel, reportPanel;
	    private JTextField idField, firstNameField, surnameField;
	    private JTextField[] marksFields;
	    
	    private CardLayout cardLayout;
	    private static final String[] SUBJECTS = {"NPS", "AAOP", "AADS", "OS", "FSD", "ASE"};
	    
	    // Enhanced color scheme
	    private static final Color PRIMARY_COLOR = new Color(63, 81, 181);  // Material Indigo
	    private static final Color ACCENT_COLOR = new Color(255, 87, 34);   // Material Deep Orange
	    private static final Color BACKGROUND_COLOR = new Color(250, 250, 250);
	    private static final Color CARD_COLOR = new Color(255, 255, 255);
	    private static final Color TEXT_PRIMARY = new Color(33, 33, 33);
	    private static final Color TEXT_SECONDARY = new Color(117, 117, 117);
	    
	    // Enhanced fonts
	    private static final Font TITLE_FONT = new Font("+", Font.BOLD, 28);
	    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 20);
	    private static final Font NORMAL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
	    private static final Font INPUT_FONT = new Font("Segoe UI", Font.PLAIN, 16);
	
	    // DB Credentials
	    private static final String URL = "jdbc:mysql://localhost:3306/student_report";
	    private static final String USER = "root";
	    private static final String PASSWORD = "laxmani@1609";
	
	    public DynamicStudentReportCardGenerator() {
	        setTitle("Student Report Card Generator");
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setSize(1000, 1000);
	        setLocationRelativeTo(null);
	        setBackground(BACKGROUND_COLOR);
	
	        cardLayout = new CardLayout();
	        mainPanel = new JPanel(cardLayout);
	        mainPanel.setBackground(BACKGROUND_COLOR);
	
	        createInputPanel();
	        createReportPanel();
	
	        add(mainPanel);
	        cardLayout.show(mainPanel, "input");
	    }
	
	    private void createInputPanel() {
	        inputPanel = new JPanel();
	        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
	        inputPanel.setBackground(BACKGROUND_COLOR);
	        inputPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
	
	        // Create header panel with logo and university name
	        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
	        headerPanel.setBackground(BACKGROUND_COLOR);
	        headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	
	        // Add university logo
	        ImageIcon originalIcon = new ImageIcon("C:\\Users\\Santhosh Kumar B\\Desktop\\imp.jpg");
	        Image scaledImage = originalIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
	        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
	        
	        // Add university name with custom styling
	        JLabel universityLabel = new JLabel("K L UNIVERSITY HYD");
	        universityLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
	        universityLabel.setForeground(PRIMARY_COLOR);
	
	        headerPanel.add(logoLabel);
	        headerPanel.add(universityLabel);
	        inputPanel.add(headerPanel);
	        inputPanel.add(Box.createVerticalStrut(30));
	
	        // Main content panel with shadow effect
	        JPanel contentPanel = new JPanel();
	        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
	        contentPanel.setBackground(CARD_COLOR);
	        contentPanel.setBorder(BorderFactory.createCompoundBorder(
	            new ShadowBorder(),
	            BorderFactory.createEmptyBorder(30, 40, 30, 40)
	        ));
	        contentPanel.setMaximumSize(new Dimension(800, 800));
	        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	        
	        JLabel titleLabel = new JLabel("Student Information");
	        titleLabel.setFont(TITLE_FONT);
	        titleLabel.setForeground(PRIMARY_COLOR);
	        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	        contentPanel.add(titleLabel);
	        contentPanel.add(Box.createVerticalStrut(30));
	
	        // Student info section
	        JPanel studentInfoPanel = new JPanel(new GridLayout(3, 2, 20, 20));
	        studentInfoPanel.setBackground(CARD_COLOR);
	
	        idField = createStyledTextField("Enter Student ID");
	        firstNameField = createStyledTextField("Enter First Name");
	        surnameField = createStyledTextField("Enter Surname");
	
	        studentInfoPanel.add(createLabelPanel("Student ID:"));
	        studentInfoPanel.add(idField);
	        studentInfoPanel.add(createLabelPanel("First Name:"));
	        studentInfoPanel.add(firstNameField);
	        studentInfoPanel.add(createLabelPanel("Surname:"));
	        studentInfoPanel.add(surnameField);
	
	        contentPanel.add(studentInfoPanel);
	        contentPanel.add(Box.createVerticalStrut(40));
	
	     // In the createInputPanel method, update the marks section:
	
	     // In the createInputPanel method, update the marks section:
	
	     // Marks section
	     JLabel marksTitle = new JLabel("Subject Marks");
	     marksTitle.setFont(HEADER_FONT);
	     marksTitle.setForeground(PRIMARY_COLOR);
	     marksTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
	     contentPanel.add(marksTitle);
	     contentPanel.add(Box.createVerticalStrut(20));
	
	     // Create a panel for marks with proper spacing
	     JPanel marksContainerPanel = new JPanel();
	     marksContainerPanel.setLayout(new BorderLayout());
	     marksContainerPanel.setBackground(CARD_COLOR);
	     marksContainerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	
	     // Use GridBagLayout for better control over component placement
	     JPanel marksPanel = new JPanel(new GridBagLayout());
	     marksPanel.setBackground(CARD_COLOR);
	     marksFields = new JTextField[SUBJECTS.length];
	     GridBagConstraints gbc = new GridBagConstraints();
	     gbc.fill = GridBagConstraints.HORIZONTAL;
	     gbc.insets = new Insets(5, 5, 5, 5);
	
	     for (int i = 0; i < SUBJECTS.length; i++) {
	         // Label constraints
	         gbc.gridx = 0;
	         gbc.gridy = i;
	         gbc.weightx = 0.3;
	         JLabel subjectLabel = new JLabel(SUBJECTS[i] + ":");
	         subjectLabel.setFont(NORMAL_FONT);
	         marksPanel.add(subjectLabel, gbc);
	
	         // TextField constraints
	         gbc.gridx = 1;
	         gbc.weightx = 0.7;
	         marksFields[i] = new JTextField(20);
	         marksFields[i].setFont(INPUT_FONT);
	         marksFields[i].setPreferredSize(new Dimension(150, 30));
	         marksFields[i].setBorder(BorderFactory.createCompoundBorder(
	             BorderFactory.createLineBorder(new Color(224, 224, 224)),
	             BorderFactory.createEmptyBorder(5, 5, 5, 5)
	         ));
	         
	         // Placeholder text
	         final String placeholder = "Enter marks (0-100)";
	         marksFields[i].setText(placeholder);
	         marksFields[i].setForeground(Color.GRAY);
	         
	         // Focus listener for placeholder
	         final int index = i;
	         marksFields[i].addFocusListener(new FocusAdapter() {
	             @Override
	             public void focusGained(FocusEvent e) {
	                 if (marksFields[index].getText().equals(placeholder)) {
	                     marksFields[index].setText("");
	                     marksFields[index].setForeground(Color.BLACK);
	                 }
	             }
	             
	             @Override
	             public void focusLost(FocusEvent e) {
	                 if (marksFields[index].getText().isEmpty()) {
	                     marksFields[index].setText(placeholder);
	                     marksFields[index].setForeground(Color.GRAY);
	                 }
	             }
	         });
	         
	         marksPanel.add(marksFields[i], gbc);
	     }
	
	     // Simple scroll pane with basic scrollbar
	     JScrollPane scrollPane = new JScrollPane(marksPanel);
	     scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
	     scrollPane.setPreferredSize(new Dimension(500, 250));
	     scrollPane.getVerticalScrollBar().setUnitIncrement(16);
	
	     marksContainerPanel.add(scrollPane, BorderLayout.CENTER);
	     contentPanel.add(marksContainerPanel);
	     contentPanel.add(Box.createVerticalStrut(30));
	
	     // Enhanced button styling without custom UI
	     JButton submitButton = new JButton("Generate Report Card") {
	         @Override
	         protected void paintComponent(Graphics g) {
	             Graphics2D g2 = (Graphics2D) g.create();
	             g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	             g2.setColor(getBackground());
	             g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
	             super.paintComponent(g);
	             g2.dispose();
	         }
	     };
	
	     submitButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
	     submitButton.setBackground(new Color(25, 118, 210));
	     submitButton.setForeground(Color.WHITE);
	     submitButton.setFocusPainted(false);
	     submitButton.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
	     submitButton.setContentAreaFilled(false);
	     submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
	     submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	
	     // Button hover effect
	     submitButton.addMouseListener(new MouseAdapter() {
	         public void mouseEntered(MouseEvent e) {
	             submitButton.setBackground(new Color(30, 136, 229));
	         }
	         public void mouseExited(MouseEvent e) {
	             submitButton.setBackground(new Color(25, 118, 210));
	         }
	     });
	
	     submitButton.addActionListener(e -> generateReport());
	     contentPanel.add(submitButton);
	
	        inputPanel.add(contentPanel);
	        mainPanel.add(inputPanel, "input");
	    }
	
	    // Custom shadow border
	    private class ShadowBorder extends AbstractBorder {
	        @Override
	        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
	            Graphics2D g2 = (Graphics2D) g.create();
	            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	            
	            for (int i = 0; i < 4; i++) {
	                g2.setColor(new Color(0, 0, 0, 20 - (i * 4)));
	                g2.drawRoundRect(x + i, y + i, width - (2 * i) - 1, height - (2 * i) - 1, 15, 15);
	            }
	            g2.dispose();
	        }
	        
	        @Override
	        public Insets getBorderInsets(Component c) {
	            return new Insets(4, 4, 4, 4);
	        }
	    }
	
	    private JTextField createStyledTextField(String placeholder) {
	        JTextField field = new JTextField(20);
	        field.setFont(INPUT_FONT);
	        field.setForeground(TEXT_PRIMARY);
	        field.setBorder(BorderFactory.createCompoundBorder(
	            BorderFactory.createLineBorder(new Color(224, 224, 224), 1, true),
	            BorderFactory.createEmptyBorder(10, 15, 10, 15)
	        ));
	        
	        // Add placeholder text
	        field.setText(placeholder);
	        field.setForeground(TEXT_SECONDARY);
	        
	        field.addFocusListener(new FocusAdapter() {
	            @Override
	            public void focusGained(FocusEvent e) {
	                if (field.getText().equals(placeholder)) {
	                    field.setText("");
	                    field.setForeground(TEXT_PRIMARY);
	                }
	            }
	            
	            @Override
	            public void focusLost(FocusEvent e) {
	                if (field.getText().isEmpty()) {
	                    field.setText(placeholder);
	                    field.setForeground(TEXT_SECONDARY);
	                }
	            }
	        });
	        
	        return field;
	    }
	
	    private JPanel createLabelPanel(String text) {
	        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
	        panel.setBackground(CARD_COLOR);
	        JLabel label = new JLabel(text);
	        label.setFont(NORMAL_FONT);
	        label.setForeground(TEXT_PRIMARY);
	        panel.add(label);
	        return panel;
	    }
	    
	    private void createReportPanel() {
	        reportPanel = new JPanel();
	        reportPanel.setLayout(new BoxLayout(reportPanel, BoxLayout.Y_AXIS));
	        reportPanel.setBackground(Color.WHITE);
	        reportPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
	        mainPanel.add(reportPanel, "report");
	    }
	
	    private JTextField createStyledTextField() {
	        JTextField field = new JTextField(20);
	        field.setFont(NORMAL_FONT);
	        field.setBorder(BorderFactory.createCompoundBorder(
	                BorderFactory.createLineBorder(Color.GRAY),
	                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
	        return field;
	    }
	
	    private void generateReport() {
	        try {
	            String studentId = idField.getText().trim();
	            String firstName = firstNameField.getText().trim();
	            String surname = surnameField.getText().trim();
	
	            if (studentId.isEmpty() || firstName.isEmpty() || surname.isEmpty()) {
	                JOptionPane.showMessageDialog(this, "Please fill all student information fields.", "Validation Error",
	                        JOptionPane.ERROR_MESSAGE);
	                return;
	            }
	
	            int[] marks = new int[SUBJECTS.length];
	            int totalMarks = 0;
	            for (int i = 0; i < SUBJECTS.length; i++) {
	                String markText = marksFields[i].getText().trim();
	                if (markText.isEmpty()) {
	                    JOptionPane.showMessageDialog(this, "Please enter marks for all subjects.", "Validation Error",
	                            JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	                marks[i] = Integer.parseInt(markText);
	                if (marks[i] < 0 || marks[i] > 100) {
	                    JOptionPane.showMessageDialog(this, "Marks must be between 0 and 100.", "Validation Error",
	                            JOptionPane.ERROR_MESSAGE);
	                    return;
	                }
	                totalMarks += marks[i];
	            }
	
	            double percentage = (double) totalMarks / (SUBJECTS.length * 100) * 100;
	            String grade = calculateGrade(percentage);
	
	            saveToDatabase(studentId, firstName, surname, marks, totalMarks, percentage, grade);
	            showReport(studentId);
	
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(this, "Please enter valid numeric marks.", "Input Error",
	                    JOptionPane.ERROR_MESSAGE);
	        }
	    }
	
	    private void showReport(String studentId) {
	        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM reports WHERE student_id = ?")) {
	
	            pstmt.setString(1, studentId);
	            ResultSet rs = pstmt.executeQuery();
	
	            if (rs.next()) {
	                reportPanel.removeAll();
	
	                // Create header panel with logo and university name
	                JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
	                headerPanel.setBackground(Color.WHITE);
	                headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
	
	                // Add university logo
	                ImageIcon originalIcon = new ImageIcon("C:\\Users\\Santhosh Kumar B\\Desktop\\imp.jpg");
	                // Scale the image to appropriate size (e.g., 80x80 pixels)
	                Image scaledImage = originalIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
	                JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
	                
	                // Add university name with custom styling
	                JLabel universityLabel = new JLabel("K L UNIVERSITY HYD");
	                universityLabel.setFont(new Font("Arial", Font.BOLD, 28));
	                universityLabel.setForeground(new Color(0, 51, 102)); // Dark blue color
	
	                headerPanel.add(logoLabel);
	                headerPanel.add(universityLabel);
	
	                reportPanel.add(headerPanel);
	                reportPanel.add(Box.createVerticalStrut(20));
	
	                // Add a separator line
	                JSeparator separator = new JSeparator();
	                separator.setMaximumSize(new Dimension(700, 1));
	                separator.setForeground(new Color(200, 200, 200));
	                reportPanel.add(separator);
	                reportPanel.add(Box.createVerticalStrut(20));
	
	                JLabel reportTitle = new JLabel("Report Card");
	                reportTitle.setFont(TITLE_FONT);
	                reportTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
	                reportPanel.add(reportTitle);
	                reportPanel.add(Box.createVerticalStrut(20));
	
	                JLabel nameLabel = new JLabel(rs.getString("first_name") + " " + rs.getString("surname"));
	                nameLabel.setFont(HEADER_FONT);
	                nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	
	                JLabel idLabel = new JLabel("Student ID: " + rs.getString("student_id"));
	                idLabel.setFont(NORMAL_FONT);
	                idLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
	
	                JPanel infoPanel = new JPanel();
	                infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
	                infoPanel.setBackground(Color.WHITE);
	                infoPanel.add(nameLabel);
	                infoPanel.add(idLabel);
	                reportPanel.add(infoPanel);
	                reportPanel.add(Box.createVerticalStrut(20));
	
	                // Rest of the code remains the same
	                JPanel marksPanel = new JPanel();
	                marksPanel.setLayout(new BoxLayout(marksPanel, BoxLayout.Y_AXIS));
	                marksPanel.setBackground(Color.WHITE);
	                marksPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY),
	                        "Subject Wise Performance", TitledBorder.LEFT, TitledBorder.TOP, HEADER_FONT));
	
	                int totalMarks = rs.getInt("total_marks");
	                for (String subject : SUBJECTS) {
	                    int mark = rs.getInt(subject);
	                    JPanel subjectPanel = new JPanel(new BorderLayout(10, 0));
	                    subjectPanel.setBackground(new Color(249, 250, 251));
	                    subjectPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	
	                    JLabel subjectLabel = new JLabel(subject);
	                    subjectLabel.setFont(NORMAL_FONT);
	
	                    JLabel markLabel = new JLabel(mark + "/100");
	                    markLabel.setFont(NORMAL_FONT);
	
	                    subjectPanel.add(subjectLabel, BorderLayout.WEST);
	                    subjectPanel.add(createProgressBar(mark), BorderLayout.CENTER);
	                    subjectPanel.add(markLabel, BorderLayout.EAST);
	
	                    marksPanel.add(subjectPanel);
	                    marksPanel.add(Box.createVerticalStrut(5));
	                }
	
	                reportPanel.add(marksPanel);
	                reportPanel.add(Box.createVerticalStrut(20));
	
	                double percentage = rs.getDouble("percentage");
	                String grade = rs.getString("grade");
	
	                JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 10, 0));
	                summaryPanel.setBackground(Color.WHITE);
	                summaryPanel.add(createSummaryCard("Total Marks", totalMarks + "/" + (SUBJECTS.length * 100),
	                        new Color(239, 246, 255)));
	                summaryPanel.add(createSummaryCard("Percentage", String.format("%.2f%%", percentage),
	                        new Color(240, 253, 244)));
	                summaryPanel.add(createSummaryCard("Grade", grade, new Color(243, 232, 255)));
	
	                reportPanel.add(summaryPanel);
	                reportPanel.add(Box.createVerticalStrut(20));
	
	                JButton backButton = new JButton("Back to Form") {
	                    @Override
	                    protected void paintComponent(Graphics g) {
	                        Graphics2D g2 = (Graphics2D) g.create();
	                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	                        g2.setColor(getBackground());
	                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
	                        super.paintComponent(g);
	                        g2.dispose();
	                    }
	                };
	
	                backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
	                backButton.setBackground(new Color(75, 85, 99));
	                backButton.setForeground(Color.WHITE);
	                backButton.setFocusPainted(false);
	                backButton.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
	                backButton.setContentAreaFilled(false);
	                backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
	                backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	
	                backButton.addMouseListener(new MouseAdapter() {
	                    public void mouseEntered(MouseEvent e) {
	                        backButton.setBackground(new Color(90, 100, 115));
	                    }
	                    public void mouseExited(MouseEvent e) {
	                        backButton.setBackground(new Color(75, 85, 99));
	                    }
	                });
	
	                backButton.addActionListener(e -> cardLayout.show(mainPanel, "input"));
	
	
	                reportPanel.add(backButton);
	
	                cardLayout.show(mainPanel, "report");
	                reportPanel.revalidate();
	                reportPanel.repaint();
	            }
	        } catch (SQLException ex) {
	            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	
	    private void saveToDatabase(String studentId, String firstName, String surname, int[] marks,
	                                int totalMarks, double percentage, String grade) {
	        String query = "INSERT INTO reports (student_id, first_name, surname, NPS, AAOP, AADS, OS, FSD, ASE, total_marks, percentage, grade) " +
	                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
	                "ON DUPLICATE KEY UPDATE " +
	                "first_name=?, surname=?, NPS=?, AAOP=?, AADS=?, OS=?, FSD=?, ASE=?, total_marks=?, percentage=?, grade=?";
	
	        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
	             PreparedStatement pstmt = conn.prepareStatement(query)) {
	
	            pstmt.setString(1, studentId);
	            pstmt.setString(2, firstName);
	            pstmt.setString(3, surname);
	            for (int i = 0; i < marks.length; i++) {
	                pstmt.setInt(4 + i, marks[i]);
	            }
	            pstmt.setInt(10, totalMarks);
	            pstmt.setDouble(11, percentage);
	            pstmt.setString(12, grade);
	
	            // For update
	            pstmt.setString(13, firstName);
	            pstmt.setString(14, surname);
	            for (int i = 0; i < marks.length; i++) {
	                pstmt.setInt(15 + i, marks[i]);
	            }
	            pstmt.setInt(21, totalMarks);
	            pstmt.setDouble(22, percentage);
	            pstmt.setString(23, grade);
	
	            pstmt.executeUpdate();
	        } catch (SQLException ex) {
	            JOptionPane.showMessageDialog(this, "Failed to save data: " + ex.getMessage(), "Database Error",
	                    JOptionPane.ERROR_MESSAGE);
	        }
	    }
	
	    private JPanel createProgressBar(int value) {
	        JPanel progressPanel = new JPanel() {
	            @Override
	            protected void paintComponent(Graphics g) {
	                super.paintComponent(g);
	                Graphics2D g2d = (Graphics2D) g;
	                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	
	                // Draw background bar
	                g2d.setColor(new Color(229, 231, 235));
	                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
	
	                // Calculate width for the progress
	                int progressWidth = (int) ((value / 100.0) * getWidth());
	                
	                // Draw progress bar
	                g2d.setColor(PRIMARY_COLOR);
	                if (progressWidth > 0) {
	                    g2d.fillRoundRect(0, 0, progressWidth, getHeight(), 10, 10);
	                }
	
	                // Add a subtle border
	                g2d.setColor(new Color(0, 0, 0, 20));
	                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
	            }
	
	            @Override
	            public Dimension getPreferredSize() {
	                return new Dimension(200, 15);
	            }
	        };
	        
	        progressPanel.setOpaque(false);
	        return progressPanel;
	    }
	
	    private JPanel createSummaryCard(String title, String value, Color bgColor) {
	        JPanel card = new JPanel();
	        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
	        card.setBackground(bgColor);
	        card.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	
	        JLabel titleLabel = new JLabel(title);
	        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
	        titleLabel.setForeground(Color.GRAY);
	        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
	
	        JLabel valueLabel = new JLabel(value);
	        valueLabel.setFont(new Font("Arial", Font.BOLD, 20));
	        valueLabel.setForeground(PRIMARY_COLOR);
	        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
	
	        card.add(titleLabel);
	        card.add(Box.createVerticalStrut(5));
	        card.add(valueLabel);
	
	        return card;
	    }
	
	    private String calculateGrade(double percentage) {
	        if (percentage >= 90) return "A+";
	        if (percentage >= 80) return "A";
	        if (percentage >= 70) return "B";
	        if (percentage >= 60) return "C";
	        if (percentage >= 50) return "D";
	        return "F";
	    }
	
	    public static void main(String[] args) {
	        try {
	            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	
	        SwingUtilities.invokeLater(() -> {
	            new DynamicStudentReportCardGenerator().setVisible(true);
	        });
	    }
	}
