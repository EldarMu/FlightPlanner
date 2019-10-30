package com.eldar.solvers;

import com.eldar.City;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

// SolverEEM является простым решением, как пример.
// Пожалуйста добавляйте ваши решения таким же образом (SolverИОФ implements Solver).
// Очень далеко от идеального так как оно находит все пути рекурсивно (обход по глубине)
// а затем разбирается в том который из них самый короткий. Если несколько коротких, выбирает любой.
public class SolverEEM implements Solver {
  public ArrayList<City> findShortestPath(int startCity, int endCity, List<City> cities, int[][] connections){
    List<ArrayList<City>> paths = new LinkedList<>();
    findPaths(new HashSet<>(), new LinkedList<>(), cities.get(startCity), paths, cities.get(endCity));
    int shortestPathLength = 0;
    ArrayList<City> shortestPath = new ArrayList<>();
    for(ArrayList<City> path : paths){
      int pathLen = 0;
      for(int i=0; i<path.size()-1; i++){
        pathLen += path.get(i).flightLengths.get(path.get(i+1));
      }
      if(shortestPathLength == 0 || pathLen < shortestPathLength){
        shortestPath = path;
        shortestPathLength = pathLen;
      }
    }
    return shortestPath;
  }

  private void findPaths(Set<City> visited, List<City> curPath, City curCity, List<ArrayList<City>> paths, City end){
    curPath.add(curCity);
    visited.add(curCity);
    if(curCity == end) {
      ArrayList<City> t = new ArrayList<>(curPath.size());
      for(City c : curPath){
        t.add(c);
      }
      paths.add(t);
    } else {
      for(City c : curCity.connections){
        if(!visited.contains(c)){
          findPaths(visited, curPath, c, paths, end);
        }
      }
    }
    visited.remove(curCity);
    curPath.remove(curPath.size()-1);
  }

}
