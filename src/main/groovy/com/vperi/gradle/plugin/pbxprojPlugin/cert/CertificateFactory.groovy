package com.vperi.gradle.plugin.pbxprojPlugin.cert

import com.vperi.gradle.extension.ExtensionFactoryBase
import groovy.transform.Canonical

/**
 * ${file.filename} -- ${file.qualifiedClassName}*
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
@Canonical
class CertificateFactory extends ExtensionFactoryBase<CertificateExt> {
  Class klass = CertificateExt

  def afterEvaluate( CertificateExt cert ) {
    _ "${cert.parent.prefix}CreateCertificate", CreateCertificateTask, cert
  }
}

