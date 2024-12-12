/**
 * Swift SonarQube Plugin - Objective-C module - Enables analysis of Swift and Objective-C projects into SonarQube.
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
package com.backelite.sonarqube.objectivec.lang.lexer;

import com.backelite.sonarqube.objectivec.internal.ObjectiveCLexer;
import com.backelite.sonarqube.objectivec.internal.ObjectiveCParser;
import com.backelite.sonarqube.objectivec.issues.antlr.AntlrContext;
import com.backelite.sonarqube.objectivec.issues.antlr.AntlrUtils;
import com.backelite.sonarqube.objectivec.issues.antlr.rule.CustomTreeVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class AntlrVisitTest {

    @Test
    public void visit() throws IOException {

        final CharStream charStream = CharStreams.fromStream(Objects.requireNonNull(this.getClass().getResourceAsStream("/NetworkRequest.m")));
        final ObjectiveCLexer lexer = new ObjectiveCLexer(charStream);
        lexer.removeErrorListeners();
        final CommonTokenStream stream = new CommonTokenStream(lexer);
        stream.fill();
        final ObjectiveCParser parser = new ObjectiveCParser(stream);
        parser.removeErrorListeners();
        final ParseTree root = parser.translationUnit();
        CustomTreeVisitor customTreeVisitor = new CustomTreeVisitor(null);
        customTreeVisitor.visit(root);
        AntlrContext antlrContext = AntlrUtils.getRequest(IOUtils.toString(Objects.requireNonNull(this.getClass().getResourceAsStream("/NetworkRequest.m")), StandardCharsets.UTF_8));
//        customTreeVisitor.fillContext(null, antlrContext);
    }
}
