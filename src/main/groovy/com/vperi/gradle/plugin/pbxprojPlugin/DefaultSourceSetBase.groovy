package com.vperi.gradle.plugin.pbxprojPlugin

import org.gradle.api.internal.file.DefaultSourceDirectorySet
import org.gradle.api.internal.file.FileResolver
import org.gradle.api.internal.tasks.DefaultGroovySourceSet

/**
 * ${file.filename} -- ${file.qualifiedClassName}*
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
public class DefaultSourceSetBase {
    def sourceDirSet
    def allSourceDirSet

    public DefaultSourceSetBase( String name, List<String> extensions, FileResolver fileResolver ) {
        def x = extensions.collect { "**/*.$it " }
        sourceDirSet = new DefaultSourceDirectorySet( name, fileResolver )
        sourceDirSet.filter.include x
        allSourceDirSet = new DefaultSourceDirectorySet( name, fileResolver )
        allSourceDirSet.source sourceDirSet
        allSourceDirSet.filter.include x
        DefaultGroovySourceSet
    }
}

