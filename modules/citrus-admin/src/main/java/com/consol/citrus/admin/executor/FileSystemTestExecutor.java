/*
 * Copyright 2006-2013 the original author or authors.
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

package com.consol.citrus.admin.executor;

import com.consol.citrus.admin.launcher.*;
import com.consol.citrus.admin.launcher.process.ExecuteSingleTest;
import com.consol.citrus.admin.service.ConfigurationService;
import com.consol.citrus.admin.websocket.WebSocketProcessListener;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * Executes test case from file system by starting new Citrus command line instance.
 * @author Christoph Deppisch
 */
public class FileSystemTestExecutor implements TestExecutor{

    /** Logger */
    private static Logger log = LoggerFactory.getLogger(FileSystemTestExecutor.class);

    @Autowired
    private ProcessMonitor processMonitor;
    @Autowired
    private ConfigurationService configService;
    @Autowired
    private WebSocketProcessListener webSocketProcessListener;

    /**
     * {@inheritDoc}
     */
    public void execute(String testName) throws ParseException {
        File file = new File(configService.getProjectHome());
        ProcessBuilder processBuilder = new ExecuteSingleTest(file, testName).getProcessBuilder();
        ProcessLauncher processLauncher = new ProcessLauncherImpl(processMonitor, testName);

        processLauncher.addProcessListener(webSocketProcessListener);
        processLauncher.launchAndContinue(processBuilder, 0);
    }
    
}
