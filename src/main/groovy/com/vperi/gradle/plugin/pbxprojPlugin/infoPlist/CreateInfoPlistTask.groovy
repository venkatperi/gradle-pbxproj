package com.vperi.gradle.plugin.pbxprojPlugin.infoPlist

import com.vperi.gradle.extension.CreatePlistTaskBase
/**
 * CreateInfoPlistTask.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@SuppressWarnings( "GroovyUnusedDeclaration" )
class CreateInfoPlistTask extends CreatePlistTaskBase<InfoPlistExt> {
  String fileName = "Info.plist"
}
