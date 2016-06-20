/*
 * Sonar Crowd Plugin
 * Copyright (C) 2009 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */

package org.sonar.plugins.crowd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sonar.api.Plugin;
import org.sonar.api.utils.Version;

/**
 * @author Evgeny Mandrikov
 */
public class CrowdPluginTest {
    private CrowdPlugin plugin;

    @Before
    public void setUp() throws Exception {
        plugin = new CrowdPlugin();
    }

    @Test
    public void test() {
        Plugin.Context context = new Plugin.Context(Version.create(5, 6));
        plugin.define(context);
        Assert.assertEquals(2, context.getExtensions().size());
    }


}
