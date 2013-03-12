if [ $# -eq 0 ] ; then
  echo 'This function expects one argument, the name of the example to execute.'
  echo 'This will compile the appropriate java class, run a demo MapReduce job locally'
  echo 'and put the results into `${name}/${name}Out`'
  exit 0
fi

name=$1

cd ${name}
rm ${name}.jar
rm -fr ${name}Classes
rm -fr ${name}Out
mkdir ${name}Classes 
javac -classpath ${HADOOP_HOME}/hadoop*-*core*.jar -d ${name}Classes ${name}.java 
jar -cvf ${name}.jar -C ${name}Classes/ .
${HADOOP_HOME}/bin/hadoop jar ${name}.jar org.myorg.${name} ${name}In/ ${name}Out
cd -
