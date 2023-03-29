// Sai Rajuladevi, srajulad
/**
 * This code demonstrates different Spark functionalities
 * used on the input file Tempest.txt
 */

package cmu.edu;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Command line arguments.
 *
 * @param args[0] text file string
 */

public class TempestAnalytics {
    // Run a basic count on the number of lines in the file
    private static void task0(String fileName, JavaSparkContext sparkContext) {
        JavaRDD<String> inputFile = sparkContext.textFile(fileName);
        System.out.println("Number of lines in The Tempest: " + inputFile.count());
    }

    // Run a count on the number of words in the file
    private static void task1(String fileName, JavaSparkContext sparkContext) {
        JavaRDD<String> inputFile = sparkContext.textFile(fileName);
        JavaRDD<String> wordsFromFile = inputFile.flatMap(content -> Arrays.asList(content.split("[^a-zA-Z]+")));
        // Use function to map a boolean to count the word splits
        Function<String, Boolean> filter = k -> ( !k.isEmpty());
        System.out.println("Number of words in The Tempest: " + wordsFromFile.filter(filter).count());
    }

    // Count distinct words
    private static void task2(String fileName, JavaSparkContext sparkContext) {
        JavaRDD<String> inputFile = sparkContext.textFile(fileName);
        JavaRDD<String> wordsFromFile = inputFile.flatMap(content -> Arrays.asList(content.split("[^a-zA-Z]+")));
        Function<String, Boolean> filter = k -> ( !k.isEmpty());
        // basically the same as the previous function, however .distinct() gets rid of duplicate words
        System.out.println("Number of distinct words in The Tempest: " + wordsFromFile.filter(filter).distinct().count());
    }

    // Count symbols in the text file
    private static void task3(String fileName, JavaSparkContext sparkContext) {
        JavaRDD<String> inputFile = sparkContext.textFile(fileName);
        // Split by an empty string to get all the chars
        JavaRDD<String> wordsFromFile = inputFile.flatMap(content -> Arrays.asList(content.split("")));
        Function<String, Boolean> filter = k -> ( !k.isEmpty());
        // run a simple count on the splits
        System.out.println("Number of symbols in The Tempest: " + wordsFromFile.filter(filter).count());
    }

    // Count distinct symbols in the text file
    private static void task4(String fileName, JavaSparkContext sparkContext) {
        JavaRDD<String> inputFile = sparkContext.textFile(fileName);
        JavaRDD<String> wordsFromFile = inputFile.flatMap(content -> Arrays.asList(content.split("")));
        Function<String, Boolean> filter = k -> ( !k.isEmpty());
        // basically the same as the previous function, however .distinct() gets rid of duplicate symbols
        System.out.println("Number of symbols in The Tempest: " + wordsFromFile.filter(filter).distinct().count());
    }

    // Count distinct symbols in the text file
    private static void task5(String fileName, JavaSparkContext sparkContext) {
        JavaRDD<String> inputFile = sparkContext.textFile(fileName);
        JavaRDD<String> wordsFromFile = inputFile.flatMap(content -> Arrays.asList(content.split("")));
        // use 2 filters, first to check if it's not an empty character
        Function<String, Boolean> filter = k -> ( !k.isEmpty() );

        // second filter checks if the letter is in between [a-zA-Z]
        Function<String, Boolean> filter2 = k -> ( !( (k.charAt(0) >= 'a' && k.charAt(0) <= 'z') ||
                (k.charAt(0) >= 'A' && k.charAt(0) <= 'Z') ) );

        // basically the same as the previous function, however filter twice
        // and .distinct() gets rid of duplicate symbols
        System.out.println("Number of distinct letters in The Tempest: " + wordsFromFile.filter(filter).filter(filter2).distinct().count());
    }

    // Count lines matching with word in the text file
    private static void task6(String fileName, String in, JavaSparkContext sparkContext) {
        JavaRDD<String> inputFile = sparkContext.textFile(fileName);
        JavaRDD<String> wordsFromFile = inputFile.flatMap(content -> Arrays.asList(content.split("\n")));
        // filter based contained words
        Function<String, Boolean> filter = k -> ( k.contains(in) );
        // iterate using this match, and output to console
        for(String line:wordsFromFile.filter(filter).collect()){
            System.out.println(line);
        }

    }
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("Tempest Analytics");
        JavaSparkContext sparkContext = new JavaSparkContext(sparkConf);

        if (args.length == 0) {
            System.out.println("No files provided.");
            System.exit(0);
        }
        task0(args[0], sparkContext);
        task1(args[0], sparkContext);
        task2(args[0], sparkContext);
        task3(args[0], sparkContext);
        task4(args[0], sparkContext);
        task5(args[0], sparkContext);

        // add scanner functionality for task 6
        System.out.println("Enter a search word: ");
        Scanner input = new Scanner(System.in);
        task6(args[0], input.nextLine(), sparkContext);
    }
}