/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 */

package com.microsoft.azure.gradle.webapp.handlers;

import com.microsoft.azure.gradle.webapp.AzureWebAppExtension;
import com.microsoft.azure.gradle.webapp.DeployTask;
import com.microsoft.azure.gradle.webapp.helpers.WebAppUtils;
import com.microsoft.azure.management.appservice.WebApp;
import com.microsoft.azure.management.appservice.WebApp.DefinitionStages.WithCreate;
import com.microsoft.azure.management.appservice.WebApp.Update;

public class JavaRuntimeHandlerImpl implements RuntimeHandler {
    private DeployTask task;
    private AzureWebAppExtension extension;

    public JavaRuntimeHandlerImpl(final DeployTask task) {
        this.task = task;
        this.extension = task.getAzureWebAppExtension();
    }

    @Override
    public WithCreate defineAppWithRuntime() throws Exception {
        final WithCreate withCreate = WebAppUtils.defineApp(task)
                .withNewWindowsPlan(extension.getPricingTier());
        withCreate.withJavaVersion(extension.getJavaVersion())
                .withWebContainer(extension.getJavaWebContainer());
        return withCreate;
    }

    @Override
    public Update updateAppRuntime(final WebApp app) throws Exception {
        WebAppUtils.assureWindowsWebApp(app);
        WebAppUtils.clearTags(app);

        final Update update = app.update();
        update.withJavaVersion(extension.getJavaVersion())
                .withWebContainer(extension.getJavaWebContainer());
        return update;
    }
}