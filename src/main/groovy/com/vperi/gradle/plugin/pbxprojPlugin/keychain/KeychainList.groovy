package com.vperi.gradle.plugin.pbxprojPlugin.keychain

/**
 * KeychainList.groovy
 *
 * Copyright © 2015 venkat
 *
 * This software may be modified and distributed under the terms
 * of the MIT license.  See the LICENSE file for details.
 */
interface KeychainList {
  Keychain create( String name, String password )
}
