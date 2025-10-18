/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pagereplacement;

import greetUI.PageReplacement;
import javax.swing.*;

/**
 *
 * @author Ronmar abalos
 */
public class MainApp extends JFrame{
     // Main entry point for the application 
    public static void main(String[] args) {
        // Use invokeLater to ensure that Swing components are created and updated
        // on the Event Dispatch Thread (EDT).
        SwingUtilities.invokeLater(() -> {
            new PageReplacement().setVisible(true);
        });
    }   
}

