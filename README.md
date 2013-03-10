wget https://s3.amazonaws.com/o19s-experiments/wordcount/TomSawyer.txt
wget https://s3.amazonaws.com/o19s-experiments/wordcount/HuckleberryFinn.txt
$HADOOP_HOME/bin/hadoop dfs -copyFromLocal ./* books
bin/hadoop jar JOHN/Hadoopadoop/matrixtranspose/matrixtranspose.jar org.myorg.MatrixTranspose trash howitworks
