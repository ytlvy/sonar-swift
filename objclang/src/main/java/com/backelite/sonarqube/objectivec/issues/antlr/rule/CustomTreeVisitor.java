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
package com.backelite.sonarqube.objectivec.issues.antlr.rule;

import com.backelite.sonarqube.objectivec.internal.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.check.Rule;

@Rule(key = "RSPEC-138")
public class CustomTreeVisitor extends ObjectiveCParserBaseVisitor {

    private static final Logger logger = LoggerFactory.getLogger(CustomTreeVisitor.class);

    private static final int MAX_LENGTH = 12;

    private final SensorContext sensorContext;

    public CustomTreeVisitor(SensorContext sensorContext) {
        this.sensorContext = sensorContext;
    }

    @Override
    public Object visitClassMethodDefinition(ObjectiveCParser.ClassMethodDefinitionContext ctx) {
        logger.debug("visitClassMethodDefinition {}", ctx.getText());
        logger.debug(" length = {}", ctx.getText().split(";").length);
//        String methodName = ctx.methodDefinition().methodSelector().getText();
        return super.visitClassMethodDefinition(ctx);
    }

    static class StatementVisitor extends ObjectiveCParserBaseVisitor {

        private int length = 0;

        @Override
        public Object visitStatement(ObjectiveCParser.StatementContext ctx) {
            length += 1;
            return super.visitStatement(ctx);
        }
    }

    @Override
    public Object visitInstanceMethodDefinition(ObjectiveCParser.InstanceMethodDefinitionContext ctx) {
        StatementVisitor statementVisitor = new StatementVisitor();
        ctx.methodDefinition().accept(statementVisitor);
        int length = statementVisitor.length;
        if (length > MAX_LENGTH) {
            logger.info("report issue {}, methodName = {}", length, ctx.methodDefinition().methodSelector().getText());
        }
//        logger.info(" length = {}", ctx.getText().split(";").length);
        return super.visitInstanceMethodDefinition(ctx);
    }

}
