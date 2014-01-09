#!/bin/sh

echo "************ UNDEPLOYING *******************"
asadmin undeploy jee_proj
echo "************ BUILDING **********************"
mvn package
echo "************ DEPLOYING *********************"
asadmin deploy target/jee_proj.war
