/* Copyright 2024 Huawei Cloud Computing Technologies Co., Ltd.
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
package io.trino.plugin.opengemini;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import io.trino.spi.type.TypeManager;

import static io.airlift.configuration.ConfigBinder.configBinder;
import static io.airlift.json.JsonCodec.listJsonCodec;
import static io.airlift.json.JsonCodecBinder.jsonCodecBinder;
import static java.util.Objects.requireNonNull;

public class OpenGeminiModule
        implements Module
{
    private final TypeManager typeManager;

    public OpenGeminiModule(TypeManager typeManager)
    {
        this.typeManager = requireNonNull(typeManager, "typeManager is null");
    }

    @Override
    public void configure(Binder binder)
    {
        binder.bind(TypeManager.class).toInstance(typeManager);

        binder.bind(OpenGeminiConnector.class).in(Scopes.SINGLETON);
        binder.bind(OpenGeminiMetadata.class).in(Scopes.SINGLETON);
        binder.bind(OpenGeminiSession.class).in(Scopes.SINGLETON);
        binder.bind(OpenGeminiSplitManager.class).in(Scopes.SINGLETON);
        binder.bind(OpenGeminiRecordSetProvider.class).in(Scopes.SINGLETON);
        binder.bind(OpenGeminiPageSinkProvider.class).in(Scopes.SINGLETON);

        configBinder(binder).bindConfig(OpenGeminiConfig.class);

        jsonCodecBinder(binder).bindMapJsonCodec(String.class, listJsonCodec(OpenGeminiTable.class));
    }
}
