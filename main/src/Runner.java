import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.awt.Desktop;


public class Runner {

    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";


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

    private static City random(Map<City, String> map) {
        Set<City> cities = map.keySet();
        int index = (int) (Math.random() * cities.size());
        return (City) cities.toArray()[index];
    }

    private static boolean ensureValidChoice(String input, int nChoices) {
        input = input.toUpperCase();
        return input.length() == 1 && ALPHABET.contains(input) && ALPHABET.indexOf(input) <= nChoices;
    }

    public static void main(String[] args) {
        CityGuesser Guesser = new CityGuesser("City Guesser");
        Guesser.setVisible(true);
        Guesser.setSize(600,300);

        try {
            // read in CityData.dat
            BufferedReader br = new BufferedReader(new FileReader("CityData.txt"));
            HashMap<City, String> cityToPop = new HashMap<City, String>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] tabsplit = line.split("\t");
                String city = tabsplit[0].substring(0, tabsplit[0].lastIndexOf(", "));
                String state = tabsplit[0].substring(tabsplit[0].indexOf(", ") + ", ".length());
                String pop = tabsplit[1];
                cityToPop.put(new City(city, state, pop), pop);
            }
            br.close();

            // game stuff
            int questionCount = 10;
            int accuracy = 0;
            long startTime = System.currentTimeMillis(); // Get the start Time
            long endTime = 0;

            //Scanner scanner = new Scanner(System.in);
            for (int i = 0; i < questionCount; i++) {
                City[] choices = new City[] { random(cityToPop), random(cityToPop), random(cityToPop), random(cityToPop), random(cityToPop) };
                int correctAnswerIndex = (int)(Math.random()*choices.length);
                City answer = choices[correctAnswerIndex];
                String question = "QUESTION "  + (i+1) + " OF " + questionCount + ": Which city is associated with the population " + answer.getPop() + "?";
                String[] answers = new String[choices.length];
                for (int j = 0; j < answers.length; j++)
                    answers[j] = ALPHABET.charAt(j) + ") " + choices[j].getName() + ", " + choices[j].getState();
                /*System.out.println(question);
                for (String x : answers) System.out.println("\t" + x);
                System.out.println("\nEnter a letter: ");
                String input = scanner.nextLine();
                while (!ensureValidChoice(input, answers.length)) {
                    System.out.println("Invalid input. Retry: ");
                    input = scanner.nextLine();
                }*/
                String input = Guesser.sendQuestion(question, answers).toUpperCase();
                int pickedIndex = ALPHABET.indexOf(input);
                if (pickedIndex == correctAnswerIndex) {
                    //System.out.println("Correct! Great job.");
                    Guesser.sendResult("Correct! Great job.");
                    accuracy++;
                } else {
                    //System.out.println("Incorrect - the correct answer was " + ALPHABET.charAt(correctAnswerIndex));
                    Guesser.sendResult("Incorrect - the correct answer was " + ALPHABET.charAt(correctAnswerIndex));
                }
                //System.out.println("Press ENTER to continue.\n\n");
                //scanner.nextLine();
            }
            endTime = System.currentTimeMillis(); //Get the end Time

            //scanner.close();
            //System.out.println("You finished the game with an accuracy of " + accuracy + " out of 10 (" + (int)(accuracy*100.0/questionCount) + "%).");
            Guesser.sendScore("You finished the game with an accuracy of " + accuracy + " out of " + questionCount + " (" + (int)(accuracy*100.0/questionCount) + "%). \nPlease see the output file Score_Report.txt for details.");
            writeScoreReport(accuracy ,questionCount, (int) ((endTime-startTime)/1000));

            //automatically open file
            try
            {
                //constructor of file class having file as argument
                File file = new File("Score_Report.txt");
                if(!Desktop.isDesktopSupported())//check if Desktop is supported by Platform or not
                {
                    System.out.println("not supported");
                    return;
                }
                Desktop desktop = Desktop.getDesktop();
                if(file.exists())         //checks file exists or not
                    desktop.open(file);              //opens the specified file
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }


            // write to CityData.dat
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("CityDataOutput.dat")));
            for (Map.Entry<City, String> entry : cityToPop.entrySet()) pw.println(entry.getKey() + " = " + entry.getValue().toString());
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
