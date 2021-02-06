
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.*;

public class Runner {

    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    //get random city
    private static City random(Map<String, City> popToCity) {
        Set<String> codes = popToCity.keySet();
        int index = (int) (Math.random() * codes.size());
        //return random city
        return popToCity.get((String) codes.toArray()[index]);
    }

    //ensure selected response is within answer choice list
    private static boolean ensureValidChoice(String input, int nChoices) {
        input = input.toUpperCase();
        return input.length() == 1 && ALPHABET.contains(input) && ALPHABET.indexOf(input) <= nChoices;
    }

    public static void main(String[] args) throws IOException{
        try {
            // read in CityData.dat
            BufferedReader br = new BufferedReader(new FileReader("CityData.txt"));
            HashMap<String, City> popToCity = new HashMap<String, City>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] tabsplit = line.split("\t");
                String city = tabsplit[0].substring(0, tabsplit[0].lastIndexOf(", "));
                String state = tabsplit[0].substring(tabsplit[0].indexOf(", ") + ", ".length());
                String pop = tabsplit[1];
                popToCity.put(pop, new City(city, state, pop));
            }
            br.close();

            // game stuff
            int questionCount = 10;
            int accuracy = 0;
            Scanner scanner = new Scanner(System.in);

            long startTime = System.currentTimeMillis(); // Get the start Time
            long endTime = 0;

            for (int i = 0; i < questionCount; i++) {
                City[] choices = new City[] { random(popToCity), random(popToCity), random(popToCity), random(popToCity), random(popToCity) };
                int correctAnswerIndex = (int)(Math.random()*choices.length);
                City answer = choices[correctAnswerIndex];
                String question = "QUESTION "  + (i+1) + " OF " + questionCount + ": Which city is associated with the population " + answer.getPop();
                String[] answers = new String[choices.length];
                for (int j = 0; j < answers.length; j++)
                    answers[j] = ALPHABET.charAt(j) + ") " + choices[j].getName() + ", " + choices[j].getState();
                System.out.println(question);
                for (String x : answers) System.out.println("\t" + x);
                System.out.println("\nEnter a letter: ");
                String input = scanner.nextLine();
                //Improper input
                while (!ensureValidChoice(input, answers.length)) {
                    System.out.println("Invalid input. Retry: ");
                    input = scanner.nextLine();
                }
                int pickedIndex = ALPHABET.indexOf(input);
                if (pickedIndex == correctAnswerIndex) {
                    System.out.println("Correct! Great job.");
                    accuracy++;
                } else {
                    //try again
                    System.out.println("Incorrect - the correct answer was " + ALPHABET.charAt(correctAnswerIndex));
                }
                System.out.println("Press ENTER to continue.\n\n");
                scanner.nextLine();
            }
            endTime = System.currentTimeMillis(); //Get the end Time

            System.out.println("You finished the game with an accuracy of " + accuracy + " out of 10 (" + (int)(accuracy*100.0/questionCount) + "%).\n\nScores are available to view in the Score_Report.txt file!");
            scanner.close();
            //write score report
            writeScoreReport(accuracy ,questionCount, (int) ((endTime-startTime)/1000));
            // write to CityData.dat
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("CityData.dat")));
            for (Map.Entry<String, City> entry : popToCity.entrySet()) pw.println(entry.getKey() + " = " + entry.getValue().toString());
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeScoreReport(int accuracy, int count, int duration) throws IOException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm");
        LocalDateTime now = LocalDateTime.now();
        PrintWriter p = new PrintWriter(new BufferedWriter(new FileWriter("Score_Report.txt", true)));
        p.println("Taken At: " + dtf.format(now));
        p.println("Duration: " + duration +" sec");
        p.println("Raw Score: " + accuracy + "/" + count);
        int percent = (int)(accuracy*100.0/count);
        p.println("Score: "  + percent + "%\n\n");
        p.close();
    }
}
