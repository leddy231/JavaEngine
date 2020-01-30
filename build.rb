$jars = ["lwjgl"]
$dirs = [];

def generatePath
    path = ["./"] + $dirs
    for directory in $jars
        Dir.chdir('./' + directory) do
            Dir.glob('*').each do |file|
                path << "./#{directory}/#{file}"
            end
        end
    end
    separator = windows ? ";" : ":"
    return path.join(separator)
end

def build
    puts "Compiling"
    return system('javac -cp "' + generatePath + '" *.java')
end

def run(args)
    puts "Running"
    system('java -cp "' + generatePath + '" Main ' + args.join(" "))
end

def clean
    Dir.glob('./**/*.class').each { |file| File.delete(file)}
end

def windows
    (/cygwin|mswin|mingw|bccwin|wince|emx/ =~ RUBY_PLATFORM) != nil
end

case ARGV[0]
when "-d"
    build
    clean
when "-b"
    build
when "-r"
    run([])
when "-c"
    clean
else
    if build
        sleep(0.1)
        run(ARGV)
    end
    sleep(0.1)
    clean
end