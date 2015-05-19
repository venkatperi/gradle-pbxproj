package com.vperi.gradle.plugin.pbxprojPlugin

import org.gradle.api.file.SourceDirectorySet
import org.gradle.api.internal.file.FileResolver
import org.gradle.util.ConfigureUtil

/**
 * ${file.filename} -- ${file.qualifiedClassName}*
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
public class DefaultSwiftSourceSet extends DefaultSourceSetBase {
    DefaultSwiftSourceSet( String name, FileResolver fileResolver ) {
        super( "$name swift source", [ "swift" ], fileResolver )
    }

    public SourceDirectorySet getSwift() {
        sourceDirSet
    }

    public DefaultSwiftSourceSet swift( Closure configureClosure ) {
        ConfigureUtil.configure configureClosure, swift
        this
    }

    @SuppressWarnings( "GroovyUnusedDeclaration" )
    public SourceDirectorySet getAllSwift() {
        allSourceDirSet
    }
}
