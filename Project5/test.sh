#!/bin/sh




# LOGIN

ssh -l student174 heinz-jumbo.heinz.cmu.local

#    S@1raj!




# Task 0 commands


javac -classpath  /usr/local/hadoop/hadoop-core-1.2.1.jar:./word_count_classes -d word_count_classes WordCount.java

jar -cvf wordCount.jar -C  word_count_classes/  .

hadoop dfs -rmr /user/student174/output



hadoop jar /home/student174/Project5/Part_1/Task0/wordCount.jar org.myorg.WordCount  /user/student174/input/words.txt /user/student174/output



hadoop dfs -copyFromLocal home/public/words.txt /user/student174/input/words.txt


hadoop dfs -getmerge /user/student174/output /home/student174/Project5/Part_1/Task0/Task0Output



hadoop dfs -cat /user/student174/output/part-r-00000

hdfs dfs -getmerge -nl /Hadoop_File/file1.txt /Hadoop_File/file2.txt /home/dikshant/Documents/hadoop_file/output.txt

hadoop job -kill job_202211081009_5253

hadoop job -kill job_202211081009_5246
job_202211081009_5246


hadoop dfs -cat /user/student174/output/part-r-00000


# Task 1

javac -classpath  /usr/local/hadoop/hadoop-core-1.2.1.jar:./letter_counter_classes -d letter_counter_classes LetterCounter.java

jar -cvf LetterCounter.jar -C  letter_counter_classes/  .


hadoop jar /home/student174/Project5/Part_1/Task1/LetterCounter.jar org.myorg.LetterCounter  /user/student174/input/words.txt /user/student174/output

hadoop dfs -getmerge /user/student174/output /home/student174/Project5/Part_1/Task1/Task1Output

sort -k 2nr Task1Output > tmp && mv tmp Task1Output


# Task 2

javac -classpath  /usr/local/hadoop/hadoop-core-1.2.1.jar:./fact_counter_classes -d fact_counter_classes FactCounter.java

jar -cvf FactCounter.jar -C  fact_counter_classes/  .

hadoop dfs -rmr /user/student174/output


hadoop jar /home/student174/Project5/Part_1/Task2/FactCounter.jar org.myorg.FactCounter  /user/student174/input/words.txt /user/student174/output

hadoop dfs -getmerge /user/student174/output /home/student174/Project5/Part_1/Task2/Task2Output

sort -k 2nr Task1Output > tmp && mv tmp Task1Output


# Task 3
javac -classpath  /usr/local/hadoop/hadoop-core-1.2.1.jar:./temperature -d temperature *.java

jar -cvf temperature.jar -C  temperature/  .

hadoop dfs -copyFromLocal /home/public/combinedYears.txt /user/student174/input/combinedYears.txt


hadoop jar /home/student174/Project5/Part_1/Task3/temperature.jar edu.cmu.andrew.mm6.MaxTemperature  /user/student174/input/combinedYears.txt /user/student174/output

hadoop dfs -getmerge /user/student174/output /home/student174/Project5/Part_1/Task3/Task3Output


 /home/public/combinedYears.txt


# Task 4
javac -classpath  /usr/local/hadoop/hadoop-core-1.2.1.jar:./temperature -d temperature *.java

jar -cvf temperature.jar -C  temperature/  .

hadoop dfs -copyFromLocal /home/public/combinedYears.txt /user/student174/input/combinedYears.txt


hadoop jar /home/student174/Project5/Part_1/Task4/temperature.jar edu.cmu.andrew.mm6.MinTemperature  /user/student174/input/combinedYears.txt /user/student174/output

hadoop dfs -getmerge /user/student174/output /home/student174/Project5/Part_1/Task4/Task4Output


 /home/public/combinedYears.txt


 # Task 5

javac -classpath  /usr/local/hadoop/hadoop-core-1.2.1.jar:./rapesplusrobberies -d rapesplusrobberies RapesPlusRobberiesCounter.java
jar -cvf RapesPlusRobberiesCounter.jar -C  rapesplusrobberies/  .

hadoop dfs -copyFromLocal /home/public/P1V.txt /user/student174/input/P1V.txt

hadoop jar /home/student174/Project5/Part_1/Task5/RapesPlusRobberiesCounter.jar org.myorg.RapesPlusRobberiesCounter  /user/student174/input/P1V.txt /user/student174/output

hadoop dfs -getmerge /user/student174/output /home/student174/Project5/Part_1/Task5/Task5Output


 # Task 6
 hadoop dfs -rmr /user/student174/output


javac -classpath  /usr/local/hadoop/hadoop-core-1.2.1.jar:./oaklandcrimestats -d oaklandcrimestats OaklandCrimeStatsCounter.java
jar -cvf OaklandCrimeStatsCounter.jar -C  oaklandcrimestats/  .

hadoop dfs -copyFromLocal /home/public/P1V.txt /user/student174/input/P1V.txt

hadoop jar /home/student174/Project5/Part_1/Task6/OaklandCrimeStatsCounter.jar org.myorg.OaklandCrimeStatsCounter  /user/student174/input/P1V.txt /user/student174/output

hadoop dfs -getmerge /user/student174/output /home/student174/Project5/Part_1/Task6/Task6Output


 # Task 7

 hadoop dfs -copyFromLocal /home/public/CrimeLatLonXYTabs.txt /user/student174/input/CrimeLatLonXYTabs.txt

 hadoop dfs -rmr /user/student174/output


javac -classpath  /usr/local/hadoop/hadoop-core-1.2.1.jar:./oaklandcrimestatskml -d oaklandcrimestatskml OaklandCrimeStatsCounterKML.java
jar -cvf oaklandcrimestatskml.jar -C  oaklandcrimestatskml/  .


hadoop jar /home/student174/Project5/Part_1/Task7/oaklandcrimestatskml.jar org.myorg.OaklandCrimeStatsCounterKML  /user/student174/input/CrimeLatLonXYTabs.txt /user/student174/output

hadoop dfs -getmerge /user/student174/output /home/student174/Project5/Part_1/Task7/Task7Output





