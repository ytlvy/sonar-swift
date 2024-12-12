/**
 * commons - Enables analysis of Swift and Objective-C projects into SonarQube.
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
package com.backelite.sonarqube.commons;

import com.sonar.sslr.api.Token;

/**
 * @author magaofei
 * @date 2021/4/5
 */
public class TokenLocation {

    private final int startLine;
    private final int startCharacter;
    private final int endLine;
    private final int endCharacter;

    public TokenLocation(Token token) {
        this.startLine = token.getLine();
        this.startCharacter = token.getColumn();
        final String[] lines = token.getOriginalValue().split("\r\n|\n|\r", -1);
        if (lines.length > 1) {
            this.endLine = token.getLine() + lines.length - 1;
            this.endCharacter = lines[lines.length - 1].length();
        } else {
            this.endLine = startLine;
            this.endCharacter = startCharacter + token.getOriginalValue().length();
        }
    }

    public int startLine() {
        return startLine;
    }

    public int startCharacter() {
        return startCharacter;
    }

    public int endLine() {
        return endLine;
    }

    public int endCharacter() {
        return endCharacter;
    }

}
