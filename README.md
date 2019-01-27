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

<p float="left">
  <img src="https://github.com/theprogrammermachine/KasperPulseFramework/blob/master/images/image1.jpg" alt="image1" width="300" height="500">
  <img src="https://github.com/theprogrammermachine/KasperPulseFramework/blob/master/images/image2.jpg" alt="image2" width="300" height="500">
  <img src="https://github.com/theprogrammermachine/KasperPulseFramework/blob/master/images/image3.jpg" alt="image3" width="300" height="500">
</p>
