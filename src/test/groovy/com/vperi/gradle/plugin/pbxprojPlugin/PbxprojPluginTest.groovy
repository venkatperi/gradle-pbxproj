package com.vperi.gradle.plugin.pbxprojPlugin

import groovy.io.FileType
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class PbxprojPluginTest {
    def root = new File( ".", "src/test/resources" )

    @Test
    void pluginIsApplied() {
        Project project = ProjectBuilder.builder().withProjectDir( root ).build()
        project.with {
            apply plugin: 'pbxproj'

            sourceSets {
                main {
                    objc {}
                    swift {}
                }
            }

            pbxproj {
                projectRoot ""
                compatibilityVersion "Xcode 3.2"
                developmentRegion "English"
                projectDirPath ""
                defaultConfigurationName "Release"
            }

            buildConfigurations {
                Debug {
                    buildSettings {
                        codeSignIdentity "abc"
                        gccPreprocessorDefinitions "DEBUG=1", "\$inherited"
                        clangWarnEnumConversion yes
                        clangWarnEnumConversion yesError
                    }
                }
            }
        }

        [ "objc", "swift" ].each {
            project.sourceSets.main."$it".srcDirs.each {
                if ( it.exists() )
                    it.eachFileRecurse FileType.FILES, { println it }
            }
        }

        println project.extensions.buildConfigurations.Debug

//        def task = project.tasks.findByName( 'pbxproj' )
//        assert task instanceof PbxprojTask
    }
}