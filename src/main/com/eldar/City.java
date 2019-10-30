package com.eldar;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class City {
  public int x;
  public int y;
  public String name;
  public List<City> connections;
  public Map<City, Integer> flightLengths;
  public City(int x, int y){
    this.x = x;
    this.y = y;
    CityNameGenerator cng = new CityNameGenerator();
    name = cng.generate();
    connections = new LinkedList<>();
    flightLengths = new HashMap<>();
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;   //If objects equal, is OK
    if (o instanceof City) {
      City c2 = (City)o;
      return x == c2.x && y == c2.y && name.equals(c2.name);
    }
    return false;
  }
}
