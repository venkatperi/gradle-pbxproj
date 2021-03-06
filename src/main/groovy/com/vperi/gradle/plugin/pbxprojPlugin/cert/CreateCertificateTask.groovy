package com.vperi.gradle.plugin.pbxprojPlugin.cert

import com.github.javafaker.Faker
import com.vperi.gradle.extension.HasCommand
import com.vperi.gradle.extension.ResourceTaskBase
import groovy.text.SimpleTemplateEngine
import org.gradle.api.tasks.TaskAction

/**
 * Gradle task - Create a self signed certificate
 */
class CreateCertificateTask extends ResourceTaskBase<CertificateExt> implements HasCommand {

  String executable = "openssl"

  @SuppressWarnings( "GroovyUnusedDeclaration" )
  @TaskAction
  void action() {
    baseDir.mkdirs()
    genConfigFile()
    genRsa()
    genReq()
    genCert()
    toDer()
  }

  def genRsa() {
    exec "genrsa", [
        des3: "",
        out: "${ext.basePath}.key",
        passout: "pass:$ext.password"
    ], [ ext.bits ]
  }

  def genReq() {
    exec "req", [
        new: "",
        key: "${ext.basePath}.key",
        out: "${ext.basePath}.csr",
        passin: "pass:$ext.password",
        config: "${ext.basePath}.config" ]
  }

  def genCert() {
    exec "x509", [
        req: "",
        days: ext.days,
        in: "${ext.basePath}.csr",
        signkey: "${ext.basePath}.key",
        out: "${ext.basePath}.crt",
        passin: "pass:$ext.password"
    ]
  }

  def toDer() {
    exec "x509", [
        in: "${ext.basePath}.crt",
        outform: "der",
        out: "${ext.basePath}.der"
    ]
  }

  def genConfigFile() {
    project.file( "${ext.basePath}.config" ).withWriter {
      it.write configText
    }
  }

  @Override
  boolean equals( Object obj ) {
    return super.equals( obj )
  }

  @Override
  int hashCode() {
    return super.hashCode()
  }

  @Override
  boolean dependsOnTaskDidWork() {
    return super.dependsOnTaskDidWork()
  }

  def getBinding() {
    ext.generateDN ? fakeDN : ext.distinguishedName
  }

  def getFakeDN() {
    def states = resource( "mkcert/states.txt" ).split( "," )
    def faker = new Faker()
    def orgName = faker.name().lastName() + " and Sons"

    def stateIdx = Math.random() * states.length as int
    [
        country: "US",
        state: states[ stateIdx ],
        locality: "Unknown",
        orgName: orgName,
        orgUnitName: orgName,
        commonName: orgName,
        email: "${faker.name().firstName()}@someprovider.com".toLowerCase()
    ]
  }

  def getConfigText() {
    def templateText = resource( "mkcert/template.config" )
    def engine = new SimpleTemplateEngine()
    def template = engine.createTemplate( templateText ).make( binding ).toString()
    template.toString()
  }

  def resource( String file ) {
    this.class.classLoader.getResource( file ).text
  }
}