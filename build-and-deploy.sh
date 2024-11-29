#!/bin/sh
#
# Swift SonarQube Plugin - Enables analysis of Swift and Objective-C projects into SonarQube.
# Copyright © 2015 sonar-next (${email})
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU Lesser General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
#

###
 # @Author: gyj yanjie.guo@kuwo.cn
 # @Date: 2024-11-30 00:26:22
 # @LastEditors: gyj yanjie.guo@kuwo.cn
 # @LastEditTime: 2024-11-30 00:47:47
 # @FilePath: /sonar-swift/build-and-deploy.sh
 # @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
### 
#
# Swift SonarQube Plugin - Enables analysis of Swift and Objective-C projects into SonarQube.
# Copyright © 2015 sonar-next (${email})
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU Lesser General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public License
# along with this program.  If not, see <http://www.gnu.org/licenses/>.
#

# Build and install snapshot plugin in Sonar

# Build first and check status
mvn clean license:format install -DskipTests
if [ "$?" != 0 ]; then
	echo "ERROR - Java build failed!" 1>&2
	exit $?
fi

# Run shell surefire
#shelltest src/test/shell --execdir --diff
#if [ "$?" != 0 ]; then
#	echo "ERROR - Shell surefire failed!" 1>&2
#	exit $?
#fi

# Deploy new version of plugin in Sonar dir
rm sonar-swift-plugin/target/*sources.jar
rm $SONARQUBE_HOME/extensions/plugins/backelite-sonar-swift*
cp sonar-swift-plugin/target/backelite*.jar $SONARQUBE_HOME/extensions/plugins
rm $SONARQUBE_HOME/extensions/plugins/*sources.jar

# Stop/start Sonar
unset GEM_PATH GEM_HOME
$SONARQUBE_HOME/bin/macosx-universal-64/sonar.sh stop
$SONARQUBE_HOME/bin/macosx-universal-64/sonar.sh start

