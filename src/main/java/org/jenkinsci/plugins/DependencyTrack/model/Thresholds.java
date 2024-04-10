/*
 * This file is part of Dependency-Track Jenkins plugin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jenkinsci.plugins.DependencyTrack.model;

import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class Thresholds implements Serializable {

    public final ThresholdValues totalFindings = new ThresholdValues();
    public final ThresholdValues newFindings = new ThresholdValues();

    public boolean hasValues() {
        return totalFindings.hasValues() || newFindings.hasValues();
    }

    @EqualsAndHashCode
    @ToString
    public static class ThresholdValues implements Serializable {

        public Integer unstableCritical;
        public Integer unstableHigh;
        public Integer unstableMedium;
        public Integer unstableLow;
        public Integer unstableUnassigned;
        public Integer failedCritical;
        public Integer failedHigh;
        public Integer failedMedium;
        public Integer failedLow;
        public Integer failedUnassigned;

        public boolean hasValues() {
            return hasUnstableValues() || hasFailedValues();
        }

        private boolean hasUnstableValues() {
            return unstableCritical != null
                    || unstableHigh != null
                    || unstableMedium != null
                    || unstableLow != null
                    || unstableUnassigned != null;
        }

        private boolean hasFailedValues() {
            return failedCritical != null
                    || failedHigh != null
                    || failedMedium != null
                    || failedLow != null
                    || failedUnassigned != null;
        }
    }
}
