package com.vperi.gradle.plugin.pbxprojPlugin

import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.internal.file.FileResolver
import org.gradle.util.ConfigureUtil

/**
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
public class DefaultObjcSourceSet extends DefaultSourceSetBase {
  DefaultObjcSourceSet( String name, FileResolver fileResolver ) {
    super( "$name objectivec source",
        [ "m", "h", "o", "mm", "c", "cpp" ], fileResolver )
  }

  public SourceDirectorySet getObjc() {
    sourceDirSet
  }

  @SuppressWarnings( "GroovyUnusedDeclaration" )
  public DefaultObjcSourceSet objc( Closure configureClosure ) {
    ConfigureUtil.configure configureClosure, objc
    this
  }

  @SuppressWarnings( "GroovyUnusedDeclaration" )
  public SourceDirectorySet getAllObjc() {
    allSourceDirSet
  }
}
