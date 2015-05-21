# Gradle Xcode Plugin


The Xcode plugin adds project file generation and building capabilities to a project. It builds off the Gradle Java plugin.

## Usage

To use the Xcode plugin, add the following to your build script:

**build.gradle**

```
apply plugin: 'pbxproj'
```
    
##Requirments
* Ruby & the [Xcodeproj](https://github.com/CocoaPods/Xcodeproj) gem

```$ [sudo] gem install xcodeproj```
     
    
## Source Sets

The Java plugin introduces two source sets to the project: `main` and `test`. Additional source sets are added for each target.

##Project Layout
The Xcode plugin assumes the following layout:

|Directory|Meaning|
|---|---|---|---|---|
|src/main/objc|ObjeciveC code. Included in all targets.|
|src/main/swift|Swift code. Included in all targets.|
|src/main/resources|Resources. Included in all targets.|
|build/gen/main/objc|Generated ObjeciveC code. Included in all targets.|
|build/gen/main/swift|Generated Swift code. Included in all targets.|
|build/gen/main/resources|Generated resources. Included in all targets.|
|src/{target}/objc|ObjeciveC code. Included in all targets.|
|src/{target}/swift|Swift code. Included only in {target}|
|src/{target}/resources|Resources. Included only in {target}|
|build/gen/{target}/objc|Generated ObjeciveC code. Included only in {target}|
|build/gen/{target}/swift|Generated Swift code. Included only in {target}|
|build/gen/{target}/resources|Generated resources. Included only in {target}|


## Targets
Xcode build targets can be added to the project using the `targets` block:

**build.gradle**

```  
targets {
  MyOsxApp {
  	type "Application"
    	language "Swift"
	platform "Osx"
	buildConfiguration "Debug"
  }
  
  MyIosApp {
     	type "Application"
    	language "Swift"
	platform "Ios"
	buildConfiguration "Release"
  }
}
```   
    
In this example, the Xcode plugin adds two `PBXTarget` objects to the generated Xcode `pbxproj` file: `MyOsxApp.app` and `MyIosApp.app`. 

### Target Specific Source Sets
The Xcode plugin adds a source set for each target along with their `src/` and `build/gen/` directory sets. So for the above example, the Xcode plugin adds the following source sets:

* **MyOsxApp Source Set**
  * `src/MyOsxApp/{objc,swift,resources}`
  * `build/gen/MyOsxApp/{objc,swift,resources}`

* **MyIosApp Source Set**
  * `src/MyIosApp/{objc,swift,resources}`
  * `build/gen/MyIosApp/{objc,swift,resources}`
 
> Note: Files under `{src,build/gen}/main/{objc,swift,resources}` are included in **all** targets.

##Tasks

###create{Target}InfoPlist
The `create{Target}InfoPlist` creates a `Info.plist` file for each target in the `build/gen/{target}/resources` directory. The `infoPlist` block can be used  change the contents of the generated files:

**build.gradle**

```
targets {
	MyOsxApp {
   		type "Application"
		language "Swift"
  		platform "Osx"
    	buildConfiguration "Debug"
    	infoPlist{
      		NSHumanReadableCopyright =  "Some Copyright Statement"
    	}
  	}
 }
```
  
###createPbxproj
The `createPbxproj` task generates a Xcode format project file for project under `build/{ProjectName}.xcodeproj/`. The generation & manipulation of the Xcode project file is handled by the CocoaPod's [xcodeproj](https://github.com/CocoaPods/Xcodeproj) ruby scripts. 

