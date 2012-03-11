==============================================================================

  How to build OTAS DM Server
  
==============================================================================
 1. Install J2SE 1.5.x and ANT 1.6.x
 
    export JAVA_HOME=<J2SE 1.5 installation directory>
    export ANT_HOME=<ANT 1.6.x installation directory>
    PATH=$JAVA_HOME/bin:$ANT_HOME/bin;$PATH
    
 2. Check out OTAS DM Builder
    Make a base directory for building.
    > mkdir otas-dm-server
    > cd otas-dm-server
    > cvs -d:pserver:zhao@cvs.otas.cn:/home/master login
    > cvs -z3 -d:pserver:zhao@cvs.otas.cn:/home/master co nWave-DM-Build
    
    > cd nWave-DM-Build
    
 3. Modify file: "build.local.properties"
 
    # ----- CVS root for the jakarta repositories ------
    default.cvsroot=":pserver:zhao@127.0.0.1:/home/master"
    
    
    # ----- Base Directory -----
    otas.base.dir=/disk/zhao/otas-dm-server
    
 4. Checkout source code
 
    > cd nWave-DM-Build
    > ant cvs
    
 6. Make the binary
    This task will produce jar, ear packages in 
       "otas-dm-server/nWave-DM-Build/output/binary"
       
    > cd nWave-DM-Build
    > ant make-all
    
 7. Run all of testcases
    This task will produce test report in 
       "otas-dm-server/nWave-DM-Build/output/test"
       
    > cd nWave-DM-Build
    > ant test-all
    
 8. Build the package
    > cd nWave-DM-Build
    > ant package-all
    
    All of package will be output into "otas-dm-server/nWave-DM-Build/output"

 9. Generate java api docs
    This task will produce test report in 
       "otas-dm-server/nWave-DM-Build/output/javadocs"
       
    > cd nWave-DM-Build
    > ant javadoc-all
    
 10. Make all of tasks

    > cd nWave-DM-Build
    > ant all
    
 11. Clean all of output

    > cd nWave-DM-Build
    > ant clean-all
    
    
 Notes: 
  1. How to get help?
     you can get help by: ant help
  
  2. How to setup for release enviroment?
     In release mode, the build process will make rtag for each release.
     
     modified flag of release mode in build.local.properties:
     release.mode=true
       
    
=============================================================================

Zhao DongLu
        
$Header: /home/master/nWave-DM-Build/readme.txt,v 1.9 2006/07/10 09:33:12 zhao Exp $
$Revision: 1.9 $
$Date: 2006/07/10 09:33:12 $
  