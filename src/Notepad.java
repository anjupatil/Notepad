import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Notepad extends JFrame implements ActionListener {

    private JTextArea textArea;
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, exitMenuItem;

    private File currentFile;

    public Notepad() {
        super("Notepad");

        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        newMenuItem = new JMenuItem("New");
        newMenuItem.addActionListener(this);
        fileMenu.add(newMenuItem);

        openMenuItem = new JMenuItem("Open...");
        openMenuItem.addActionListener(this);
        fileMenu.add(openMenuItem);

        saveMenuItem = new JMenuItem("Save");
        saveMenuItem.addActionListener(this);
        fileMenu.add(saveMenuItem);

        saveAsMenuItem = new JMenuItem("Save As...");
        saveAsMenuItem.addActionListener(this);
        fileMenu.add(saveAsMenuItem);

        fileMenu.addSeparator();

        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(this);
        fileMenu.add(exitMenuItem);

        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newMenuItem) {
            newFile();
        } else if (e.getSource() == openMenuItem) {
            openFile();
        } else if (e.getSource() == saveMenuItem) {
            saveFile();
        } else if (e.getSource() == saveAsMenuItem) {
            saveAsFile();
        } else if (e.getSource() == exitMenuItem) {
            exit();
        }
    }

    private void newFile() {
        if (textArea.getText().equals("")) {
            currentFile = null;
            setTitle("Notepad");
        } else {
            int confirm = JOptionPane.showConfirmDialog(this, "Do you want to save changes to " + currentFile.getName() + "?", "New", JOptionPane.YES_NO_CANCEL_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                saveFile();
            } else if (confirm == JOptionPane.NO_OPTION) {
                currentFile = null;
                setTitle("Notepad");
                textArea.setText("");
            }
        }
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            setTitle(currentFile.getName() + " - Notepad");
            try {
                BufferedReader reader = new BufferedReader(new FileReader(currentFile));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                textArea.setText(sb.toString());
                reader.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error opening file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFile() {
        if (currentFile == null) {
            saveAsFile();
        } else {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile));
                writer.write(textArea.getText());
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveAsFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            setTitle(currentFile.getName() + " - Notepad");
            saveFile();
        }
    }

    private void exit() {
        if (textArea.getText().equals("")) {
            System.exit(0);
        } else {
            int confirm = JOptionPane.showConfirmDialog(this, "Do you want to save changes to " + currentFile.getName() + "?", "Exit", JOptionPane.YES_NO_CANCEL_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                saveFile();
                System.exit(0);
            } else if (confirm == JOptionPane.NO_OPTION) {
                System.exit(0);
            }
        }
    }
    public static void main(String[] args) {
        new Notepad();
    }

}

