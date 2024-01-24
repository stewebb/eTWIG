# eTWIG - The event management software for Griffin Hall.
# copyright: Copyright (c) 2024 Steven Webb (Social Media Representative)
# license: MIT
# author: Steven Webb [xiaoancloud@outlook.com]
# website: https://etwig.grinecraft.net
# function: The main script for building and running the application.

#!/bin/bash
mkdir -p ./output

# ---- Java Build ----
mvn clean install -Dmaven.test.skip=true
cp ./target/etwig-*.jar ./output/etwig.jar

# ---- Python Build ----

# ---- Run Application ----
cd ./output
java -jar ./etwig.jar > "$(date '+%Y-%m-%d_%H-%M-%S').log" &
