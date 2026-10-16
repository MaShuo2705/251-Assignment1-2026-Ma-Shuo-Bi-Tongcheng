package com.assignment.editor;
import javax.swing.*;
import java.awt.*;
public class MainFrame extends JFrame
{
    public MainFrame()
    {
        setTitle("My Java Text Editor");
        setSize(800,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JMenuBar menuBar=new JMenuBar();
        JMenu fileMenu=new JMenu("File");
        JMenu searchMenu=new JMenu("Search");
        JMenu viewMenu=new JMenu("View");
        JMenu helpMenu=new JMenu("Help");
        menuBar.add(fileMenu);
        menuBar.add(searchMenu);
        menuBar.add(viewMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
        JTextArea textArea=new JTextArea();
        add(new JScrollPane(textArea),BorderLayout.CENTER);
        JMenuItem newItem=new JMenuItem("New");
        newItem.addActionListener(e->new MainFrame());
        fileMenu.add(newItem);
        
        setVisible(true);
    }
    public static void main(String[] args)
    {
        new MainFrame();
    }
}
