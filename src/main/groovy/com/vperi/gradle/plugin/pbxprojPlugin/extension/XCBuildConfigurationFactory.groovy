package com.vperi.gradle.plugin.pbxprojPlugin.extension

import groovy.transform.Canonical
import org.gradle.api.NamedDomainObjectFactory
import org.gradle.api.Project

/**
 * ${file.filename} -- ${file.qualifiedClassName}*
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@Canonical
class XCBuildConfigurationFactory implements NamedDomainObjectFactory<XCBuildConfiguration> {
  Project project

  @Override
  XCBuildConfiguration create( String s ) {
    new XCBuildConfiguration( project: project, name: s )
  }
}

