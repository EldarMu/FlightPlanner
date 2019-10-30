package com.eldar;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class CityNameGenerator {
  private String[] prefixes =
      {"Helsinki", "Stockholm", "Malmo", "Oslo", "Gothenburg", "Bergen", "Copenhagen", "Athens",
      "Zagreb", "Budapest", "Prague", "Brno", "Hamburg", "Bremen", "Leipzig", "Berlin", "Dresden",
      "Cologne", "Frankfurt", "Stuttgart", "Munchen", "Amsterdam", "Rotterdam", "Utrecht", "Antwerp",
      "Ghent", "Brussels", "Luxembourg", "Liechtenstein", "Zurich", "Bern", "Geneva", "Lucerne",
      "Lausanne", "Interlaken", "Lugano", "Venice", "Milan", "Verona", "Genoa", "Turin", "Bologna",
      "Florence", "San Marino", "Rome", "Naples", "Sorrento", "Palermo", "Cagliari", "Nice",
      "Marseille", "Montpellier", "Cannes", "Toulouse", "Nantes", "Lyon", "Orleans", "Strasbourg",
      "Calais", "Paris", "London", "Bath", "Plymouth", "Brighton", "Southampton", "Canterbury",
      "Oxford", "Bristol", "Cardiff", "Leicester", "Nottingham", "Sheffield", "Manchester",
      "Liverpool", "Leeds", "York", "Birmingham", "Glasgow", "Edinburgh", "Aberdeen", "Belfast",
      "Dublin", "Limerick", "Kilkenny", "Cork", "Galway", "Barcelona", "Bilbao", "Valencia",
      "Madrid", "Salamanca", "Seville", "Granada", "Lisbon", "Porto", "Philadelphia", "New York",
      "Boston", "Cambridge", "Baltimore", "Savannah", "Orlando", "Tampa", "Miami", "Houston",
      "Dallas", "Austin", "San Diego", "Los Angeles", "San Francisco", "Santa Barbara", "San Jose",
      "Sacramento", "Las Vegas", "Denver", "Chicago", "Detroit", "Springfield", "Minneapolis",
      "Seattle", "Portland", "New Orleans", "Toronto", "Ottawa", "Montreal", "Vancouver", "Cancun",
      "Rio de Janeiro", "Sao Paulo", "Buenos Aires", "Honolulu", "Kyoto", "Tokyo", "Osaka", "Seoul",
      "Busan", "Manila", "Bangkok", "Sydney", "Melbourne", "Mumbai", "Muscat", "Dubai", "Cairo",
      "Casablanca", "Marrakesh"};
  private String[] vowelSuffixes = {"yevo", "novo", "nsk", "vskiy", "vskoye", "ievgrad", "grad"};
  private String[] consonantSuffixes = {"sk", "skiy", "ovo", "skoye", "ov", "iev",
      "ovskoye", "grad", "evskiy", "ievo"};
  private char[] vowels = {'a', 'e', 'i', 'o', 'u', 'y'};
  private Set<Character> vowelSet;
  Random rnd;
  public CityNameGenerator(){
    rnd = new Random();
    vowelSet = new HashSet<>();
    for(int i=0; i<vowels.length; i++){
      vowelSet.add(vowels[i]);
    }
  }
  String generate(){
    String prefix = prefixes[rnd.nextInt(prefixes.length)];
    if(vowelSet.contains(prefix.charAt(prefix.length()-1))){
      prefix += vowelSuffixes[rnd.nextInt(vowelSuffixes.length)];
    } else {
      prefix += consonantSuffixes[rnd.nextInt(consonantSuffixes.length)];
    }
    return prefix;
  }
}
