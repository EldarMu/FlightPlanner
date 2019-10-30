package com.eldar.solvers;

import com.eldar.City;
import java.util.ArrayList;
import java.util.List;

public interface Solver {
  // findShortestPath список городов включает в себя начальный и конечный город. данные предоставлены
  // и в форме списка всех городов и в форме матрицы связности, чтобы был выбор как имплементировать.
  public ArrayList<City> findShortestPath(int startCity, int endCity, List<City> cities, int[][] connections);
}
