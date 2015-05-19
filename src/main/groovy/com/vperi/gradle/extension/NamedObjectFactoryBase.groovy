package com.vperi.gradle.extension

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
abstract class NamedObjectFactoryBase<T extends ExtensionBase> implements NamedDomainObjectFactory<T> {
  Project project

  abstract Class getKlass()

  @Override
  T create( String name ) {
    def ext = createInstance( name )
    project.afterEvaluate {
      assert project.state.executed
      addTasksFor ext
    }
    ext
  }

  def createInstance( String name ) {
    klass.newInstance( name, project ) as T
  }

  abstract def addTasksFor( T cert )
}

