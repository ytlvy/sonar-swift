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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonar.api.profiles.ProfileImporter;
import org.sonar.api.profiles.RulesProfile;
import org.sonar.api.profiles.XMLProfileParser;
import org.sonar.api.utils.ValidationMessages;

import java.io.Reader;

/**
 * @author magaofei
 * @date 2021/04/11
 */
public class InferProfileImporter extends ProfileImporter {

    private static final Logger logger = LoggerFactory.getLogger(InferProfileImporter.class);

    private static final String UNABLE_TO_LOAD_DEFAULT_PROFILE = "Unable to load default infer profile";

    private final XMLProfileParser profileParser;

    public InferProfileImporter(final XMLProfileParser xmlProfileParser) {
        super(InferRulesDefinition.REPOSITORY_KEY, InferRulesDefinition.REPOSITORY_KEY);
        setSupportedLanguages("java");
        profileParser = xmlProfileParser;
    }

    @Override
    public RulesProfile importProfile(Reader reader, ValidationMessages messages) {
        final RulesProfile profile = profileParser.parse(reader, messages);

        if (null == profile) {
            messages.addErrorText(UNABLE_TO_LOAD_DEFAULT_PROFILE);
            logger.error(UNABLE_TO_LOAD_DEFAULT_PROFILE);
        }
        return profile;
    }
}
