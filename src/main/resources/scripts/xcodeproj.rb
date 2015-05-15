require 'xcodeproj'
require 'commander/import'

program :version, '0.1.0'
program :description, 'xcodeproj commands'

command :'project create' do |c|
  c.syntax = 'xcodeproj project create'
  c.description = 'Create a new project'
  c.option '--project PROJECT', String, 'Name of project'
  c.action do |args, options|
    raise "Project exists" if File.exists?(options.project)
    project = Xcodeproj::Project.new(options.project)
    project.save
  end
end

command :'project show' do |c|
  c.syntax = 'xcodeproj project show'
  c.description = 'Pretty print a project'
  c.option '--project PROJECT', String, 'Name of project'
  c.action do |args, options|
    project = Xcodeproj::Project.open(options.project)
    p project.pretty_print
  end
end

command :'group create' do |c|
  c.syntax = 'xcodeproj group create'
  c.description = 'Create a new group'
  c.option '--project PROJECT', String, 'Name of project'
  c.option '--name GROUP', String, 'Name of the group'
  c.option '--path PATH', String, 'Path to the new group'
  c.action do |args, o|
    project = Xcodeproj::Project.open(o.project)
    project.main_group.find_subpath o.path, true
    project.save
  end
end

command :'target create' do |c|
  c.syntax = 'xcodeproj target create'
  c.description = 'Create a new target'
  c.option '--project PROJECT', String, 'Name of project'
  c.option '--type TYPE', String, 'Type of target. Can be application, framework, dynamic_library or static_library.'
  c.option '--name NAME', String, 'Name of the target'
  c.option '--platform PLATFORM', String, 'Platform of the target. Can be `ios` or `osx`'
  c.option '--language LANGUAGE', String, '`objc` or `swift`'
  c.option '--deployment DEPLOYMENT TARGET', String, 'The deployment target'
  c.action do |args, o|
    project = Xcodeproj::Project.open(o.project)
    project.new_target(o.type.to_sym, o.name, o.platform.to_sym,
                       o.deployment, nil, o.language.to_sym)
    project.save
  end
end

command :'targets list' do |c|
  c.syntax = 'xcodeproj targets list'
  c.description = 'List targets in the project'
  c.option '--project PROJECT', String, 'Name of project'
  c.action do |args, o|
    project = Xcodeproj::Project.open(o.project)
    p project.targets.collect { |t| t.name }.join(' ')
    p project.root_object.build_configuration_list.build_configurations[0].build_settings["CLANG_WARN_ENUM_CONVERSION"]
  end
end

command :'target add files' do |c|
  c.syntax = 'xcodeproj target add file'
  c.description = 'Add file to target'
  c.option '--project PROJECT', String, 'Name of project'
  c.option '--target TARGET', String, 'Name of target'
  c.option '--path PATH', String, 'Path to the group where files will be added'
  c.option '--files FILES', Array, 'Files to add'
  c.action do |args, o|
    project = Xcodeproj::Project.open(o.project)
    target = project.targets.select { |t| t.name == o.target }[0]
    raise 'Target not found' if target.nil?
    group = project.main_group.find_subpath o.path, true
    refs = o.files.collect do |file|
      group.new_file file
    end
    target.add_file_references refs
    project.save
  end
end

command :'build setting' do |c|
  c.syntax = 'xcodeproj build setting'
  c.description = 'List targets in the project'
  c.option '--project PROJECT', String, 'Name of project'
  c.option '--target TARGET', String, 'the target'
  c.option '--config CONFIG', String, 'the build configuration'
  c.option '--name NAME', String, 'the build setting name'
  c.option '--value VALUE', String, 'the build setting value'
  c.action do |args, o|
    project = Xcodeproj::Project.open(o.project)
    list = if o.target.nil?
             project.root_object.build_configuration_list.build_configurations
           else
             target = project.targets.select { |t| t.name == o.target }[0]
             raise 'Target not found' if target.nil?
             target.build_configuration_list.build_configurations
           end
    config = list.find { |x| x.name == o.config }
    raise 'Build configuration not found' if config.nil?
    if o.value.nil?
      p config.build_settings[o.name]
    else
      config.build_settings[o.name] = o.value
      project.save
    end
  end
end

