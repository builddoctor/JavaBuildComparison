#!/usr/bin/env ruby
require 'open3'
@results = {}

def collect(name, version_cmd, filter, test_cmd)
  @results[name] = {}
  @results[name]['version'] = `#{version_cmd} | grep #{filter}`
  command = "time #{test_cmd}"
  stdin, stdout, stderr = Open3.popen3(command)
  output = stderr.read.to_a
  last =  output.last
  @results[name]['stdout'] = stdout.read
  @results[name]['time'] = last
end

def header(message)
  print message.upcase
  puts ":"
end

def render
  @results.each_key do |key|
    header key
    puts @results[key]['version']
    puts @results[key]['time']
  end
end

collect('ant', 'ant -version', "Apache.Ant", 'ant clean packaged-code')
collect('maven', 'mvn -version', "Apache.Maven", 'mvn clean package')
collect('gradle', 'gradle -version', "Gradle.1", 'gradle clean build')
collect('rake', 'rake -V', "rake", 'rake clean package')
collect('buildr', 'bundle exec buildr -V', 'Buildr', 'bundle exec buildr clean package')
collect('make', 'make -v', 'GNU', 'make clean src/main/java/com/builddoctor/HelloWorld.class')

render
