import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.border.EmptyBorder;

public class RecursiveListerFrame extends JFrame {

    JPanel mainPnl;
    JPanel topPnl;
    JPanel middlePnl;
    JPanel bottomPnl;

    JTextArea displayTA;
    JScrollPane scroller;

    JLabel titleLbl;
    ImageIcon icon;

    JButton start;
    JButton quit;

    ArrayList<String> fortunes = new ArrayList<String>();

    int fortuneIndex = -1;

    Random rnd = new Random();


    public RecursiveListerFrame()
    {
        mainPnl = new JPanel();

        mainPnl.setLayout(new BorderLayout());

        createTopPnl();
        mainPnl.add(topPnl, BorderLayout.NORTH);

        createMiddlePnl();
        mainPnl.add(middlePnl, BorderLayout.CENTER);

        createBottomPnl();
        mainPnl.add(bottomPnl, BorderLayout.SOUTH);

        add(mainPnl);

    }

    private void createTopPnl()
    {
        topPnl = new JPanel();
        titleLbl = new JLabel("Recursive Lister", JLabel.CENTER);

        titleLbl.setFont(new Font("Roboto", Font.PLAIN, 36));

        titleLbl.setVerticalTextPosition(JLabel.BOTTOM);
        titleLbl.setHorizontalTextPosition(JLabel.CENTER);

        topPnl.setBackground(new Color(198,226,255));

        topPnl.add(titleLbl);
    }

    private void createMiddlePnl()
    {
        middlePnl = new JPanel();
        displayTA = new JTextArea(10, 40);

        displayTA.setFont(new Font("Verdana", Font.PLAIN, 20));

        displayTA.setEditable(true);
        scroller = new JScrollPane(displayTA);
        middlePnl.add(scroller);

        middlePnl.setBackground(new Color(198,226,255));

    }

    private void createBottomPnl()
    {


        bottomPnl = new JPanel();
        bottomPnl.setLayout(new GridLayout(1, 2));

        start = new JButton("Start");
        start.addActionListener((ActionEvent ae) ->
        {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            File selectedFile;
            String rec = "";

            try
            {

                File workingDirectory = new File(System.getProperty("user.home"));

                chooser.setCurrentDirectory(workingDirectory);

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                {
                    selectedFile = chooser.getSelectedFile();
                    Path file = selectedFile.toPath();

                    recursiveList(selectedFile);

                    InputStream in = new BufferedInputStream(Files.newInputStream(file, CREATE));
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                    reader.close();

                }

                else {
                    System.out.println("Failed to choose a file to process");
                    System.out.println("Run the program again!");
                    System.exit(0);
                }
            }

            catch (FileNotFoundException e)
            {
                System.out.println("File not found!");
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }


        });


        quit = new JButton("Quit");
        quit.addActionListener((ActionEvent ae) -> System.exit(0));

        start.setPreferredSize(new Dimension(40, 40));
        quit.setPreferredSize(new Dimension(40, 40));

        start.setFont(new Font("Sans Serif", Font.PLAIN, 15));
        quit.setFont(new Font("Sans Serif", Font.PLAIN, 15));

        bottomPnl.add(start);
        bottomPnl.add(quit);

        bottomPnl.setBackground(new Color(198,226,255));

    }

    private void recursiveList(File file){

        File[] listOfFiles = file.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                Path filePath = listOfFiles[i].toPath();
                displayTA.append(listOfFiles[i].getName()+"\n");
            } else if (listOfFiles[i].isDirectory()) {
                recursiveList(listOfFiles[i]);
            }
        }
    }

}