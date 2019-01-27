# KasperPulseFramework
you can build android client ui by generating ui json string serverside and pushing it to client . this framework will get a json string as input and generate android ui elements tree . 

# Setup :

add jitpack io to project level build.gradle allprojects -> respositories block :

  allprojects 
  {
    repositories 
    {
      ...
      maven { url 'https://jitpack.io' }
    }
  }

add library dependency to app level build.gradle :

  implementation 'com.github.theprogrammermachine:KasperPulseFramework:v1.0'

# Sample page built with this util :

![alt text](https://pasteboard.co/HYq97bK.jpg)

![alt text](https://pasteboard.co/HYq9sZ0.jpg)

![alt text](https://pasteboard.co/HYq9C83.jpg)


