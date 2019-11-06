package com.eldar.solvers;

import com.eldar.City;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SolverMGS implements Solver {
    public ArrayList<City> findShortestPath(int startCity, int endCity, List<City> cities, int[][] connections) {
        int[] pathLen = new int[cities.size()];//пути присваиваемые вершине
        boolean[] visited = new boolean[cities.size()]; // посещена вершина или нет
        for (int i = 0; i < cities.size(); i++)
        {
            pathLen[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }
        pathLen[startCity] = 0;
        for (int count = 0; count < cities.size() - 1; count++)
        {
            int i = minDistance(cities, pathLen, visited);
            visited[i] = true;
            for (int j = 0; j < cities.size(); j++)
            {
                if ((!visited[j] && connections[i][j] != 0) && (pathLen[i] != Integer.MAX_VALUE) && (pathLen[i] + connections[i][j] < pathLen[j]))
                {
                    pathLen[j] = pathLen[i] + connections[i][j];
                }
            }
        }
        return spotWay(cities,endCity,pathLen,connections,startCity);
    }
    public ArrayList<City> spotWay(List<City> cities, int end_city, int[] pathLen, int[][] connections, int start_city) {
        ArrayList<City> points = new ArrayList<>();
        points.add(cities.get(end_city));
        int k = 1;
        int weight = pathLen[end_city];
        while (end_city != start_city)
        {
            for (int i = 0; i < cities.size(); i++)
                if (connections[end_city][i] != 0)
                {
                    int tmp = weight - connections[end_city][i];
                    if (tmp == pathLen[i])
                    {                 
                        weight = tmp;
                        end_city = i;
                        points.add(cities.get(i));
                        k++;
                    }
                }
        }
        for(int i = 0, j = points.size() - 1; i < j; i++) {
            points.add(i, points.remove(j));
        }
        return points;
    }
    int minDistance(List<City> cities, int[] pathLen, boolean[] visited)
    {
        int min = Integer.MAX_VALUE, min_index = -1;
        for (int i = 0; i < cities.size(); i++)
            if (visited[i] == false && pathLen[i] <= min)
            {
                min = pathLen[i];
                min_index = i;
            }
        return min_index;
    }
}

