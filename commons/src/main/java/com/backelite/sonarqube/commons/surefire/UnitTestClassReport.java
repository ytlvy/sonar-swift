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
package com.backelite.sonarqube.commons.surefire;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

public final class UnitTestClassReport {
    private int errors = 0;
    private int failures = 0;
    private int skipped = 0;
    private int tests = 0;
    private long durationMilliseconds = 0L;

    private long negativeTimeTestNumber = 0L;
    private List<UnitTestResult> results = null;

    private void initResults() {
        if (results == null) {
            results = Lists.newArrayList();
        }
    }

    public UnitTestClassReport add(UnitTestResult result) {
        initResults();
        results.add(result);
        if (result.getStatus().equals(UnitTestResult.STATUS_SKIPPED)) {
            skipped += 1;

        } else if (result.getStatus().equals(UnitTestResult.STATUS_FAILURE)) {
            failures += 1;

        } else if (result.getStatus().equals(UnitTestResult.STATUS_ERROR)) {
            errors += 1;
        }
        tests += 1;
        if (result.getDurationMilliseconds() < 0) {
            negativeTimeTestNumber += 1;
        } else {
            durationMilliseconds += result.getDurationMilliseconds();
        }
        return this;
    }

    public int getErrors() {
        return errors;
    }

    public int getFailures() {
        return failures;
    }

    public int getSkipped() {
        return skipped;
    }

    public int getTests() {
        return tests;
    }

    public long getNegativeTimeTestNumber() {
        return negativeTimeTestNumber;
    }

    public List<UnitTestResult> getResults() {
        if (results == null) {
            return Collections.emptyList();
        }
        return results;
    }

    public long getDurationMilliseconds() {
        return durationMilliseconds;
    }

}
