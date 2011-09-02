#!/usr/bin/env ruby
require 'open3'
@results = {}

def collect(name, version_cmd, test_cmd)
  @results[name] = {}
  @results[name]['version'] = `#{version_cmd}`
  command = "time #{test_cmd}"
  stdin, stdout, stderr = Open3.popen3(command)
  puts name
  puts stdout.gets
  @results[name]['time'] = stderr.gets
end

def stars(count, eol)
  count.times { putc '*' }
  puts if eol
end

def header(message)
  length = 80
  padding = ((length - message.length) / 2) - 1
  stars(length, true)
  stars(padding, false)
  putc '  '
  putc ' ' if message.length.odd?
  print message.upcase
  putc '  '
  stars(padding, true)
  stars(length, true)
end

def render
  @results.each_key do |key|
    header key
    puts @results[key]['version']
    puts @results[key]['time']
  end
end

collect('ant', 'ant -version', 'ant clean packaged-code')
collect('maven', 'mvn -version', 'mvn clean install')
collect('gradle', 'gradle -version', 'gradle clean build')
collect('rake', 'rake -V', 'rake clean package')
collect('buildr', 'bundle exec buildr -v', 'bundle exec buildr clean package')
render
