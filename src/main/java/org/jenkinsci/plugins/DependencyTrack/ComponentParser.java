/*
 * Copyright 2024 OWASP.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jenkinsci.plugins.DependencyTrack;

import lombok.experimental.UtilityClass;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jenkinsci.plugins.DependencyTrack.model.Component;
import org.jenkinsci.plugins.DependencyTrack.model.Finding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Ronny "Sephiroth" Perinke <sephiroth@sephiroth-j.de>
 */
@UtilityClass
class ComponentParser extends ModelParser {

    List<Component> parse(final String jsonResponse) {
        final JSONArray jsonArray = JSONArray.fromObject(jsonResponse);
        return jsonArray.stream()
                .map(JSONObject.class::cast)
                .map(ComponentParser::parseComponent)
                .collect(Collectors.toCollection(ArrayList<Component>::new));
    }


    /**
     * 官方留的一个坑，这里加一下特殊处理，将首尾的一对花括号替换为[]，从而让json解析正确。
     * @param jsonResponse body的原始返回值，以字符串的形式
     * @return Component数组
     */
    List<Component> specialParse(final String jsonResponse){
        final JSONObject temp = JSONObject.fromObject(jsonResponse);
        ArrayList<Component> resArray = new ArrayList<Component>();
        for(Object obj: temp.values()){
            resArray.add(parseComponent((JSONObject)obj));
        }
        return resArray;
    }


    Component parseComponent(JSONObject json) {
        final String uuid = getKeyOrNull(json, "uuid");
        final String name = getKeyOrNull(json, "name");
        final String group = getKeyOrNull(json, "group");
        final String version = getKeyOrNull(json, "version");
        final String purl = getKeyOrNull(json, "purl");
        final String dependencyGraph = getKeyOrNull(json, "dependencyGraph");
        return new Component(uuid, name, group, version, purl, dependencyGraph);
    }


}
