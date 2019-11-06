package com.eldar;

import com.eldar.solvers.Solver;
import com.eldar.solvers.SolverEEM;
import com.eldar.solvers.SolverMGS;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;


import javax.swing.*;
import java.awt.*;

// Главный класс. Создает интерфейс, сохраняет данные требуемые для рисования графы, и разбирается
// с логикой вызванной при нажатии кнопок.
public class Planner extends JComponent
{
  private int squareSize;
  private static int n = 100;
  private Button restart;
  private Button solve;
  private JTextPane txt;
  private FlightMap fm;

  private City start;
  private int startIx;
  private City end;
  private int endIx;
  private ArrayList<City> validSolution;
  private ArrayList<City> solverSolution;


  public static void main(String[] args){
    JFrame window = new JFrame("Flight Planner");
    Planner pln = new Planner();
    pln.setSquareSize();
    pln.txt = new JTextPane();
    pln.txt.setEditable(false);
    pln.txt.setSize(pln.txt.getPreferredSize());
    pln.restart = new Button("Restart");
    pln.solve = new Button("Solve");
    Container pane = window.getContentPane();
    pane.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    pln.generatePuzzle();

    pln.restart.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        pln.generatePuzzle();
        pln.validate();
        pln.repaint();
      }
    });

    pln.solve.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if(pln.startIx == -1){return;} // чтобы не решал не существующие задачи
        Solver solver = new SolverMGS(); // !!когда имплементируете свой Solver, замените это
        ArrayList<City> solution = solver.findShortestPath(pln.startIx, pln.endIx, pln.fm.cities, pln.fm.connections);
        List<ArrayList<City>> validSolutions = pln.fm.getSolutions();

        boolean correct = false;
        for(ArrayList<City> validSolution: validSolutions){
          if(validSolution.size() != solution.size()){continue;}
          boolean mistake = false;
          for(int i=0; i<validSolution.size(); i++){
            if(!validSolution.get(i).equals(solution.get(i))){
              mistake = true;
            }
          }
          if(!mistake){
            correct = true;
            break;
          }
        }
        if(correct){
          pln.solverSolution = solution;
          JOptionPane.showMessageDialog(null,"Ваш алгоритм нашел кратчайший путь");
        } else {
          pln.validSolution = validSolutions.get(new Random().nextInt(validSolutions.size()));
          pln.validSolution = validSolutions.get(new Random().nextInt(validSolutions.size()));
          JOptionPane.showMessageDialog(null,"Ваш алгоритм не нашел кратчайший путь");
        }
        pln.validate();
        pln.repaint();
      }
    });

    pln.setMinimumSize(pln.getPreferredSize());
    c.fill = GridBagConstraints.BOTH;
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 2;
    pane.add(pln, c);

    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 1;
    pane.add(pln.txt, c);

    c.gridx = 0;
    c.gridy = 2;
    c.gridwidth = 1;
    c.weightx = 0.5;
    pane.add(pln.restart, c);

    c.gridx = 1;
    c.gridy = 2;
    c.gridwidth = 1;
    c.weightx = 0.5;
    pane.add(pln.solve, c);


    window.pack();
    window.setMinimumSize(window.getSize());
    window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    window.setLocationRelativeTo(null);
    window.setVisible(true);
  }

  private void setSquareSize(){
    Planner pln = this;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    if(screenSize.getHeight() <= 1080.0){
      pln.squareSize = 6;
    } else {
      pln.squareSize = 9;
    }
  }

  private void generatePuzzle(){
    Planner pln = this;
    boolean foundGoodPuzzle = false;
    while(!foundGoodPuzzle){
      pln.fm = new FlightMap(n);
      foundGoodPuzzle = pln.fm.generatePuzzle();
    }
    pln.start = pln.fm.start;
    pln.startIx = pln.fm.startIx;
    pln.end = pln.fm.end;
    pln.endIx = pln.fm.endIx;
    pln.validSolution = new ArrayList<>();
    pln.solverSolution = new ArrayList<>();

    pln.txt.setText("Найдите кратчайший путь от " + pln.start.name + " до " + pln.end.name);
  }

  public Dimension getPreferredSize()
  {
    return new Dimension((int)Math.sqrt(n)*10*squareSize, (int)Math.sqrt(n)*10*squareSize);
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    g.setColor(new Color(0, 128, 0));
    for (int x = 0; x < fm.getMapWidth(); x++) {
      for (int y = 0; y < fm.getMapHeight(); y++) {
        if (fm.flightMap[y][x] == 0) {
          g.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);
        }
      }
    }

    g.setColor(Color.YELLOW);

    for (int i = 0; i < fm.connections.length; i++) {
      for (int j = 0; j < fm.connections[0].length; j++) {
        if (fm.connections[i][j] != 0) {
          City c1 = fm.cities.get(i);
          City c2 = fm.cities.get(j);
          g.drawLine(c1.x*squareSize, c1.y*squareSize, c2.x*squareSize, c2.y*squareSize);
        }
      }
    }

    if(startIx==-1){ return; }

    g.setColor(Color.BLUE);
    g.fillRect(start.x*squareSize, start.y*squareSize, squareSize, squareSize);
    g.setColor(Color.RED);
    g.fillRect(end.x*squareSize, end.y*squareSize, squareSize, squareSize);

    Graphics2D g2 = (Graphics2D)g;
    g2.setStroke(new BasicStroke(3));
    for(int i=0; i<validSolution.size()-1; i++){
      City c1 = validSolution.get(i);
      City c2 = validSolution.get(i+1);
      g2.drawLine(c1.x*squareSize, c1.y*squareSize, c2.x*squareSize, c2.y*squareSize);
    }

    g2.setColor(Color.BLUE);
    for(int i=0; i<solverSolution.size()-1; i++){
      City c1 = solverSolution.get(i);
      City c2 = solverSolution.get(i+1);
      g2.drawLine(c1.x*squareSize, c1.y*squareSize, c2.x*squareSize, c2.y*squareSize);
    }
  }

}
