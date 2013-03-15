for dir in `ls "."`
do
  if [ -d "$dir" ]; then
    echo
    echo $dir
    ./build.sh "$dir" 2>&1 | grep -o Exception
  fi
done
