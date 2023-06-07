import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class StreamApiTasks {

  public static void main(String[] args) {
    longestWords();
  }

  private static void filterAListOfStringsAndMapToUpperCase() {
    Character letter = 'w';
    List<String> strings = Arrays.asList("This is going", "to be", "Legen", "wait for it", "Dary!");
    System.out.println("Printing list before performing a task");
    printAList(strings);
    strings = strings.stream()
        .filter(it -> !it.startsWith(String.valueOf(letter)))
        .map(it -> it.toUpperCase(Locale.ROOT))
        .collect(Collectors.toList());
    System.out.println("Printing list after performing a task");
    printAList(strings);
  }

  private static void averageOfTheSquaresOfEvenNumbers() {
    List<Integer> integers = Arrays.asList(1, 5, 33, 4, 54, 87, 97, 27, 21, 57, 77, 667, 45, 87, 2);
    List<Integer> evenIntegers = integers.stream()
        .filter(it -> it % 2 == 0)
        .collect(Collectors.toList());
    double sumSquare = evenIntegers.stream()
        .reduce(0, (subtotal, element) -> subtotal + element * element);
    System.out.println("Result:" + sumSquare / evenIntegers.size());
  }

  private static void wordCounter() {
    try {
      List<String> words = readWords()
          .stream()
          .map(String::toLowerCase)
          .collect(Collectors.toList());
      Map<String, Integer> wordsOccurrences = new HashMap<>();
      words.forEach(it -> wordsOccurrences.put(it, 0));

      wordsOccurrences.keySet()
          .forEach(word -> wordsOccurrences
              .compute(word, (key, value) -> Math.toIntExact(words.stream()
                  .filter(it -> it.equals(word)).count())));

      System.out.println("Words occurrences:");
      sortAndPrintAMap(wordsOccurrences);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void wordByLengthMap() {
    try {
      List<String> words = readWords();
      Map<Integer, List<String>> wordsByLength = new HashMap<>();
      words.forEach(it -> wordsByLength.put(it.length(), Collections.emptyList()));
      wordsByLength.keySet().forEach(length ->
          wordsByLength.compute(length, (key, value) ->
              words.stream().filter(it -> it.length() == length).collect(Collectors.toList())
          )
      );
      System.out.println("Words occurrences:");
      printAMap(wordsByLength);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void twoLongestWords() {
    try {
      List<String> words = readWords();
      words = words.stream()
          .sorted((a, b) -> b.length() - a.length())
          .limit(2)
          .collect(Collectors.toList());
      System.out.println("Longest words:");
      printAList(words);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void maximumValue() {
    List<Integer> integers = Arrays.asList(1, 5, 33, 4, 54, 87, 97, 27, 21, 57, 77, 667, 45, 87, 2);
    List<Integer> integers2 = Collections.emptyList();
    Optional<Integer> maxValue = Optional.ofNullable(integers.stream().reduce(0, Integer::max));

    if (maxValue.isPresent()) {
      System.out.println("Maximum value: " + maxValue.get());
    } else {
      System.out.println("List is empty");
    }
  }

  private static void findAWord() {
    try {
      List<String> words = readWords();
//      words = Collections.emptyList();
      String neededWord = "api";

      Optional<String> foundWord = words.stream()
          .filter(it -> it.equalsIgnoreCase(neededWord))
          .findFirst();

      if (foundWord.isPresent()) {
        System.out.println("Found word: " + foundWord.get());
      } else {
        System.out.println("Word " + neededWord + " is not found in the list");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void averageLengthOfTheWords() {
    try {
      List<String> words = readWords();
//      words = Collections.emptyList();
      OptionalDouble averageOfTheWords = words.stream().mapToInt(String::length).average();
      if (averageOfTheWords.isPresent()) {
        System.out.println("Average length of the words: " + averageOfTheWords.getAsDouble());
      } else {
        System.out.println("Sentence is an empty");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void longestWords() {
    try {
      List<String> words = readWords();
//      words = Collections.emptyList();
      Optional<String> longestWord = words.stream().max(Comparator.comparingInt(String::length));
      if (longestWord.isPresent()) {
        System.out.println("Longest word: " + longestWord.get());
      } else {
        System.out.println("Sentence is an empty");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static List<String> readWords() throws IOException {
    URL url = StreamApiTasks.class.getResource("text.txt");
    File file = new File(Objects.requireNonNull(url).getPath());
    BufferedReader br = new BufferedReader(new FileReader(file));
    List<String> words = br.lines()
        .flatMap(line -> Arrays.stream(line.split(" "))
            .map(word -> word.replaceAll("[^a-zA-Z]", "")))
        .filter(it -> !it.isEmpty())
        .collect(Collectors.toList());
    System.out.println("All words:");
    printAList(words);
    return words;
  }

  private static void printAList(List strings) {
    System.out.print("[");
    for (int i = 0; i < strings.size(); i++) {
      System.out.print(strings.get(i));
      if (i < strings.size() - 1) {
        System.out.print(", ");
      }
    }
    System.out.println("]");
  }

  private static void sortAndPrintAMap(Map<String, Integer> wordsOccurrences) {
    List<Entry> entrySet = wordsOccurrences.entrySet().stream()
        .sorted((a, b) -> b.getValue() - a.getValue()).collect(Collectors.toList());
    printEntrySet(entrySet);
  }

  private static void printAMap(Map wordsOccurrences) {
    printEntrySet((List<Entry>) new ArrayList<>(wordsOccurrences.entrySet()));
  }

  private static void printEntrySet(List<Entry> entries) {
    System.out.print("{");
    for (int i = 0; i < entries.size(); i++) {
      Entry currentEntry = entries.get(i);
      System.out.print(currentEntry.getKey() + ": " + currentEntry.getValue());
      if (i < entries.size() - 1) {
        System.out.print(", ");
      }
    }
    System.out.println("}");
  }
}
