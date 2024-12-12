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

import static org.junit.jupiter.api.Assertions.*;

import org.json.simple.parser.ParseException;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FilePredicates;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.fs.internal.DefaultFilePredicates;
import org.sonar.api.batch.fs.internal.DefaultFileSystem;
import org.sonar.api.batch.fs.internal.TestInputFileBuilder;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.issue.NewIssueLocation;
import org.sonar.api.batch.sensor.issue.internal.DefaultIssue;
import org.sonar.api.rule.RuleKey;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

import static org.mockito.Mockito.*;

class InferReportParserTest {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    private DefaultFileSystem fs;
    private Path moduleBasePath;

    @Mock
    SensorContext sensorContext;

    @Mock
    FileSystem fileSystem;
    private InferReportParser self;


    @org.junit.jupiter.api.BeforeEach
    void setUp() throws IOException {
        moduleBasePath = temp.newFolder().toPath();
        fs = new DefaultFileSystem(moduleBasePath);

        MockitoAnnotations.openMocks(this);
        self = new InferReportParser(sensorContext);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }


    @Test
    void parseReport() throws IOException, ParseException {
        ClassLoader classLoader = getClass().getClassLoader();
        File reportFile = new File(Objects.requireNonNull(classLoader.getResource("report_java.json")).getFile());
        File file = new File(Objects.requireNonNull(classLoader.getResource("Hello.java")).getFile());

        FilePredicates predicates = new DefaultFilePredicates(temp.newFolder().toPath());
        InputFile javaFile = new TestInputFileBuilder("foo", "Hello.java")
            .setModuleBaseDir(moduleBasePath)
            .setLanguage("java")
            .setContents(new String(Files.readAllBytes(file.toPath())))
            .setStatus(InputFile.Status.SAME)
            .build();

        fs.add(javaFile);


        when(sensorContext.fileSystem()).thenReturn(fs);
        when(fileSystem.predicates()).thenReturn(predicates);

        DefaultIssue defaultIssue = mock(DefaultIssue.class);
        when(defaultIssue.addFlow(anyIterable())).thenReturn(defaultIssue);
        when(defaultIssue.forRule(any(RuleKey.class))).thenReturn(defaultIssue);
        when(defaultIssue.at(any(NewIssueLocation.class))).thenReturn(defaultIssue);
        when(sensorContext.newIssue()).thenReturn(defaultIssue);
        doNothing().when(defaultIssue).save();

        self.parseReport(reportFile);

        verify(defaultIssue, times(1)).addFlow(anyIterable());
        verify(defaultIssue, times(1)).forRule(any(RuleKey.class));
        verify(defaultIssue, times(1)).at(any(NewIssueLocation.class));
        verify(defaultIssue, times(1)).save();


    }
}