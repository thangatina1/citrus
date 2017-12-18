/*
 * Copyright 2006-2010 the original author or authors.
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

package com.consol.citrus.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.util.ArrayList;
import java.util.List;

public class JsonSchemaRepository  implements BeanNameAware, InitializingBean {

    /** This repositories name in the Spring application context */
    private String name = "schemaRepository";

    /** List of schema resources */
    private List<JsonSchema> schemas = new ArrayList<>();

    /** List of location patterns that will be translated to schema resources */
    private List<String> locations = new ArrayList<>();

    /** Logger */
    private static Logger log = LoggerFactory.getLogger(JsonSchemaRepository.class);


    /**
     * {@inheritDoc}
     */
    @Override
    public void setBeanName(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

        for (String location : locations) {
            Resource[] findings = resourcePatternResolver.getResources(location);

            for (Resource resource : findings) {
                addSchemas(resource);
            }
        }
    }

    private void addSchemas(Resource resource) throws Exception {
        if (resource.getFilename().endsWith(".json")) {
            if (log.isDebugEnabled()) {
                log.debug("Loading json schema resource " + resource.getFilename());
            }
            JsonSchema jsonSchema = new JsonSchema(resource);
            jsonSchema.afterPropertiesSet();
            schemas.add(jsonSchema);
        } else {
            log.warn("Skipped resource other than json schema for repository (" + resource.getFilename() + ")");
        }
    }

    public String getName() {
        return name;
    }

    public List<JsonSchema> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<JsonSchema> schemas) {
        this.schemas = schemas;
    }

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        JsonSchemaRepository.log = log;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }
}