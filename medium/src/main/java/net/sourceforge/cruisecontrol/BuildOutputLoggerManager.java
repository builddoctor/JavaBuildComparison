/********************************************************************************
 * CruiseControl, a Continuous Integration Toolkit
 * Copyright (c) 2007, ThoughtWorks, Inc.
 * 200 E. Randolph, 25th Floor
 * Chicago, IL 60601 USA
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *     + Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     + Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *
 *     + Neither the name of ThoughtWorks, Inc., CruiseControl, nor the
 *       names of its contributors may be used to endorse or promote
 *       products derived from this software without specific prior
 *       written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ********************************************************************************/
package net.sourceforge.cruisecontrol;

import net.sourceforge.cruisecontrol.util.BuildOutputLogger;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Lazy load a BuildOutputLogger.
 */
public class BuildOutputLoggerManager {

    public static final BuildOutputLoggerManager INSTANCE = new BuildOutputLoggerManager();

    private final Map<String, BuildOutputLogger> loggers = new ConcurrentHashMap<String, BuildOutputLogger>();

    BuildOutputLoggerManager() {
    }

    /**
     * If we haven't already created one, create a temporary BuildOutputLogger to retrieve empty lines.
     * @param projectName a project name
     * @return Either logger or a temporary empty logger.
     */
    public BuildOutputLogger lookup(final String projectName) {
        return lookupOrCreate(projectName, null);
    }

    public BuildOutputLogger lookupOrCreate(final String projectName, final File outputFile) {
        BuildOutputLogger logger;
        if (projectName == null) {
            // eg: AntBootstrapper executes with empty buildProperties object, so no 'projectName' prop will exist,
            // so we can't cache the logger. Therefore, just create one and hand it back.
            return new BuildOutputLogger(outputFile);
        } else {
            logger = loggers.get(projectName);
        }

        if (logger == null) {
            logger = new BuildOutputLogger(outputFile);
            loggers.put(projectName, logger);
        }

        if (outputFile != null
                //&& !logger.isDataFileSet() // replace current temp logger with a new logger with a data file
                && !logger.isDataFileEquals(outputFile) // replace logger if files are not the same.
                ) {

            logger = new BuildOutputLogger(outputFile);
            loggers.put(projectName, logger);
        }

        return logger;
    }

    /**
     * Allows use of remote live output readers.
     * @param projectName the unique name of this project.
     * @param buildOutputLoggerRemote a live output reader connected to a distributed agent.
     * @return prior logger for this project if one existed.
     */
    public BuildOutputLogger put(final String projectName, final BuildOutputLogger buildOutputLoggerRemote) {
        return loggers.put(projectName, buildOutputLoggerRemote);
    }

    /**
     * Allows cleanup of remote live output readers.
     * Don't hold onto remote references, could get nasty otherwise.
     * @param projectName an existing project name who's reader should be removed.
     * @return the removed reader or null if none existed.
     */
    public BuildOutputLogger remove(final String projectName) {
        return loggers.remove(projectName);
    }
}
