require 'rake/clean'

CLASSPATH="target/classes:target/test-classes:lib/junit-4.9.jar"

CLEAN.include('target')
CLEAN.include('TEST-*')
CLEAN.include('reports')

task :compile  do 
  mkdir_p "target/classes"
  classes = Dir.glob('src/main/java/**/*.java')
  sh "javac -d target/classes #{classes.join(' ')}"
end

task :compile_tests => :compile do 
  mkdir_p "target/test-classes"
  test_classes = Dir.glob('src/test/java/**/*.java')
  sh "javac -d target/test-classes -cp #{CLASSPATH} #{test_classes.join(' ')}"
end

task :test => :compile_tests do 
  tests = Dir['src/test/java/**/*.java'].map { |file| file.sub(/\.java/, '').gsub(/\//, '.').sub('src.test.java.', '') }
  sh "java -cp #{CLASSPATH} org.junit.runner.JUnitCore #{tests.join(' ')}"
end

task :package => :test do 
  sh "cd target/classes && jar -cvf willy.jar ."
end

task :default => :package
