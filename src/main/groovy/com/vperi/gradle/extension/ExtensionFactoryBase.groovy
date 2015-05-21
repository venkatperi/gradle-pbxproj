package com.vperi.gradle.extension

import groovy.transform.Canonical
import groovy.util.logging.Slf4j
import org.gradle.api.NamedDomainObjectFactory
import org.gradle.api.Project
import org.gradle.api.Task

/**
 * ${file.filename} -- ${file.qualifiedClassName}*
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@Canonical
@Slf4j
abstract class ExtensionFactoryBase<T extends ExtensionBase> implements NamedDomainObjectFactory<T> {
  Project project
  ContainerExtBase parent

  abstract Class<T> getKlass()

  @Override
  T create( String name ) {
    def ext = createInstance( name )
    project.afterEvaluate {
      assert project.state.executed
      afterEvaluate ext
    }
    ext
  }

  Task _( String name, Class klass, T x ) {
    def task = project.task( name, type: klass ) {
      ext = x
    }
//    task.inputs.property "ext", x
    task
  }

  T createInstance( String name ) {
    klass.newInstance( name, project, parent )
  }

  abstract def afterEvaluate( T cert )
}

