#Getting Started
* Download hadoop. I'm using version 1.0.4.
* Make sure that HADOOP_HOME and JAVA_HOME are properly defined.
* `./build.sh WordCount` will just compile the WordCount jar and will
run a local Hadoop MapReduce job on the sample files in
WordCount/WordCountIn. The results will be placed in WordCountOut. Make
sure to take a look at the scrip. It's written to be approachable.
* `./runall.sh` will run every example printing the name and printing
"Exception" if there was one.


If you want git to stop bothering you about the removed zip files, try git update-index --assume-unchanged -z
whatever.zip
