#!/usr/bin/env ruby
require 'open3'
@results = {}

def collect(name, version_cmd, filter, test_cmd)
  @results[name] = {}
  @results[name]['version'] = `#{version_cmd} | grep #{filter}`
  start_time = Time.now
  command = "(cd small && #{test_cmd})"
  stdin, stdout, stderr = Open3.popen3(command)
  output = stderr.read.to_a
  last =  output.last
  finish_time = Time.now
  @results[name]['stdout'] = stdout.read
  @results[name]['time'] = finish_time - start_time
end

def header(message)
  print message.upcase
  puts ":"
end

def render
  sorted = @results.sort_by { |k,v| v['time'] }
  sorted.each do |a|
    header a[0]
    info = a[1]
      puts info['version']
      puts info['time']
  end
end

collect('ant', 'ant -version', "Apache.Ant", 'ant clean packaged-code')
collect('maven', 'mvn -version', "Apache.Maven", 'mvn clean package')
collect('gradle', 'gradle -version', "Gradle.1", 'gradle clean build')
collect('rake', 'rake -V', "rake", 'rake clean package')
collect('buildr', 'bundle exec buildr -V', 'Buildr', 'bundle exec buildr clean package')
collect('make', 'make -v', 'GNU', 'make clean src/main/java/com/builddoctor/HelloWorld.class')

render
