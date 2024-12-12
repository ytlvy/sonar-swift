/**
 * java-lang - Enables analysis of Swift and Objective-C projects into SonarQube.
 * Copyright © 2015 sonar-next (${email})
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.sonar.next.sonarqube.java.issues.infer;

import com.backelite.sonarqube.commons.Constants;
import com.github.sonar.next.sonarqube.java.lang.core.Java;
import org.apache.tools.ant.DirectoryScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;

import java.io.File;

/**
 * @author magaofei
 * @date 2021/04/11
 */
public class JavaInferSensor implements Sensor {

    private static final Logger logger = LoggerFactory.getLogger(JavaInferSensor.class);
    public static final String REPORT_PATH_KEY = Constants.PROPERTY_JAVA_PREFIX + ".infer.report";
    public static final String DEFAULT_REPORT_PATH = "sonar-reports/*infer_report.json";

    private final SensorContext context;

    public JavaInferSensor(SensorContext context) {
        this.context = context;
    }

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor
                .onlyOnLanguage(Java.KEY)
                .name("Infer Java")
                .onlyOnFileType(InputFile.Type.MAIN);
    }

    @Override
    public void execute(SensorContext context) {
        final String projectBaseDir = context.fileSystem().baseDir().getAbsolutePath();

        InferReportParser parser = new InferReportParser(context);
        parseReportIn(projectBaseDir, parser);
    }


    private void parseReportIn(final String baseDir, final InferReportParser parser) {
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setIncludes(new String[]{reportPath()});
        scanner.setBasedir(baseDir);
        scanner.setCaseSensitive(false);
        scanner.scan();
        String[] files = scanner.getIncludedFiles();

        for (String filename : files) {
            logger.info("Processing Infer report {}", filename);
            parser.parseReport(new File(filename));
        }
    }

    private String reportPath() {
        return context.config()
                .get(REPORT_PATH_KEY)
                .orElse(DEFAULT_REPORT_PATH);
    }

}
