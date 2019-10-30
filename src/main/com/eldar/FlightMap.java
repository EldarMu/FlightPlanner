package com.eldar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class FlightMap {
  private static double p = 0.01;
  int[][] connections;
  int[][] flightMap;
  List<City> cities;
  Random rnd;

  public City start;
  public int startIx;
  public City end;
  public int endIx;
  private List<ArrayList<City>> solutions;


  public FlightMap(int n) {
    connections = new int[n][n];
    rnd = new Random();

    cities = new ArrayList<>(n);
    int sqrt = (int)Math.sqrt(n);
    flightMap = new int[10*sqrt][10*sqrt];
    for(int i=0; i<10*sqrt; i+=10){
      for(int j=0; j<10*sqrt; j+=10){
        int x = rnd.nextInt(10)+j;
        int y = rnd.nextInt(10)+i;
        flightMap[y][x] = 1;
        City c = new City(x, y);
        cities.add(c);
      }
    }

    for(int i=0; i<n; i++){
      for(int j=0; j<n; j++){
        float t = rnd.nextFloat();
        if(i != j && connections[i][j] == 0 && t <= p){
          City c1 = cities.get(i);
          City c2 = cities.get(j);

          int dist = getDistance(c1, c2);
          connections[i][j] = dist;
          connections[j][i] = dist;
          c1.connections.add(c2);
          c1.flightLengths.put(c2, dist);
          c2.connections.add(c1);
          c2.flightLengths.put(c1, dist);
        }
      }
    }
  }

  // generatePuzzle создает новую задачку для решения вашим алгоритмом. Так как построение
  // графы пробабилистичное, есть шанс (очень низкий) что созданная графа не будет способна
  // предоставить адекватную задачку, в котором случае мы возвращаем false.
  public boolean generatePuzzle(){
    boolean foundGoodPuzzle = false;
    for(int tries=30; tries>0; tries--) {
      City tempStart = new City(-1,-1);
      int tempStartIx = -1;
      for(int i=0; i<cities.size()/4;i++){
        int t = rnd.nextInt(cities.size()/4);
        City curCity = cities.get(t);
        if(curCity.connections.size()>0){
          tempStart = curCity;
          tempStartIx = t;
          break;
        }
      }
      if(tempStartIx==-1){continue;}
      City tempEnd = new City(-1,-1);
      int tempEndIx = -1;
      for(int i=cities.size()-1; i>=cities.size()*3/4;i--){
        City curCity = cities.get(i);
        if(curCity.connections.size()>0 && !curCity.flightLengths.containsKey(start)){
          tempEnd = curCity;
          tempEndIx = i;
          break;
        }
      }
      if(tempEndIx==-1){continue;}
      List<ArrayList<City>> paths = new LinkedList<>();

      findPaths(new HashSet<>(), new LinkedList<>(), tempStart, paths, tempEnd);
      if(paths.size()==0){continue;}
      solutions = findShortestPaths(paths);
      start = tempStart;
      end = tempEnd;
      startIx = tempStartIx;
      endIx = tempEndIx;
      foundGoodPuzzle=true;
      break;
    }
    return foundGoodPuzzle;
  }

  private List<ArrayList<City>> findShortestPaths(List<ArrayList<City>> paths){
    List<ArrayList<City>> shortestPaths = new LinkedList<>();
    int shortestLength = 0;
    for(ArrayList<City> path : paths) {
      int tempLen = 0;
      for(int i=0; i<path.size()-1; i++){
        tempLen += path.get(i).flightLengths.get(path.get(i+1));
      }
      if(shortestLength==0 || tempLen<shortestLength){
        shortestPaths.clear();
        shortestLength=tempLen;
        shortestPaths.add(path);
      } else if(tempLen==shortestLength){
        shortestPaths.add(path);
      }
    }
    return shortestPaths;
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

  public List<ArrayList<City>> getSolutions(){
    return solutions;
  }

  public int getDistance(City c1, City c2){
    double dx = Math.pow(c1.x-c2.x, 2.0);
    double dy = Math.pow(c1.y-c2.y, 2.0);
    return (int)Math.sqrt(dx+dy)*100;
  }

  int getMapWidth(){
    return flightMap[0].length;
  }

  int getMapHeight(){
    return flightMap.length;
  }

}
