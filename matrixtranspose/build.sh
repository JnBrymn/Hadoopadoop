
mkdir matrixtranspose_classes 
javac -classpath ${HADOOP_HOME}/hadoop-core.jar -d matrixtranspose_classes MatrixTranspose.java 
jar -cvf matrixtranspose.jar -C matrixtranspose_classes/ .
