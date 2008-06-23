/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.core.datadictionary.validation.charlevel;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.kuali.core.datadictionary.exporter.ExportMap;
import org.kuali.core.datadictionary.validation.CharacterLevelValidationPattern;

/**
 * This is a description of what this class does - ctdang don't forget to fill this in. 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */
public class RegexValidationPattern extends CharacterLevelValidationPattern {

    private static final long serialVersionUID = -5642894236634278352L;
    private static final Logger LOG=Logger.getLogger(RegexValidationPattern.class);
    /**
     * Regular expression, e.g. "[a-zA-Z0-9]"
     */
    private String pattern;

    /**
     * This exports a representation of this instance by an ExportMap.
     * 
     * @see org.kuali.core.datadictionary.validation.CharacterLevelValidationPattern#extendExportMap(org.kuali.core.datadictionary.exporter.ExportMap)
     */
    public void extendExportMap(ExportMap exportMap) {
        if (LOG.isTraceEnabled()) {
            String message=String.format("ENTRY %s",
                    (exportMap==null)?"null":exportMap.toString());
            LOG.trace(message);
        }
        
        // Set element value
        exportMap.set("type", "regex");
        // Set attribute (of the above element) value
        exportMap.set("pattern", getPattern());

        if (LOG.isTraceEnabled()) {
            String message=String.format("EXIT %s",
                    (exportMap==null)?"null":exportMap.toString());
            LOG.trace(message);
        }
        
     }

    /**
     * This returns an instance of this class as string.
     * 
     * @see org.kuali.core.datadictionary.validation.ValidationPattern#getPatternXml()
     */
    public String getPatternXml() {
        if (LOG.isTraceEnabled()) {
            String message=String.format("ENTRY");
            LOG.trace(message);
        }
        
        StringBuffer xml = new StringBuffer("<regex ");
        xml.append(pattern);
        xml.append("/>");

        if (LOG.isTraceEnabled()) {
            String message=String.format("EXIT %s", xml.toString());
            LOG.trace(message);
        }
        
        return xml.toString();
    }

    /**
     * This returns the specified regular expression defined in the data dictionary
     * entry for validating the value of an attribute.
     * 
     * @see org.kuali.core.datadictionary.validation.ValidationPattern#getRegexString()
     */
    protected String getRegexString() {
        if (LOG.isTraceEnabled()) {
            String message=String.format("ENTRY %s",
                    (pattern==null)?"null":pattern.toString());
            LOG.trace(message);
        }
        
        if (StringUtils.isEmpty(pattern)) {
            throw new IllegalStateException(this.getClass().getName()+".pattern is empty");
        }

        if (LOG.isTraceEnabled()) {
            String message=String.format("EXIT");
            LOG.trace(message);
        }
        
        return pattern;
    }

    /**
     * @return the pattern
     */
    public final String getPattern() {
        return this.pattern;
    }

    /**
     * @param pattern the pattern to set
     */
    public final void setPattern(String pattern) {
        this.pattern = pattern;
    }

}
