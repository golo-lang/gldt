if [ -d golo-lang.tmp/.git ]; then
  cd golo-lang.tmp
  git pull origin
  cd ..
else
  rm -rf golo-lang.tmp
  git clone git@github.com:golo-lang/golo-lang.git golo-lang.tmp
fi

#
# check source and target dir for differences
#
# $1 is the relative path in the source
# $2 is the relative path in the target
# $3 is the sub directory to check
#
function check {
for i in `ls golo-lang.tmp/$1/$3`
do
if [ ! -f org.golo-lang.gldt.core.tests/$2/$3/$i ]; then
  echo $3/$i new !!!
else
  diff golo-lang.tmp/$1/$3/$i org.golo-lang.gldt.core.tests/$2/$3/$i >/dev/null 2>&1
  if [ $? -eq 1 ]; then
    echo $3/$i modified
  fi
fi
done
}

check src/test/resources src/main/resources for-execution
check src/test/resources src/main/resources for-parsing-and-compilation
check src/test/resources src/main/resources for-test
check . src/main/resources samples
