/**
 * Swift SonarQube Plugin - Swift module - Enables analysis of Swift and Objective-C projects into SonarQube.
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
package com.backelite.sonarqube.swift.cpd;

import com.backelite.sonarqube.commons.TokenLocation;
import com.sonar.sslr.api.GenericTokenType;
import com.sonar.sslr.api.Token;
import com.sonar.sslr.api.TokenType;
import com.sonar.sslr.impl.Lexer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.cpd.NewCpdTokens;

import java.io.File;
import java.util.Iterator;

/**
 * @author magaofei
 * @date 2021/4/5
 */
public class SwiftCpdAnalyzer {

    private static final Logger logger = LoggerFactory.getLogger(SwiftCpdAnalyzer.class);

    private final SensorContext context;
    private final Lexer lexer;

    public SwiftCpdAnalyzer(SensorContext context, Lexer lexer) {
        this.context = context;
        this.lexer = lexer;
    }

    public void pushCpdTokens(InputFile inputFile) {
//        NewHighlighting highlighting = context.newHighlighting();

        File file = inputFile.file();
//        highlighting.onFile(inputFile);

        if (inputFile.filename().endsWith(".swift")) {
            return;
        }

        NewCpdTokens cpdTokens = context.newCpdTokens();
        cpdTokens.onFile(inputFile);

        Iterator<Token> iterator = lexer.lex(file).iterator();

        while (iterator.hasNext()) {
            Token token = iterator.next();
            TokenType tokenType = token.getType();

            if (!tokenType.equals(GenericTokenType.EOF)) {
                TokenLocation tokenLocation = new TokenLocation(token);
                cpdTokens.addToken(tokenLocation.startLine(), tokenLocation.startCharacter(), tokenLocation.endLine(), tokenLocation.endCharacter(), token.getValue());
            }
//            if (tokenType.equals(GenericTokenType.LITERAL)) {
//                highlight(highlighting, token, TypeOfText.STRING);
//            } else if (KEYWORDS.contains(tokenType)) {
//                highlight(highlighting, token, TypeOfText.KEYWORD);
//            }
//            for (Trivia trivia : token.getTrivia()) {
//                highlight(highlighting, trivia.getToken(), TypeOfText.COMMENT);
//            }
        }

//        highlighting.save();
        cpdTokens.save();
    }

//    private static void highlight(NewHighlighting highlighting, Token token, TypeOfText typeOfText) {
//        TokenLocation tokenLocation = new TokenLocation(token);
//        highlighting.highlight(tokenLocation.startLine(), tokenLocation.startCharacter(), tokenLocation.endLine(), tokenLocation.endCharacter(), typeOfText);
//    }

}
