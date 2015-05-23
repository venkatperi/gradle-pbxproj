package com.vperi.xcodeproj

import spock.lang.Specification

/**
 * XcodeBuildSpec.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
class XcodeBuildSpec extends Specification {
  def xb

  def setup() {
    xb = new XcodeBuild()
  }

  def "get xcodebuild version"() {
    expect:
    xb.version
  }

  def "xcodebuild exists in the path"() {
    expect:
    XcodeBuild.exists
  }

  def "get sdk versions"() {
    when:
    Map sdks = xb.sdks

    then:
    sdks.size(  ) > 0
    sdks.containsKey "macosx"
    sdks.containsKey "iphoneos"
    sdks.containsKey "iphonesimulator"
  }

}
