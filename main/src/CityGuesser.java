import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CityGuesser extends JFrame{
    private JTextArea textArea1;
    private JButton enterButton;
    private JPanel mainPanel;
    private JTextField textField1;
    private boolean enterPressed;

    public CityGuesser(String title){
        super(title);
        enterPressed = false;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enterPressed = true;
                textField1.setText("");
            }
        });
        textField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 10){
                    enterPressed = true;
                    textField1.setText("");
                }
            }
        });
        mainPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 10){
                    enterPressed = true;
                    textField1.setText("");
                }
            }
        });
    }
    //send the questions, then get the response for that question. then, continue calling until all questions done, and then send score to sendScore()
    public String sendQuestion(String question, String[] answers){
        textField1.show();
        String text = question;
        for(String s : answers){
            text += "\n" + s;
        }
        textArea1.setText(text);
        while(!ensureValidChoice(textField1.getText(),5)) {
            while (!enterPressed) {
                "".split(" ");
            }
            if(ensureValidChoice(textField1.getText(),5)) break;
            enterPressed = false;
            textArea1.setText(text + "\n That is not an answer choice!");
        }
        enterPressed = false;
        //String a = textField1.

        return textField1.getText();
        //return "a";
    }
    public void sendResult(String result){
        textField1.hide();
        textArea1.setText(result + "\n Press Enter To Continue");
        while(!enterPressed){
            "".split(" ");
        }
        enterPressed = false;
        return;
    }

    public void sendScore(String scoreText){
        textArea1.setText(scoreText);
        return;
    }
    private static boolean ensureValidChoice(String input, int nChoices) {
        String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        input = input.toUpperCase();
        return input.length() == 1 && ALPHABET.contains(input) && ALPHABET.indexOf(input) <= nChoices;
    }
}
