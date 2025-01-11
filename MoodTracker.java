import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MoodTracker {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mood Tracker");
        JTextArea textArea = new JTextArea("Enter your thoughts...");
        JButton submitButton = new JButton("Submit");
        JLabel resultLabel = new JLabel("Your sentiment will appear here.");
        JTextArea historyArea = new JTextArea("Mood History:\n", 10, 30);
        historyArea.setEditable(false);

        // Load mood history on startup
        loadHistory(historyArea);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = textArea.getText();
                String sentiment = analyzeSentiment(input);
                resultLabel.setText("Your sentiment: " + sentiment);
                saveEntry(input, sentiment);
                updateHistory(input, sentiment, historyArea);
                textArea.setText(""); // Clear input area after submission
            }
        });

        frame.add(textArea);
        frame.add(submitButton);
        frame.add(resultLabel);
        frame.add(new JScrollPane(historyArea));
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static String analyzeSentiment(String text) {
        String[] positiveWords = {"happy", "great", "fantastic", "amazing", "good", "energetic"};
        String[] negativeWords = {"sad", "angry", "bad", "terrible", "upset", "frustrated"};

        int positiveCount = 0;
        int negativeCount = 0;

        for (String word : positiveWords) {
            if (text.toLowerCase().contains(word)) {
                positiveCount++;
            }
        }

        for (String word : negativeWords) {
            if (text.toLowerCase().contains(word)) {
                negativeCount++;
            }
        }

        if (positiveCount > negativeCount) {
            return "Positive";
        } else if (negativeCount > positiveCount) {
            return "Negative";
        } else {
            return "Neutral";
        }
    }

    public static void saveEntry(String input, String sentiment) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("mood_history.txt", true))) {
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            writer.write(date + " - " + input + " - " + sentiment + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void loadHistory(JTextArea historyArea) {
        try (BufferedReader reader = new BufferedReader(new FileReader("mood_history.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                historyArea.append(line + "\n");
            }
        } catch (IOException ex) {
            historyArea.append("No history available.\n");
        }
    }

    public static void updateHistory(String input, String sentiment, JTextArea historyArea) {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        historyArea.append(date + " - " + input + " - " + sentiment + "\n");
    }
}
