package com.vperi.gradle.plugin.pbxprojPlugin.cert

import com.vperi.gradle.extension.NamedObjectFactoryBase
import groovy.transform.Canonical

/**
 * ${file.filename} -- ${file.qualifiedClassName}*
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@Canonical
class CertificateFactory extends NamedObjectFactoryBase<CertificateExt> {
  Class klass = CertificateExt

  def addTasksFor( CertificateExt cert ) {
    def createTask = project.task( "certCreate${cert.name.capitalize()}", type: MakeCertTask ) {
      ext = cert
    }
    createTask.inputs.property "ext", cert
  }
}

