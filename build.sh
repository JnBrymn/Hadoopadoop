if [ $# -eq 0 ] ; then
  echo 'This function expects one argument, the name of the example to execute.'
  echo 'This will compile the appropriate java class, run a demo MapReduce job locally'
  echo 'and put the results into `${name}/${name}Out`'
  exit 0
fi

name=${1%/}

echo "Unzipping contents in ${name}/${name}In"
pushd .
cd ${name}/${name}In
unzip -o *.zip
rm *.zip
echo "Building contents of ${name}/${name}Src"
cd ..
rm ${name}.jar
rm -fr ${name}Classes
rm -fr ${name}Out
mkdir ${name}Classes
javac -classpath ${HADOOP_HOME}/hadoop*-*core*.jar -d ${name}Classes ${name}Src/*.java 
echo "Jarring"
jar -cvf ${name}.jar -C ${name}Classes/ .
echo "Running Hadoop Job"
echo "hadoop jar ${name}.jar org.myorg.${name} ${name}In/ ${name}Out"
${HADOOP_HOME}/bin/hadoop jar ${name}.jar org.myorg.${name} ${name}In/ ${name}Out
popd
