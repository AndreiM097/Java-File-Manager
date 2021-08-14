package com.marianandrei.gui;

import com.marianandrei.filemanager.FileManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;

/*  TODO:*Add "Choose Path" button by using JFileChooser, link here : https://stackoverflow.com/questions/10083447/selecting-folder-destination-in-java/10083508 */

public class UserInterface {
    private JFrame jFrame;
//    private JPanel jPanel;
    private JLabel firstPathInfo;
    private JLabel secondPathInfo;
    private JFileChooser unmanagedPathChooser;
    private JFileChooser toManagePathChooser;
    public static String defaultDownloadsPath;
    public static String currentPathToManage;
    public static String newManagedPath;
    private JLabel displayFirstPath;
    private JLabel displaySecondPath;
    private JLabel goBtnInfo;
    private JButton goBtn;
    private JButton chosenUnmanagedPathBtn;
    private JButton pathToManageFilesBtn;
    private int response;

    private ImageIcon background;
    private JLabel backgroundLabel;

    public UserInterface() {

        defaultDownloadsPath = "C:/Users/" + System.getProperty("user.name") + "/Downloads";

        //Setting the first path from user's input:
        firstPathInfo = new JLabel("Please choose the first path where you want the files to be taken from.");
        firstPathInfo.setBounds(10, 20, 500, 20);
        firstPathInfo.setForeground(Color.WHITE);

        chosenUnmanagedPathBtn = new JButton("Choose folder");
        chosenUnmanagedPathBtn.setBounds(10, 45, 113, 20);
        chosenUnmanagedPathBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                unmanagedPathChooser = new JFileChooser(defaultDownloadsPath);
                unmanagedPathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                response = unmanagedPathChooser.showOpenDialog(null);

                try {
                    displayFirstPath.setText(unmanagedPathChooser.getSelectedFile().getAbsolutePath());
                    if(Files.exists(Path.of(unmanagedPathChooser.getSelectedFile().getAbsolutePath())) && unmanagedPathChooser.getSelectedFile().getAbsolutePath() != null){
                        displayFirstPath.setForeground(Color.GREEN);
                        resetUi();
                    }
                } catch (NullPointerException exception) {
                    displayFirstPath.setText("No folder chosen.Setting default user's downloads path...");
                    displayFirstPath.setForeground(Color.RED);
                    currentPathToManage = defaultDownloadsPath;
                    resetUi();
                }

                if (response == JFileChooser.APPROVE_OPTION) {
                    currentPathToManage = unmanagedPathChooser.getSelectedFile().getAbsolutePath();
                }

//                FileManager fileManager = new FileManager();
//                fileManager.manageFiles(currentPathToManage, newManagedPath);
            }
        });

        displayFirstPath = new JLabel();
        displayFirstPath.setBounds(130, 45, 413, 20);
        displayFirstPath.setForeground(Color.WHITE);

        //Setting the second path from user's input:
        secondPathInfo = new JLabel("Please choose the second path where you want your files to be moved and managed.");
        secondPathInfo.setBounds(10 ,80 ,500, 20);
        secondPathInfo.setForeground(Color.WHITE);

        pathToManageFilesBtn = new JButton("Choose folder");
        pathToManageFilesBtn.setBounds(10 ,105 ,113, 20);
        pathToManageFilesBtn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                toManagePathChooser = new JFileChooser(defaultDownloadsPath);
                toManagePathChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                response = toManagePathChooser.showOpenDialog(null);

                try {
                    displaySecondPath.setText(toManagePathChooser.getSelectedFile().getAbsolutePath());
                    if(Files.exists(Path.of(toManagePathChooser.getSelectedFile().getAbsolutePath())) && toManagePathChooser.getSelectedFile().getAbsolutePath() != null){
                        displaySecondPath.setForeground(Color.green);
                        resetUi();
                    }
                } catch (NullPointerException exception) {
                    displaySecondPath.setText("No folder chosen.Setting default user's downloads path...");
                    newManagedPath = defaultDownloadsPath;
                    displaySecondPath.setForeground(Color.RED);
                    resetUi();
                }

                if (response == JFileChooser.APPROVE_OPTION) {
                    newManagedPath = toManagePathChooser.getSelectedFile().getAbsolutePath();
                    System.out.println( "The path that the files will be taken from: " + currentPathToManage);
                    System.out.println( "The path where the items will be moved and managed: " + newManagedPath);
                }
            }
        });

        displaySecondPath = new JLabel();
        displaySecondPath.setBounds(130, 105, 413, 20);
        displaySecondPath.setForeground(Color.WHITE);


        //TODO:* Add button that uses the first actionPerformed given path and the second one ( newManagedPath )


        goBtnInfo = new JLabel();
        goBtnInfo.setBounds(100, 170, 413, 20);
        goBtnInfo.setForeground(Color.WHITE);
        goBtnInfo.setVisible(false);

        goBtn = new JButton("Go!");
        goBtn.setBounds(100, 140, 413, 20);
        goBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if(currentPathToManage != null && newManagedPath != null) {
                    FileManager fileManager = new FileManager();

                    fileManager.manageFiles(currentPathToManage, newManagedPath);

                    goBtnInfo.setVisible(true);
                    goBtnInfo.setForeground(Color.GREEN);
                    goBtnInfo.setText("Successful!");
                    resetUi();
               }
               else{
                   goBtnInfo.setVisible(true);
                   goBtnInfo.setText("Something went wrong");
                   goBtnInfo.setForeground(Color.RED);

                   System.out.println("Something went wrong..." +
                           "\n Path 1: " + currentPathToManage +
                           "\n Path 2: " + newManagedPath);
                   resetUi();
               }
            }
        });

        background = new ImageIcon(this.getClass().getResource("/img/wallpaper.jpg"));
        backgroundLabel = new JLabel(background);

        backgroundLabel.setSize(600, 250);
        backgroundLabel.add(firstPathInfo);
        backgroundLabel.add(chosenUnmanagedPathBtn);
        backgroundLabel.add(secondPathInfo);
        backgroundLabel.add(pathToManageFilesBtn);
        backgroundLabel.add(displayFirstPath);
        backgroundLabel.add(displaySecondPath);
        backgroundLabel.add(goBtn);
        backgroundLabel.add(goBtnInfo);

        jFrame = new JFrame("File manager by Marian Andrei");
        jFrame.add(backgroundLabel);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.setBounds(250, 180, 600, 250);
        jFrame.setVisible(true);
    }

    public void resetUi(){
        backgroundLabel.setSize(601, 250);
        backgroundLabel.setSize(600, 250);
    }
}