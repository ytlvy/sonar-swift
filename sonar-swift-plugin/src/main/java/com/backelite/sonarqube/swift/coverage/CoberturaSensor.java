/**
 * sonar-swift-plugin - Enables analysis of Swift and Objective-C projects into SonarQube.
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
package com.backelite.sonarqube.swift.coverage;

import com.backelite.sonarqube.commons.Constants;
import com.backelite.sonarqube.objectivec.lang.core.ObjectiveC;
import com.backelite.sonarqube.swift.lang.core.Swift;
import org.apache.tools.ant.DirectoryScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;

import java.io.File;

public class CoberturaSensor implements Sensor {
    private static final Logger LOGGER = LoggerFactory.getLogger(CoberturaSensor.class);
    public static final String REPORT_PATTERN_KEY = Constants.PROPERTY_PREFIX + ".coverage.reportPattern";
    public static final String DEFAULT_REPORT_PATTERN = "sonar-reports/coverage*.xml";
    private final SensorContext context;

    public CoberturaSensor(final SensorContext context) {
        this.context = context;
    }

    private String reportPath() {
        return context.config()
            .get(REPORT_PATTERN_KEY)
            .orElse(DEFAULT_REPORT_PATTERN);
    }

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor
            .name("Cobertura")
            .onlyOnLanguages(Swift.KEY, ObjectiveC.KEY)
            .onlyOnFileType(InputFile.Type.MAIN);
    }

    @Override
    public void execute(SensorContext context) {
        CoberturaReportParser parser = new CoberturaReportParser(context);
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setIncludes(new String[]{reportPath()});
        scanner.setBasedir(context.fileSystem().baseDir().getAbsolutePath());
        scanner.setCaseSensitive(false);
        scanner.scan();
        String[] files = scanner.getIncludedFiles();

        for (String filename : files) {
            LOGGER.info("Processing Cobertura report {}", filename);
            parser.parseReport(new File(filename));
        }
    }
}
