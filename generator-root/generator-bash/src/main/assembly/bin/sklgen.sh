#!/bin/sh


###############################################################################
#################### sh script to execute generator-bash ######################
###############################################################################


SKLGEN_VERSION=3.0.0-M3
SKLGEN_RUNNABLE_JAR="$SKLGEN_HOME/boot/generator-bash-$SKLGEN_VERSION.jar"
SKLGEN_LIB=$SKLGEN_HOME/lib
SKLGEN_CLASSPATH=$SKLGEN_RUNNABLE_JAR:$SKLGEN_LIB/*

RUNNABLE_CLASS="org.sklsft.generator.bash.launcher.MainLauncher"

echo current directory : $PWD
echo generator home : $SKLGEN_HOME
echo version : $SKLGEN_VERSION
echo java home : $JAVA_HOME

SKLGEN_CMD_LINE_ARGS=$1
DATABASE=$2

RUN(){
  echo "start sklgen"
  echo "to exec: $JAVA_HOME/bin/java -classpath $SKLGEN_CLASSPATH $RUNNABLE_CLASS $SKLGEN_CMD_LINE_ARGS $PWD $DATABASE"
  eval "$JAVA_HOME/bin/java -classpath $SKLGEN_CLASSPATH $RUNNABLE_CLASS $SKLGEN_CMD_LINE_ARGS $PWD $DATABASE"
  echo "end sklgen"
  END
}



ERROR(){
  echo "FAILED"
  exit 1
}

END(){
  echo "END"
}

# ==== START VALIDATION ====
if [ ! -z "$JAVA_HOME" ]; then

  if [ -f "$JAVA_HOME/bin/java" ]; then

    if [ ! -z "$SKLGEN_HOME" ]; then

      if [ -f "$SKLGEN_HOME/bin/sklgen.sh" ]; then
        RUN
      else
        echo "ERROR: SKLGEN_HOME is set to an invalid directory."
        echo "SKLGEN_HOME = $SKLGEN_HOME"
        echo "Please set the SKLGEN_HOME variable in your environment to match the"
        echo "location of your skeleton generator installation"
        ERROR
      fi

    else
      echo "ERROR: SKLGEN_HOME not found in your environment."
      echo "Please set the SKLGEN_HOME variable in your environment to match the"
      echo "location of the Skeleton generator installation"
      ERROR
    fi

  else
    echo "ERROR: JAVA_HOME is set to an invalid directory."
    echo "JAVA_HOME = $JAVA_HOME"
    echo "Please set the JAVA_HOME variable in your environment to match the"
    echo "location of your Java installation"
    ERROR
  fi

else
  echo "ERROR: JAVA_HOME not found in your environment."
  echo "Please set the JAVA_HOME variable in your environment to match the"
  echo "location of your Java installation"
  ERROR
fi
