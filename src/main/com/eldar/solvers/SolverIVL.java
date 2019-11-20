package com.eldar.solvers;

import com.eldar.City;

import java.util.*;

public class SolverIVL implements Solver {
    public ArrayList<City> findShortestPath(int src, int end_apex, List<City> cities, int[][] connections){
        List<ArrayList<City>> paths = new LinkedList<>();
        ArrayList<City> shortestPath = new ArrayList<>();
        ArrayList<Integer> way = new ArrayList<>();
        int shortestPathLength = 0;
        int[] dist = new int[cities.size()]; //пути присваиваемые вершине
        boolean[] sptSet = new boolean[cities.size()]; // посещена вершина или нет
        for (int i = 0; i < cities.size(); i++) {
            dist[i] = Integer.MAX_VALUE;
            sptSet[i] = false;
        }
        dist[src] = 0;  // src - начальная вершина
        for (int count = 0; count < cities.size() - 1; count++) {
            int u = minDistance(cities, dist, sptSet); // индекс вершины из которой ищем путь (меняется)
            sptSet[u] = true;
            for (int v = 0; v < cities.size(); v++) // dist v - вес следующей вершины изначально бесконечность
            {
                if ((!sptSet[v] && connections[u][v] != 0) && (dist[u] != Integer.MAX_VALUE) && (dist[u] + connections[u][v] < dist[v])) {
                    dist[v] = dist[u] + connections[u][v]; // присваиваем вершине вес ребра + вес той из которой пришли
                }
            }
        }
        if (dist[end_apex] != Integer.MAX_VALUE) {
            shortestPath = discoverRealWay(cities, end_apex, dist, connections, paths, src); // вычитаем вес текущей из рассчитаного и сравниваем с весом предыдущей
        }
        return shortestPath;
    }

    int minDistance(List<City> cities, int[] dist, boolean[] sptSet) {
        int min = Integer.MAX_VALUE, min_index = -1;
        for (int v = 0; v < cities.size(); v++)
            if (sptSet[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        return min_index;
    }


    public ArrayList<City> discoverRealWay(List<City> cities, int end_apex, int[] dist, int[][] connections, List<ArrayList<City>> paths, int begin_apex){
        Integer[] necessary_points = new Integer[cities.size()]; // массив посещенных вершин
        ArrayList<City> path = new ArrayList<>();
        path.add(cities.get(end_apex)); // начальный элемент - конечная вершина
        int k = 1; // индекс предыдущей вершины
        int weight = dist[end_apex]; // вес конечной вершины
        while (end_apex != begin_apex) // пока не дошли до начальной вершины
        {
            for (int i = 0; i < cities.size(); i++) // просматриваем все вершины
                if (connections[end_apex][i] != 0)   // если связь есть
                {
                    int tmp = weight - connections[end_apex][i]; // определяем вес пути из предыдущей вершины
                    if (tmp == dist[i]) // если вес совпал с рассчитанным
                    {                 // значит из этой вершины и был переход
                        weight = tmp; // сохраняем новый вес
                        end_apex = i;       // сохраняем предыдущую вершину
                        path.add(cities.get(i));
                        k++;
                    }
                }
        }
        Collections.reverse(path);
        return path;
    }
}



