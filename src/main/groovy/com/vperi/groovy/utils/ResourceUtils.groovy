package com.vperi.groovy.utils

import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

/**
 * ResourceUtils.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@SuppressWarnings( "GroovyUnusedDeclaration" )
class ResourceUtils {

  static def copyJarFiles( Class klass, String sourceDir, String outputDir, Closure select = null ) {
    def location = klass.protectionDomain.codeSource.location.toURI().path
    def uri = URI.create( "jar:file:$location" )
    def fs = FileSystems.newFileSystem( uri, [ : ] )
    def from = fs.getPath( sourceDir )
    def to = new File( outputDir ).toPath()

    copyFiles from, to, select
  }

  static def copyFiles( File from, File to, Closure select = null ) {
    copyFiles from.toPath(), to.toPath(), select
  }

  static def copyFiles( Path from, Path to, Closure select = null ) {
    Files.walk( from ).each { src ->
      if ( !select || select( src ) ) {
        def dest = to.resolve( from.relativize( src ).toString() )
        if ( Files.isDirectory( src ) ) {
          Files.createDirectories( dest )
        } else {
          Files.copy( src, dest, StandardCopyOption.REPLACE_EXISTING )
        }
      }
    }
  }
}
