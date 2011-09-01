require 'rake/clean'

CLEAN.include('target')
task :compile  do 
  mkdir_p "target/classes"
  classes = Dir.glob('src/main/java/**/*.java')
  sh "javac -d target/classes #{classes.join(' ')}"
end

task :package => :compile do 
  sh "cd target/classes && jar -cvf willy.jar ."
end
