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
package com.backelite.sonarqube.objectivec.issues.antlr;

import com.backelite.sonarqube.objectivec.internal.ObjectiveCParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.sensor.SensorContext;

public class ClassMethodVisitor implements ParseTreeItemVisitor {

    private static final Logger logger = LoggerFactory.getLogger(ClassMethodVisitor.class);

    @Override
    public void apply(ParseTree tree) {
        logger.info("apply {}", tree.getText());
    }

    @Override
    public void fillContext(SensorContext context, AntlrContext antlrContext) {
        logger.info("fill {}", antlrContext.getRoot());
    }


//    @Override
//    public Object visitTranslationUnit(ObjectiveCParser.TranslationUnitContext ctx) {
//        logger.info("visitTranslationUnit {}", ctx.getText());
//        return super.visitTranslationUnit(ctx);
//    }
//
//    @Override
//    public Object visitMethodDeclaration(ObjectiveCParser.MethodDeclarationContext ctx) {
//        logger.info("visitMethodDeclaration {}", ctx.getText());
//        return super.visitMethodDeclaration(ctx);
//    }
//
//    @Override
//    public Object visitClassMethodDefinition(ObjectiveCParser.ClassMethodDefinitionContext ctx) {
//        logger.info("visitClassMethodDefinition {}", ctx.getText());
//        return super.visitClassMethodDefinition(ctx);
//    }

}
