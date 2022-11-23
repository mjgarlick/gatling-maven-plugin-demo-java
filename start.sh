#!/bin/sh
VARS="-DUSERS=$USERS -DTYPE_OF_TEST=$TYPE_OF_TEST -DDURATION=$DURATION -DRAMP_DURATION=$RAMP_DURATION -DDOMAIN=$DOMAIN"
echo "Simulating against $DOMAIN with a $SIMULATION_CLASS simulation, using $USERS users for a $TYPE_OF_TEST type simulation"
java $VARS -jar app.jar -s $SIMULATION_CLASS