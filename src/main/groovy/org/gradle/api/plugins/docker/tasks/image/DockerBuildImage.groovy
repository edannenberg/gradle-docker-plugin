/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.api.plugins.docker.tasks.image

import org.gradle.api.plugins.docker.tasks.AbstractDockerTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.Optional

class DockerBuildImage extends AbstractDockerTask {
    /**
     * Input directory containing Dockerfile. Defaults to "$projectDir/docker".
     */
    @InputDirectory
    File inputDir = project.file('docker')

    /**
     * Tag for image.
     */
    @Input
    @Optional
    String tag

    @Override
    void runRemoteCommand(URLClassLoader classLoader) {
        def dockerClient = getDockerClient(classLoader)

        if(!getTag()) {
            logger.quiet "Building image from folder '${getInputDir()}'."
            dockerClient.buildImageCmd(getInputDir()).exec()
        }
        else {
            logger.quiet "Building image from folder '${getInputDir()}' with tag '${getTag()}'."
            dockerClient.buildImageCmd(getInputDir()).withTag(getTag()).exec()
        }
    }
}